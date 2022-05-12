package com.sdd.caption.viewmodel;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.TincomingDAO;
import com.sdd.caption.dao.TpinpaditemDAO;
import com.sdd.caption.dao.TsecuritiesitemDAO;
import com.sdd.caption.dao.TtokenitemDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tincoming;
import com.sdd.caption.domain.Tpinpaditem;
import com.sdd.caption.domain.Tsecuritiesitem;
import com.sdd.caption.domain.Ttokenitem;
import com.sdd.caption.model.TincomingListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class IncomingApprovalVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private TincomingListModel model;

	private TincomingDAO oDao = new TincomingDAO();
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;
	private String action;
	private String declinememo;
	private String productgroup;
	private String producttype;
	private int maxFlush;
	private int maxBatchCommit;
	private int flushCounter;
	private Integer total;

	private Muser oUser;
	private List<Tincoming> objSelected = new ArrayList<Tincoming>();

	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Radiogroup rgapproval;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg)
			throws Exception {
		Selectors.wireComponents(view, this, false);
		productgroup = arg.trim();
		System.out.println(productgroup);
		maxFlush = Integer.parseInt(Library.getProperty("maxFlush"));
		maxBatchCommit = Integer.parseInt(Library.getProperty("maxBatchCommit"));
		oUser = (Muser) zkSession.getAttribute("oUser");

		paging.addEventListener("onPaging", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Tincoming>() {
				@Override
				public void render(Row row, final Tincoming data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
					Checkbox check = new Checkbox();
					check.setAttribute("obj", data);
					check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Checkbox checked = (Checkbox) event.getTarget();
							if (checked.isChecked())
								objSelected.add((Tincoming) checked.getAttribute("obj"));
							else
								objSelected.remove((Tincoming) checked.getAttribute("obj"));
						}
					});
					row.getChildren().add(check);
					row.getChildren().add(new Label(data.getIncomingid()));
					row.getChildren().add(new Label(AppData.getProductgroupLabel(data.getProductgroup())));
					row.getChildren().add(new Label(data.getMproducttype().getProducttype()));
					row.getChildren().add(new Label(
							data.getEntrytime() != null ? datetimeLocalFormatter.format(data.getEntrytime()) : "-"));
					row.getChildren().add(new Label(
							data.getEntryby() != null && !data.getEntryby().equals("") ? data.getEntryby() : "-"));
					row.getChildren().add(new Label(
							data.getItemqty() != null ? NumberFormat.getInstance().format(data.getItemqty()) : "0"));
					row.getChildren().add(
							new Label(data.getMemo() != null && !data.getMemo().equals("") ? data.getMemo() : "-"));
				}
			});
		}
		doReset();
	}

	@Command
	@NotifyChange({"pageTotalSize", "total"})
	public void doSearch() {
		try {
			filter = "productgroup = '" + productgroup + "' and status = '" + AppUtils.STATUS_INVENTORY_INCOMINGWAITAPPROVAL
					+ "'";

			if (producttype != null && producttype.trim().length() > 0) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "mproducttype.producttype like '%" + producttype.trim().toUpperCase() + "%'";
			}
			
			for(Tincoming data : oDao.listNativeByFilter(filter, "tincomingpk")) {
				total = total + data.getItemqty();
			}
			needsPageUpdate = true;
			paging.setActivePage(0);
			pageStartNumber = 0;
			refreshModel(pageStartNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			objSelected = new ArrayList<Tincoming>();
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				if (checked) {
					chk.setChecked(true);
					objSelected.add((Tincoming) chk.getAttribute("obj"));
				} else {
					chk.setChecked(false);
					objSelected.remove((Tincoming) chk.getAttribute("obj"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		declinememo = null;
		objSelected = new ArrayList<Tincoming>();
		total = 0;
		doSearch();
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tincomingpk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TincomingListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		if (objSelected.size() > 0) {
			if (action != null && action.length() > 0) {
				if (action.equals(AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED)
						|| (action.equals(AppUtils.STATUS_INVENTORY_INCOMINGDECLINE) && declinememo != null
								&& declinememo.trim().length() > 0)) {
					Session session = StoreHibernateUtil.openSession();
					Transaction transaction = session.beginTransaction();
					try {
						for (Tincoming obj : objSelected) {
							if (action.equals(AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED)) {
								flushCounter = 0;

								Mproducttype mproducttype = obj.getMproducttype();

								if (obj.getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN)) {
									obj.setDecisionby(oUser.getUserid());
									obj.setDecisiontime(new Date());
									obj.setStatus(AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED);

									Integer itemno = obj.getItemstartno();
									for (Integer i = 1; i <= obj.getItemqty(); i++) {
										Ttokenitem data = new Ttokenitem();
										data.setTincoming(obj);
										data.setItemno(itemno.toString());
										data.setItemnoinject(null);
										data.setStatus(AppUtils.STATUS_SERIALNO_ENTRY);
										new TtokenitemDAO().save(session, data);
										itemno++;
									}
								} else if (obj.getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)) {
									String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath(
											AppUtils.FILES_ROOT_PATH + AppUtils.PATH_PINPAD + "/" + obj.getFilename());
									System.out.println(path);
									FileInputStream file = new FileInputStream(path);
									obj.setDecisionby(oUser.getUserid());
									obj.setDecisiontime(new Date());
									obj.setStatus(AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED);

									Workbook wb = null;
									if (obj.getFilename().toLowerCase().endsWith("xlsx")) {
										wb = new XSSFWorkbook(file);
									} else if (obj.getFilename().trim().toLowerCase().endsWith("xls")) {
										wb = new HSSFWorkbook(file);
									}
									Sheet sheet = wb.getSheetAt(0);
									for (org.apache.poi.ss.usermodel.Row row : sheet) {
										try {
											if (row.getRowNum() < 1) {
												continue;
											}
											Tpinpaditem data = new Tpinpaditem();
											for (Integer count = 0; count <= row.getLastCellNum(); count++) {
												data.setTincoming(obj);
												Cell cell = row.getCell(count,
														org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
												if (cell == null) {
													continue;
												}

												switch (count) {
												case 1:
													if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
														cell.setCellType(Cell.CELL_TYPE_STRING);
														data.setItemno(cell.getStringCellValue());
													} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
														data.setItemno(cell.getStringCellValue());
													}
													break;
												}
												data.setStatus(AppUtils.STATUS_SERIALNO_ENTRY);
												new TpinpaditemDAO().save(session, data);
											}

											if (flushCounter % maxFlush == 0) {
												session.flush();
												session.clear();
											}

											if (flushCounter % maxBatchCommit == 0) {
												transaction.commit();
												session.close();

												session = StoreHibernateUtil.openSession();
												transaction = session.beginTransaction();
											}

											flushCounter++;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								} else if (obj.getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
									obj.setDecisionby(oUser.getUserid());
									obj.setDecisiontime(new Date());
									obj.setStatus(AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED);
									
									Integer itemno = obj.getItemstartno();
									for (Integer i = 1; i <= obj.getItemqty(); i++) {
										Tsecuritiesitem data = new Tsecuritiesitem();
										data.setTincoming(obj);
										data.setItemno(obj.getPrefix() + itemno.toString());
										data.setNumerator(itemno);
										data.setStatus(AppUtils.STATUS_SERIALNO_ENTRY);
										new TsecuritiesitemDAO().save(session, data);
										
										itemno++;
									}
								}

								mproducttype.setLaststock(
										(mproducttype.getLaststock() == null ? 0 : mproducttype.getLaststock())
												+ obj.getItemqty());
								if (mproducttype.getLaststock() >= mproducttype.getStockmin()
										&& mproducttype.getIsalertstockpagu().equals("Y")) {
									mproducttype.setIsalertstockpagu("N");
									mproducttype.setIsblockpagu("N");
									mproducttype.setAlertstockpagurelease(new Date());
								} else {
									mproducttype.setIsalertstockpagu("N");
									mproducttype.setAlertstockpagurelease(new Date());
								}
								mproducttypeDao.save(session, mproducttype);
							}
							obj.setStatus(action);
							obj.setDecisionmemo(declinememo);
							obj.setDecisionby(oUser.getUserid());
							obj.setDecisiontime(new Date());
							oDao.save(session, obj);
						}
						transaction.commit();
						Clients.showNotification("Proses approval data berhasil", "info", null, "middle_center", 3000);
						doReset();
					} catch (Exception e) {
						transaction.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}
				} else {
					Messagebox.show("Anda harus mengisi field Decline Memo", "Info", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("Silahkan pilih action", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("Silahkan pilih data untuk proses submit", "Info", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDeclinememo() {
		return declinememo;
	}

	public void setDeclinememo(String declinememo) {
		this.declinememo = declinememo;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}

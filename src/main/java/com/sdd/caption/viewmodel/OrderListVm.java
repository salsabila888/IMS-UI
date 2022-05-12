package com.sdd.caption.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.bind.annotation.ContextParam;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.ToutgoingDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Toutgoing;
import com.sdd.caption.handler.OrderPrintHandler;
import com.sdd.caption.handler.PinmailerHandler;
import com.sdd.caption.model.TorderListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.StringUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class OrderListVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private TorderListModel model;
	private TorderDAO oDao = new TorderDAO();
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();
	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private int totalcheck;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;
	private String orderid;
	private String title;
	private Date date;
	private Integer year;
	private Integer month;
	private String productgroup;
	private Integer totalselected;
	private Integer totaldataselected;
	private String ordertype;
	private Boolean isSaved;
	private String status;
	private Integer branchlevel;
	private Map<Integer, Torder> mapData = new HashMap<>();

	// private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	@Wire
	private Combobox cbMonth;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Checkbox chkAll;
	@Wire
	private Column action;
	@Wire
	private Row row;
	@Wire
	private Foot foot;
	@Wire
	private Button btnPersoDone, btnManifest;
	@Wire
	private Label lbTitle;
	@Wire
	private Window winOrderList;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg)
			throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		if(oUser != null)
			branchlevel = oUser.getMbranch().getBranchlevel();

		doResetListSelected();
		productgroup = arg;
		title = AppData.getProductgroupLabel(productgroup);

		if (productgroup.equals("TC") || productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)
				|| productgroup.equals(AppUtils.PRODUCTGROUP_DOCUMENT))
			action.setVisible(true);
		else
			foot.setVisible(true);

		if (productgroup.equals("TC"))
			lbTitle.setValue("DAFTAR ORDER CABANG");
		else
			lbTitle.setValue("DAFTAR ORDER PRODUKSI");

		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
				chkAll.setChecked(false);
			}
		});

		grid.setRowRenderer(new RowRenderer<Torder>() {
			@Override
			public void render(Row row, final Torder data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
				Checkbox check = new Checkbox();
				check.setAttribute("obj", data);
				check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						Checkbox checked = (Checkbox) event.getTarget();
						Torder obj = (Torder) checked.getAttribute("obj");
						if (checked.isChecked()) {
							if (obj.getStatus().equals(AppUtils.STATUS_PRODUCTION_PRODUKSIWAITAPPROVAL)
									|| obj.getStatus().equals(AppUtils.STATUS_PRODUCTION_DECLINEPRODUKSI)
									|| obj.getStatus().equals(AppUtils.STATUS_INVENTORY_OUTGOINGDECLINE)
									|| obj.getStatus().equals(AppUtils.STATUS_ORDER_WAITAPPROVAL)) {
								checked.setChecked(false);
								Messagebox.show(
										"Data belum bisa dipilih karena dalam status "
												+ AppData.getStatusLabel(obj.getStatus()),
										"Info", Messagebox.OK, Messagebox.INFORMATION);
							} else {
								if (obj.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)) {
									totalcheck++;
									System.out.println("TOTAL CHECK : " + totalcheck);
								}
								mapData.put(data.getTorderpk(), data);
								totaldataselected += obj.getItemqty();
							}
						} else {
							mapData.remove(data.getTorderpk());
							totaldataselected -= obj.getItemqty();
							if (obj.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)) {
								totalcheck -= 1;
								System.out.println("TOTAL CHECK : " + totalcheck);
							}
						}
						totalselected = mapData.size();
						if (totalcheck > 0)
							btnPersoDone.setDisabled(false);
						else
							btnPersoDone.setDisabled(true);
						BindUtils.postNotifyChange(null, null, OrderListVm.this, "totalselected");
						BindUtils.postNotifyChange(null, null, OrderListVm.this, "totaldataselected");
					}
				});
				if (mapData.get(data.getTorderpk()) != null)
					check.setChecked(true);
				row.getChildren().add(check);

				A a = new A(data.getOrderid());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						Window win = new Window();
						map.put("obj", data);
						map.put("arg", arg);
						win = (Window) Executions.createComponents("/view/order/orderitem.zul", null, map);
						win.setWidth("50%");
						win.setClosable(true);
						win.doModal();
					}
				});
				row.getChildren().add(a);
				row.getChildren().add(new Label(
						data.getInserttime() != null ? dateLocalFormatter.format(data.getInserttime()) : "-"));
				row.getChildren().add(new Label(data.getInsertedby() != null ? data.getInsertedby() : "-"));
				row.getChildren().add(new Label(data.getOrderoutlet() != null ? data.getOrderoutlet() : "-"));
				row.getChildren().add(new Label(data.getMproduct().getProductname()));
				row.getChildren().add(new Label(
						data.getItemqty() != null ? NumberFormat.getInstance().format(data.getItemqty()) : "0"));
				row.getChildren()
						.add(new Label(
								data.getTotalproses() != null ? NumberFormat.getInstance().format(data.getTotalproses())
										: "0"));

				if (data.getStatus().equals(AppUtils.STATUS_ORDER_DECLINE)) {
					if (data.getOrderlevel() == 3) {
						if (!data.getOrderoutlet().equals("00")) {
							status = AppData.getStatusLabel(data.getStatus()) + "CABANG";
						} else {
							status = AppData.getStatusLabel(data.getStatus()) + "WILAYAH";
						}
					} else if (data.getOrderlevel() == 2) {
						status = AppData.getStatusLabel(data.getStatus()) + "DIVISI";
					}
				} else {
					status = AppData.getStatusLabel(data.getStatus());
				}

				row.getChildren().add(new Label(data.getStatus() != null ? status : "-"));
				row.getChildren().add(new Label(data.getDecisionby() != null ? data.getDecisionby() : "-"));
				row.getChildren().add(new Label(
						data.getDecisiontime() != null ? dateLocalFormatter.format(data.getDecisiontime()) : "-"));

				Button btnMemo = new Button("Memo");
				btnMemo.setAutodisable("self");
				btnMemo.setClass("btn btn-sm btn-info");
				btnMemo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("obj", data);
						map.put("arg", arg);
						Window win = (Window) Executions.createComponents("/view/order/ordermemo.zul", null, map);
						win.setWidth("60%");
						win.setClosable(true);
						win.doModal();
					}
				});

				row.getChildren().add(btnMemo);

				Button btnscan = new Button("Outbound");
				btnscan.setAutodisable("self");
				btnscan.setClass("btn btn-default btn-sm");
				btnscan.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						map.put("obj", data);
						Window win = new Window();
						if (productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)) {
							win = (Window) Executions.createComponents("/view/pinpad/pinpadscanentry.zul", null, map);
						} else if (productgroup.equals("TC")) {
							win = (Window) Executions.createComponents("/view/token/tokenscanentry.zul", null, map);
						}
						win.setWidth("70%");
						win.setClosable(true);
						win.doModal();
						win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getData() != null) {
									needsPageUpdate = true;
									pageStartNumber = 0;
									refreshModel(pageStartNumber);

									BindUtils.postNotifyChange(null, null, OrderListVm.this, "pageTotalSize");
								}
							}
						});
					}
				});

				Button btnoutgoing = new Button("Outgoing");
				btnoutgoing.setAutodisable("self");
				btnoutgoing.setClass("btn btn-success btn-sm");
				btnoutgoing.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						toOutgoing(data);
						
						needsPageUpdate = true;
						pageStartNumber = 0;
						refreshModel(pageStartNumber);
					}
				});

				Button btnswitch = new Button("Switching");
				btnswitch.setAutodisable("self");
				btnswitch.setClass("btn btn-primary btn-sm");
				btnswitch.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						map.put("obj", data);
						map.put("arg", data.getProductgroup());

						Window win = new Window();
						win = (Window) Executions.createComponents("/view/switching/switchingentry.zul", null, map);
						win.setWidth("70%");
						win.setClosable(true);
						win.doModal();
						win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getData() != null) {
									needsPageUpdate = true;
									pageStartNumber = 0;
									refreshModel(pageStartNumber);

									BindUtils.postNotifyChange(null, null, OrderListVm.this, "pageTotalSize");
								}
							}
						});
					}
				});

				if (data.getStatus().equals(AppUtils.STATUS_ORDER_REQUESTORDER)
						&& data.getOrdertype().equals(AppUtils.ENTRYTYPE_MANUAL_BRANCH)) {
					if (branchlevel < 3) {
						if (data.getOrderlevel() - 1 == branchlevel) {
							Div div = new Div();
							div.setClass("btn-group btn-sm");
							div.appendChild(btnoutgoing);
							div.appendChild(btnswitch);
							row.getChildren().add(div);
						} else {
							row.getChildren().add(new Label());
						}
					} else {
						if (!data.getOrderoutlet().equals("00")) {
							Div div = new Div();
							div.setClass("btn-group btn-sm");
							div.appendChild(btnoutgoing);
							div.appendChild(btnswitch);
							row.getChildren().add(div);
						} else {
							row.getChildren().add(new Label());
						}
					}
				} else {
					if (productgroup.equals("TC") || productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)) {
						if (data.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)
								&& data.getOrdertype().equals(AppUtils.ENTRYTYPE_MANUAL_BRANCH)) {
							row.getChildren().add(btnscan);
						} else {
							row.getChildren().add(new Label());
						}

					} else {
						row.getChildren().add(new Label());
					}
				}
			}
		});

		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; i < months.length; i++) {
			Comboitem item = new Comboitem();
			item.setLabel(months[i]);
			item.setValue(i + 1);
			cbMonth.appendChild(item);
		}
		doReset();
	}

	@NotifyChange("*")
	public void toOutgoing(Torder obj) {
		try {
			boolean isValid = true;
			Session session = StoreHibernateUtil.openSession();
			Transaction transaction = session.beginTransaction();

			if (oUser.getMbranch().getBranchlevel() == 1) {
				Mproducttype objStock = mproducttypeDao
						.findByPk(obj.getMproduct().getMproducttype().getMproducttypepk());
				if (objStock != null) {
					if (objStock.getLaststock() - objStock.getStockreserved() < obj.getItemqty()) {
						isValid = false;
					} else {
						objStock.setStockreserved(objStock.getStockreserved() + obj.getItemqty());
						mproducttypeDao.save(session, objStock);
					}

				} else {
					isValid = false;
				}
			} else if (oUser.getMbranch().getBranchlevel() == 2) {
				Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk()
						+ " and mproductfk = " + obj.getMproduct().getMproductpk());
				if (objStock != null) {
					if (objStock.getStockcabang() - objStock.getStockreserved() > obj.getItemqty()) {
						objStock.setStockreserved(objStock.getStockreserved() + obj.getItemqty());
						tbranchstockDao.save(session, objStock);
					} else {
						isValid = false;
					}
				} else {
					isValid = false;
				}
			} else if (oUser.getMbranch().getBranchlevel() == 3) {
				Tbranchstock objStock = tbranchstockDao
						.findByFilter("mbranchfk = " + obj.getMbranch().getMbranchpk() + " and mproductfk = "
								+ obj.getMproduct().getMproductpk() + " outlet = '00'");
				if (objStock != null) {
					if (objStock.getStockcabang() - objStock.getStockreserved() > obj.getItemqty()) {
						objStock.setStockreserved(objStock.getStockreserved() + obj.getItemqty());
						tbranchstockDao.save(session, objStock);
					} else {
						isValid = false;
					}
				} else {
					isValid = false;
				}
			}

			if (isValid) {
				obj.setStatus(AppUtils.STATUS_ORDER_OUTGOINGAPPROVAL);
				new TorderDAO().save(session, obj);

				Toutgoing toutgoing = new Toutgoing();
				toutgoing.setMproduct(obj.getMproduct());
				toutgoing.setTorder(obj);
				toutgoing.setEntryby(obj.getInsertedby());
				toutgoing.setEntrytime(new Date());
				toutgoing.setItemqty(obj.getItemqty());
				toutgoing.setLastupdated(new Date());
				toutgoing.setOutgoingid(obj.getOrderid());
				toutgoing.setProductgroup(obj.getMproduct().getProductgroup());
				toutgoing.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGWAITAPPROVAL);
				toutgoing.setUpdatedby(oUser.getUserid());
				new ToutgoingDAO().save(session, toutgoing);
			} else {
				Messagebox.show("Tidak dapat melanjutkan proses stock tidak mencukupi.", "Info", Messagebox.OK,
						Messagebox.INFORMATION);
			}

			transaction.commit();
			session.close();

			if (isValid) {
				doReset();
				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doExport() {
		try {
			if (filter.length() > 0) {
				List<Torder> listData = oDao.listByFilter(filter, orderby);
				if (listData != null && listData.size() > 0) {
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet sheet = workbook.createSheet();

					int rownum = 0;
					int cellnum = 0;
					Integer no = 0;
					Integer total = 0;
					org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
					Cell cell = row.createCell(0);
					cell.setCellValue("Daftar Order " + AppData.getProductgroupLabel(productgroup));
					rownum++;
					row = sheet.createRow(rownum++);
					cell = row.createCell(0);
					cell.setCellValue("Periode");
					cell = row.createCell(1);
					cell.setCellValue(StringUtils.getMonthLabel(month) + " " + year);
					row = sheet.createRow(rownum++);

					/*
					 * row = sheet.createRow(rownum++); cell = row.createCell(0); rownum++;
					 */
					Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
					datamap.put(1, new Object[] { "No", "No Order", "Tanggal Order", "Pemesan", "Order Level",
							"Order Outlet", "Jenis Produk", "Total Data", "Status" });
					no = 2;
					for (Torder data : listData) {
						datamap.put(no,
								new Object[] { no - 1, data.getOrderid(),
										dateLocalFormatter.format(data.getInserttime()), data.getInsertedby(),
										NumberFormat.getInstance().format(data.getOrderlevel()), data.getOrderoutlet(),
										data.getMproduct().getProductname(), data.getItemqty(),
										AppData.getStatusLabel(data.getStatus()) });
						no++;
						total += data.getItemqty();
					}
					datamap.put(no, new Object[] { "", "TOTAL", "", total });
					Set<Integer> keyset = datamap.keySet();
					for (Integer key : keyset) {
						row = sheet.createRow(rownum++);
						Object[] objArr = datamap.get(key);
						cellnum = 0;
						for (Object obj : objArr) {
							cell = row.createCell(cellnum++);
							if (obj instanceof String)
								cell.setCellValue((String) obj);
							else if (obj instanceof Integer)
								cell.setCellValue((Integer) obj);
							else if (obj instanceof Double)
								cell.setCellValue((Double) obj);
						}
					}

					String path = Executions.getCurrent().getDesktop().getWebApp()
							.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
					String filename = "CAPTION_DAFTAR_ORDER_" + new SimpleDateFormat("yyMMddHHmm").format(new Date())
							+ ".xlsx";
					FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
					workbook.write(out);
					out.close();

					Filedownload.save(new File(path + "/" + filename),
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				} else {
					Messagebox.show("Data tidak tersedia", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "torderpk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TorderListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}

	@NotifyChange({ "totalselected", "totaldataselected" })
	private void doResetListSelected() {
		totalselected = 0;
		totaldataselected = 0;
		totalcheck = 0;
		mapData = new HashMap<>();
	}

	@Command
	@NotifyChange({ "totalselected", "totaldataselected" })
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			List<Row> components = grid.getRows().getChildren();
			int statusfail = 0;
			boolean branchInvalid = true;
			boolean productInvalid = true;
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				Torder obj = (Torder) chk.getAttribute("obj");
				if (checked) {
					if (!chk.isChecked()) {
						if (obj.getStatus().equals(AppUtils.STATUS_PRODUCTION_PRODUKSIWAITAPPROVAL)
								|| obj.getStatus().equals(AppUtils.STATUS_PRODUCTION_DECLINEPRODUKSI)
								|| obj.getStatus().equals(AppUtils.STATUS_INVENTORY_OUTGOINGDECLINE)) {
							chk.setChecked(false);
							statusfail++;
						} else {

							if (obj.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)) {
								totalcheck++;
								System.out.println("TOTAL CHECK : " + totalcheck);
							}
							chk.setChecked(true);
							mapData.put(obj.getTorderpk(), obj);
							totaldataselected += obj.getItemqty();
						}
					}
				} else {
					if (chk.isChecked()) {
						chk.setChecked(false);
						mapData.remove(obj.getItemqty());
						totaldataselected -= obj.getItemqty();
						if (obj.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)) {
							totalcheck -= 1;
							System.out.println("TOTAL CHECK : " + totalcheck);
						}
					}
				}
			}
			if (totalcheck > 0)
				btnPersoDone.setDisabled(false);
			else
				btnPersoDone.setDisabled(true);
			if (statusfail > 0) {
				Messagebox.show("Ada beberapa data belum bisa dipilih karena dalam status belum approved oleh produksi",
						"Info", Messagebox.OK, Messagebox.INFORMATION);
			} else if (!branchInvalid) {
				Messagebox.show("Ada beberapa ID cabang yang belum terdaftar di Caption.",
						WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
			} else if (!productInvalid) {
				Messagebox.show("Ada beberapa kode produk yang belum terdaftar di Caption.",
						WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
			}
			totalselected = mapData.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doViewSelected() {
		if (mapData.size() > 0) {
			Map<String, Object> map = new HashMap<>();
			map.put("mapData", mapData);
			map.put("totalselected", totalselected);
			map.put("totaldataselected", totaldataselected);

			Window win = (Window) Executions.createComponents("/view/order/orderselected.zul", null, map);
			win.setClosable(true);
			win.doModal();
		} else {
			Messagebox.show("Tidak ada data", "Info", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	@NotifyChange("*")
	@Command
	public void doResetSelected() {
		if (mapData.size() > 0) {
			Messagebox.show("Anda ingin mereset data-data yang sudah anda pilih?", "Confirm Dialog",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								doResetListSelected();
								refreshModel(pageStartNumber);
								BindUtils.postNotifyChange(null, null, OrderListVm.this, "*");
							}
						}
					});
		} else {
			Messagebox.show("Tidak ada data", "Info", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	@Command
	public void doDone() {
		try {
			if (mapData.size() == 0) {
				Messagebox.show("Tidak ada data", WebApps.getCurrent().getAppName(), Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("Anda ingin melakukan update status done produksi?", "Confirm Dialog",
						Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onOK")) {
									if (productgroup.equals(AppUtils.PRODUCTGROUP_PINMAILER)) {
										Map<String, Object> mapResult = PinmailerHandler.doDone(mapData,
												(Muser) Sessions.getCurrent().getAttribute("oUser"));
										String error = (String) mapResult.get("error");
										int failed = (int) mapResult.get("failed");
										if (error != null) {
											Messagebox.show(error, WebApps.getCurrent().getAppName(), Messagebox.OK,
													Messagebox.ERROR);
										} else {
											if (failed > 0) {
												Messagebox.show(
														"Ada beberapa data belum diupdate karena status belum produksi",
														WebApps.getCurrent().getAppName(), Messagebox.OK,
														Messagebox.INFORMATION);
											} else {
												Messagebox.show("Update status done berhasil",
														WebApps.getCurrent().getAppName(), Messagebox.OK,
														Messagebox.INFORMATION);
											}
											refreshModel(pageStartNumber);
											doReset();
										}
									}
								}
							}
						});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doBon() {
		if (mapData.size() == 0) {
			Messagebox.show("Tidak ada data", WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("process", "Cetak Bon Kartu");
			Window win = (Window) Executions.createComponents("/view/export/exportformat.zul", null, map);
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getData() != null) {
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) event.getData();
						String format = (String) map.get("format");
						List<Torder> objList = new ArrayList<>();
						for (Entry<Integer, Torder> entry : mapData.entrySet()) {
							Torder obj = entry.getValue();
							objList.add(obj);
						}

						OrderPrintHandler.doBonPrint(objList, format, productgroup);
					}
				}
			});
		}
	}

	@Command
	public void doManifestPrint() {
		try {
			if (mapData.size() == 0) {
				Messagebox.show("Tidak ada data", "Info", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				Map<String, Object> map = new HashMap<>();
				map.put("isOrder", "Y");
				Window win = (Window) Executions.createComponents("/view/perso/persomanifestprint.zul", null, map);
				win.setClosable(true);
				win.doModal();
				win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getData() != null) {
							try {

								Map<String, Object> map = (Map<String, Object>) event.getData();
								String operators = (String) map.get("operators");

								List<Torder> objList = new ArrayList<>();
								for (Entry<Integer, Torder> entry : mapData.entrySet()) {
									Torder obj = entry.getValue();
									objList.add(obj);
								}
								Collections.sort(objList, Torder.fieldComparator);
								zkSession.setAttribute("objList", objList);

								Map<String, String> parameters = new HashMap<>();
								parameters.put("OPERATORS", operators);

								zkSession.setAttribute("parameters", parameters);
								if (productgroup.equals(AppUtils.PRODUCTGROUP_CARD))
									zkSession.setAttribute("reportPath",
											Executions.getCurrent().getDesktop().getWebApp()
													.getRealPath(SysUtils.JASPER_PATH + "/manualmanifestorder.jasper"));
								else
									zkSession.setAttribute("reportPath", Executions.getCurrent().getDesktop()
											.getWebApp().getRealPath(SysUtils.JASPER_PATH + "/manifestnoncard.jasper"));

								Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");
							} catch (HibernateException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		// if (year != null && month != null) {
		// filter = "extract(year from inserttime) = " + year + " and " + "extract(month
		// from inserttime) = " + month
		// + " and productgroup = '" + arg + "'";
		// if (orderid != null && orderid.length() > 0) {
		// if (filter.length() > 0)
		// filter += " and ";
		// filter += "orderid like '%" + orderid.trim().toUpperCase() + "%'";
		// }
		// needsPageUpdate = true;
		// paging.setActivePage(0);
		// pageStartNumber = 0;
		// refreshModel(pageStartNumber);
		// }
		if (productgroup.equals("TC"))
			filter = "productgroup = '" + AppUtils.PRODUCTGROUP_TOKEN + "' and extract(year from inserttime) = " + year
					+ " and " + "extract(month from inserttime) = " + month + " and ordertype = '"
					+ AppUtils.ENTRYTYPE_MANUAL_BRANCH + "'";
		else if (productgroup.equals(AppUtils.PRODUCTGROUP_TOKEN))
			filter = "productgroup = '" + productgroup + "' and extract(year from inserttime) = " + year + " and "
					+ "extract(month from inserttime) = " + month + " and ordertype = '" + AppUtils.ENTRYTYPE_MANUAL
					+ "'";
		else
			filter = "productgroup = '" + productgroup + "' and extract(year from inserttime) = " + year + " and "
					+ "extract(month from inserttime) = " + month;
//		}

		if (branchlevel == 2)
			filter += " and mregionfk = " + oUser.getMbranch().getMregion().getMregionpk();
		else if (branchlevel == 3)
			filter += " and mbranchfk = " + oUser.getMbranch().getMbranchpk();

		if (orderid != null && orderid.length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "orderid like '%" + orderid.trim().toUpperCase() + "%'";
		}
		if (ordertype != null && ordertype.length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "ordertype = '" + ordertype.trim().toUpperCase() + "'";
		}
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		orderid = null;
		date = new Date();
		btnPersoDone.setDisabled(true);
		year = Calendar.getInstance().get(Calendar.YEAR);
		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		ordertype = "";
		doSearch();
	}

	@Command
	public void doClose() {
		Event closeEvent = new Event("onClose", winOrderList, isSaved);
		Events.postEvent(closeEvent);
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Integer getTotalselected() {
		return totalselected;
	}

	public Integer getTotaldataselected() {
		return totaldataselected;
	}

	public void setTotalselected(Integer totalselected) {
		this.totalselected = totalselected;
	}

	public void setTotaldataselected(Integer totaldataselected) {
		this.totaldataselected = totaldataselected;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

}

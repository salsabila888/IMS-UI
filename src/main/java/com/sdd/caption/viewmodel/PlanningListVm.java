package com.sdd.caption.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zel.impl.parser.ParseException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.TplanDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tplan;
import com.sdd.caption.model.TplanListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.StringUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PlanningListVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	
	private Session session;
	private Transaction transaction;
	
	private Muser oUser;
	private TplanListModel model;
	private TplanDAO planDao = new TplanDAO();
	
	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;	
	private Integer year;
	private Integer month;
	private String status;	
	private String productgroup;
	private String producttype;
	private String arg;
	
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	@Wire
	private Combobox cbMonth;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	
	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg) 
			throws ParseException {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) Sessions.getCurrent().getAttribute("oUser");
		
		this.arg = arg;
		
		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});

			grid.setRowRenderer(new RowRenderer<Tplan>() {

				@Override
				public void render(Row row, Tplan data, int index) throws Exception {
					row.getChildren()
					.add(new Label(
							String.valueOf((SysUtils.PAGESIZE * pageStartNumber)
									+ index + 1)));
					row.getChildren().add(new Label(data.getInputtime() != null ? new SimpleDateFormat("dd-MM-YYYY").format(data.getInputtime()) : "-"));
					row.getChildren().add(new Label(data.getProductgroup() != null ? AppData.getProductgroupLabel(data.getProductgroup()) : "-"));
					row.getChildren().add(new Label(data.getAnggaran() != null ? "Rp" + NumberFormat.getInstance().format(data.getAnggaran()) : "-"));
					row.getChildren().add(new Label(data.getTotalqty() != null ? String.valueOf(data.getTotalqty()) : "-"));
					row.getChildren()
							.add(new Label(data.getMemono() != null ? data.getMemono() : "-"));
					row.getChildren().add(new Label(data.getMemodate() != null ? new SimpleDateFormat("dd-MM-YYYY").format(data.getMemodate()) : "-"));

					A a = new A(data.getMemofileori());
					a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							zkSession.setAttribute("reportPath",
									AppUtils.FILES_ROOT_PATH + AppUtils.MEMO_PATH + data.getMemofileori());
							Executions.getCurrent().sendRedirect("/view/docviewer.zul", "_blank");
						}
					});
					row.getChildren().add(a);
					
					row.getChildren().add(new Label(data.getStatus() != null ? AppData.getStatusLabel(data.getStatus()) : "-"));
					row.getChildren()
					.add(new Label(data.getDecisionby() != null ? data.getInputer(): "-"));
					row.getChildren().add(new Label(data.getDecisiontime() != null ? new SimpleDateFormat("dd-MM-YYYY").format(data.getDecisiontime()) : "-"));
					
					Button btnDetail = new Button();
					btnDetail.setLabel("Detail");
					btnDetail.setAutodisable("self");
					btnDetail.setSclass("btn-info");
					btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<>();
							map.put("obj", data);
							map.put("isDetail", "Y");
							Window win = (Window) Executions.createComponents("/view/planning/planningdata.zul", null, map);
							win.setWidth("70%");
							win.setClosable(true);
							win.doModal();
						}
					});
					
					Button btnEdit = new Button();
					btnEdit.setLabel("Edit");
					btnEdit.setAutodisable("self");
					btnEdit.setSclass("btn-warning");
					btnEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<>();
							map.put("obj", data);
							map.put("arg", arg);
							map.put("isEdit", "Y");
							Window win = (Window) Executions.createComponents("/view/planning/planningentry.zul", null, map);
							win.setWidth("70%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override
								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, PlanningListVm.this, "*");
								}
							});
						}
					});
					
					Button btnDelete = new Button();
					btnDelete.setLabel("Delete");
					btnDelete.setAutodisable("self");
					btnDelete.setSclass("btn-danger");
					btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						
						@SuppressWarnings({ "unchecked", "rawtypes" })
						@Override
						public void onEvent(Event event) throws Exception {
							Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
									Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

										@Override
										public void onEvent(Event event) throws Exception {
											if (event.getName().equals("onOK")) {
												try {
													session = StoreHibernateUtil.openSession();
													transaction = session.beginTransaction();
													
													Tplan obj = planDao.findByFilter(" tplanpk = " + data.getTplanpk() + 
															" and productgroup = '" + arg + "'" );
													
													if (obj != null) 
														planDao.delete(session, obj);				
													
													session.clear();
													planDao.delete(session, data);
													transaction.commit();
													session.close();

													Clients.showNotification(Labels.getLabel("common.delete.success"),
															"info", null, "middle_center", 3000);

													BindUtils.postNotifyChange(null, null, PlanningListVm.this, "obj");
													BindUtils.postNotifyChange(null, null, PlanningListVm.this, "pageTotalSize");

													
												} catch (Exception e) {
													Messagebox.show("Error : " + e.getMessage(),
															WebApps.getCurrent().getAppName(), Messagebox.OK,
															Messagebox.ERROR);
													e.printStackTrace();
												}
												
											}
											
											needsPageUpdate = true;
											doReset();
											
										}

							});		
	

						}
					});

					Div div = new Div();
					div.setClass("btn-group");
					div.appendChild(btnDetail);
					if(data.getStatus().equals(AppUtils.STATUS_PLANNING_WAITAPPROVAL)) {
						div.appendChild(btnEdit);
						div.appendChild(btnDelete);
					}
					row.appendChild(div);
				}
			});
		
		
		String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length; i++) {
	      Comboitem item = new Comboitem();
	      item.setLabel(months[i]);
	      item.setValue(i+1);
	      cbMonth.appendChild(item);
	    }
		doReset();
	}	
		
	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		if (year != null && month != null) {
			filter = "extract(year from inputtime) = " + year + " and "
					+ "extract(month from inputtime) = " + month + " and productgroup = '" + arg + "'";
			if (producttype != null && producttype.trim().length() > 0) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "mproducttype.producttype like '%" + producttype.trim().toUpperCase() + "%'";
			}
			
			System.out.println("user : " + oUser.getUsername() + "and branchlevel" + oUser.getMbranch().getBranchlevel());
			
			if (oUser.getMbranch().getBranchlevel() == 2) {
				filter += " and mregionfk = " + oUser.getMbranch().getMregion().getMregionpk();
			} else if (oUser.getMbranch().getBranchlevel() == 3) {
				filter += " and mbranchfk = " + oUser.getMbranch().getMbranchpk();
			}
			
			needsPageUpdate = true;
			paging.setActivePage(0);
			pageStartNumber = 0;
			refreshModel(pageStartNumber);
		}		
	}
	
	@Command
	@NotifyChange("*")
	public void doReset() {
		productgroup = AppData.getProductgroupLabel(arg);
		year = Calendar.getInstance().get(Calendar.YEAR);
		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		doSearch();
	}
	
	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tplanpk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TplanListModel(activePage, SysUtils.PAGESIZE, filter,
				orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}	
	
	@Command
	public void doExport() {
		try {
			if (filter.length() > 0) {
				List<Tplan> listData = planDao.listByFilter(filter, orderby);
				if (listData != null && listData.size() > 0) {
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet sheet = workbook.createSheet();

					int rownum = 0;
					int cellnum = 0;
					Integer no = 0;
					Integer total = 0;
					org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
					Cell cell = row.createCell(0);
					cell.setCellValue("Daftar Planning " + AppData.getProductgroupLabel(productgroup));
					rownum++;
					row = sheet.createRow(rownum++);
					cell = row.createCell(0);
					cell.setCellValue("Periode");
					cell = row.createCell(1);
					cell.setCellValue(StringUtils.getMonthLabel(month) + " " + year);
					row = sheet.createRow(rownum++);

					Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
					datamap.put(1, new Object[] { "No", "Tanggal Input", "Product", "Anggaran", "Jumlah Unit", "No Memo",
							"Tanggal Memo", "Inputer", "Approver", "Tanggal Approval"});
					no = 2;
					for (Tplan data : listData) {
						datamap.put(no,
								new Object[] { no - 1, datetimeLocalFormatter.format(data.getInputtime()), data.getProductgroup(),
										"Rp" + NumberFormat.getInstance().format(data.getAnggaran()), data.getTotalqty(), data.getMemono(), 
										datetimeLocalFormatter.format(data.getMemodate()), data.getInputer(), 
										data.getDecisionby(), datetimeLocalFormatter
										.format(data.getDecisiontime()) });
						no++;
						total += data.getTotalqty();
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
					String filename = "CAPTION_DAFTAR_USULAN_PENGADAAN_" + new SimpleDateFormat("yyMMddHHmm").format(new Date())
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
	
	@Command
	@NotifyChange("*")
	public void doAdd(@BindingParam("item") String item) { 
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("arg", arg);
			map.put("isDetail", "Y");
			Window win = (Window) Executions.createComponents("/view/planning/planningentry.zul", null, map);
			win.setWidth("70%");
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					doReset();
					BindUtils.postNotifyChange(null, null, PlanningListVm.this, "*");
				}
			});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	public int getPageStartNumber() {
		return pageStartNumber;
	}
	public void setPageStartNumber(int pageStartNumber) {
		this.pageStartNumber = pageStartNumber;
	}
	public int getPageTotalSize() {
		return pageTotalSize;
	}
	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}
	public boolean isNeedsPageUpdate() {
		return needsPageUpdate;
	}
	public void setNeedsPageUpdate(boolean needsPageUpdate) {
		this.needsPageUpdate = needsPageUpdate;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductgroup() {
		return productgroup;
	}
	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Muser getoUser() {
		return oUser;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

}

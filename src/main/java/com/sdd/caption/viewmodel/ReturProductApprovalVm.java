package com.sdd.caption.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.TreturnDAO;
import com.sdd.caption.dao.TreturnitemDAO;
import com.sdd.caption.dao.TreturntrackDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.domain.Treturntrack;
import com.sdd.caption.model.TreturnListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class ReturProductApprovalVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private TreturnDAO oDao = new TreturnDAO();

	private TreturnListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;
	private String action;
	private String status;
	private String decisionmemo;
	private String productgroup;
	private String producttype;
	private int flushCounter;

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	private Muser oUser;
	private List<Treturn> objSelected = new ArrayList<Treturn>();

	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Radiogroup rgapproval;
	@Wire
	private Radio rba, rbd;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg,
			@ExecutionArgParam("stats") String stats) throws ParseException {
		Selectors.wireComponents(view, this, false);
		productgroup = arg.trim();
		status = stats.trim();

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
			grid.setRowRenderer(new RowRenderer<Treturn>() {
				@Override
				public void render(Row row, final Treturn data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
					Checkbox check = new Checkbox();
					check.setAttribute("obj", data);
					check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Checkbox checked = (Checkbox) event.getTarget();
							if (checked.isChecked())
								objSelected.add((Treturn) checked.getAttribute("obj"));
							else
								objSelected.remove((Treturn) checked.getAttribute("obj"));
						}
					});
					row.getChildren().add(check);
					row.getChildren().add(new Label(data.getRegid()));
					row.getChildren()
							.add(new Label(AppData.getProductgroupLabel(data.getMproduct().getProductgroup())));
					row.getChildren().add(new Label(dateLocalFormatter.format(data.getInserttime())));
					row.getChildren().add(new Label(
							data.getItemqty() != null ? NumberFormat.getInstance().format(data.getItemqty()) : "0"));
					row.getChildren().add(new Label(data.getInsertedby()));
				}
			});
		}
		doReset();
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		if (status.equals(AppUtils.STATUS_RETUR_WAITAPPROVAL)) {
			rba.setValue(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH + AppUtils.STATUS_RETUR_WAITAPPROVALOPR);
			rbd.setValue(AppUtils.STATUS_RETUR_DECLINE);
			filter = "productgroup = '" + productgroup + "' and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVAL + "'";
		} else if (status.equals(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH)) {
			rba.setValue(AppUtils.STATUS_RETUR_WAITAPPROVALPFA);
			rbd.setValue(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH);
			filter = "productgroup = '" + productgroup + "' and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH
					+ "'";
		} else if (status.equals(AppUtils.STATUS_RETUR_WAITAPPROVALPFA)) {
			rba.setValue(AppUtils.STATUS_RETUR_APPROVALPFA);
			rbd.setValue(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA);
			filter = "productgroup = '" + productgroup + "' and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALPFA
					+ "'";
		} else if (status.equals(AppUtils.STATUS_RETUR_WAITAPPROVALOPR)) {
			rba.setValue(AppUtils.STATUS_RETUR_APPROVALOPR);
			rbd.setValue(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR);
			filter = "productgroup = '" + productgroup + "' and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALOPR
					+ "'";
		}
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			objSelected = new ArrayList<Treturn>();
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				if (checked) {
					chk.setChecked(true);
					objSelected.add((Treturn) chk.getAttribute("obj"));
				} else {
					chk.setChecked(false);
					objSelected.remove((Treturn) chk.getAttribute("obj"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		decisionmemo = null;
		rgapproval = null;
		action = null;
		objSelected = new ArrayList<Treturn>();
		doSearch();
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "treturnpk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TreturnListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
				if (action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH + AppUtils.STATUS_RETUR_WAITAPPROVALOPR)
						|| action.equals(AppUtils.STATUS_RETUR_DECLINE)
						|| action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH)
						|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH)
						|| action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALPFA)
						|| action.equals(AppUtils.STATUS_RETUR_APPROVALPFA)
						|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA)
						|| action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALOPR)
						|| action.equals(AppUtils.STATUS_RETUR_APPROVALOPR)
						|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR) && decisionmemo != null
								&& decisionmemo.trim().length() > 0) {

					Session session = StoreHibernateUtil.openSession();
					Transaction transaction = session.beginTransaction();
					try {
						for (Treturn obj : objSelected) {
							if (action.equals(
									AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH + AppUtils.STATUS_RETUR_WAITAPPROVALOPR)
									|| action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALPFA)
									|| action.equals(AppUtils.STATUS_RETUR_APPROVALPFA)
									|| action.equals(AppUtils.STATUS_RETUR_APPROVALOPR)) {
								flushCounter = 0;
								if (action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH
										+ AppUtils.STATUS_RETUR_WAITAPPROVALOPR)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN) || obj
											.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)) {
										if (obj.getMreturnreason().getIsDestroy().equals("Y")) {
											obj.setStatus(AppUtils.STATUS_RETUR_DESTROYED);
										} else {
											obj.setStatus(AppUtils.STATUS_RETUR_WAITAPPROVALOPR);
										}
										
										Treturntrack objrt = new Treturntrack();
										objrt.setTreturn(obj);
										objrt.setTracktime(new Date());
										if (obj.getMreturnreason().getIsDestroy().equals("Y")) {
											obj.setStatus(AppUtils.STATUS_RETUR_DESTROYED);
										} else {
											objrt.setTrackstatus(AppUtils.STATUS_RETUR_WAITAPPROVALOPR);
										}
										objrt.setTrackdesc(decisionmemo);
										new TreturntrackDAO().save(session, objrt);
									} else if (obj.getMproduct().getProductgroup()
											.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										if (obj.getMreturnreason().getIsDestroy().equals("Y")) {
											obj.setStatus(AppUtils.STATUS_RETUR_DESTROYED);
										} else {
											obj.setStatus(AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH);
										}
										
										Treturntrack objrt = new Treturntrack();
										objrt.setTreturn(obj);
										objrt.setTracktime(new Date());
										if (obj.getMreturnreason().getIsDestroy().equals("Y")) {
											obj.setStatus(AppUtils.STATUS_RETUR_DESTROYED);
										} else {
											objrt.setTrackstatus(AppUtils.STATUS_RETUR_WAITAPPROVALOPR);
										}
										objrt.setTrackdesc(decisionmemo);
										new TreturntrackDAO().save(session, objrt);
									}
									
								} else if (action.equals(AppUtils.STATUS_RETUR_WAITAPPROVALPFA)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.setStatus(AppUtils.STATUS_RETUR_WAITAPPROVALPFA);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_WAITAPPROVALPFA);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								} else if (action.equals(AppUtils.STATUS_RETUR_APPROVALPFA)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.setStatus(AppUtils.STATUS_DELIVERY_PAKETPROSES);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_DELIVERY_PAKETPROSES);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								} else if (action.equals(AppUtils.STATUS_RETUR_APPROVALOPR)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN) || obj
											.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)) {
										obj.setStatus(AppUtils.STATUS_RETUR_APPROVALOPR);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_APPROVALOPR);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								}
								obj.setDecisionby(oUser.getUserid());
								obj.setDecisiontime(new Date());
								oDao.save(session, obj);

								transaction.commit();
								Clients.showNotification("Proses approval data berhasil", "info", null, "middle_center",
										3000);
							} else if (action.equals(AppUtils.STATUS_RETUR_DECLINE)
									|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH)
									|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA)
									|| action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR)) {
								flushCounter = 0;
								if (action.equals(AppUtils.STATUS_RETUR_DECLINE)) {
									flushCounter = 0;
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN)
											|| obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)
											|| obj.getMproduct().getProductgroup()
													.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.setStatus(AppUtils.STATUS_RETUR_DECLINE);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_DECLINE);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								} else if (action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.setStatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALWILAYAH);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								} else if (action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.setStatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALPFA);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								} else if (action.equals(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR)) {
									if (obj.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN) || obj
											.getMproduct().getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)) {
										obj.setStatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR);
									}
									Treturntrack objrt = new Treturntrack();
									objrt.setTreturn(obj);
									objrt.setTracktime(new Date());
									objrt.setTrackstatus(AppUtils.STATUS_RETUR_DECLINEAPPROVALOPR);
									objrt.setTrackdesc(decisionmemo);
									new TreturntrackDAO().save(session, objrt);
								}
								
								obj.setDecisionby(oUser.getUserid());
								obj.setDecisiontime(new Date());
								oDao.save(session, obj);
								
								List<Treturnitem> objList = new TreturnitemDAO().listByFilter("treturnfk = " + obj.getTreturnpk(), "treturnitempk");
								for(Treturnitem data : objList) {
									data.setItemstatus(obj.getStatus());
									new TreturnitemDAO().save(session, data);
								}

								transaction.commit();
								Clients.showNotification("Proses Decline Approval data berhasil", "info", null,
										"middle_center", 3000);
							}
						}
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDecisionmemo() {
		return decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public int getFlushCounter() {
		return flushCounter;
	}

	public void setFlushCounter(int flushCounter) {
		this.flushCounter = flushCounter;
	}
}
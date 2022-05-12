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
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.bind.annotation.ContextParam;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.MbranchDAO;
import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.MregionDAO;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TordermemoDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Mregion;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Tordermemo;
import com.sdd.caption.model.TorderListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class OrderApprovalVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private TorderListModel model;

	private TorderDAO oDao = new TorderDAO();
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();
	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;
	private String productgroup;
	private String action;
	private String decisionmemo;
	private Integer total;
	private boolean isOPR = false;
	private boolean isJAL = false;

	private Muser oUser;
	private List<Torder> objSelected = new ArrayList<Torder>();

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Radiogroup rgapproval;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg,
			@ExecutionArgParam("isOPR") String opr, @ExecutionArgParam("isJAL") String jal) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		productgroup = arg;
		if (opr != null && opr.equals("Y"))
			isOPR = true;
		else if (jal != null && jal.equals("Y"))
			isJAL = true;

		paging.addEventListener("onPaging", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
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
						if (checked.isChecked())
							objSelected.add((Torder) checked.getAttribute("obj"));
						else
							objSelected.remove((Torder) checked.getAttribute("obj"));
					}
				});
				row.getChildren().add(check);
				row.getChildren().add(new Label(data.getOrderid()));
				row.getChildren().add(new Label(AppData.getProductgroupLabel(data.getMproduct().getProductgroup())));
				row.getChildren().add(new Label(dateLocalFormatter.format(data.getInserttime())));
				row.getChildren().add(new Label(
						data.getItemqty() != null ? NumberFormat.getInstance().format(data.getItemqty()) : ""));
				row.getChildren().add(new Label(data.getInsertedby()));
			}
		});
		doReset();
	}

	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			objSelected = new ArrayList<Torder>();
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				if (checked) {
					chk.setChecked(true);
					objSelected.add((Torder) chk.getAttribute("obj"));
				} else {
					chk.setChecked(false);
					objSelected.remove((Torder) chk.getAttribute("obj"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		try {
			filter = "status = '" + AppUtils.STATUS_ORDER_WAITAPPROVAL + "' and productgroup = '" + productgroup
					+ "' and mbranchfk = " + oUser.getMbranch().getMbranchpk();

//			if (isOPR) {
//				filter = "status = '" + AppUtils.STATUS_ORDER_WAITAPPROVALOPR + "' and productgroup = '" + productgroup
//						+ "'";
//			} else if (isJAL) {
//				filter = "status = '" + AppUtils.STATUS_ORDER_WAITAPPROVALJAL + "' and productgroup = '" + productgroup
//						+ "'";
//			}
			
			for(Torder data : oDao.listNativeByFilter(filter, "torderpk")) {
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
	@NotifyChange("*")
	public void doReset() {
		decisionmemo = null;
		action = null;
		total = 0;
		objSelected = new ArrayList<Torder>();
		doSearch();
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

	@Command
	@NotifyChange("*")
	public void doSave() {
		if (objSelected.size() > 0) {
			if (action != null && action.length() > 0) {
				if (action.equals(AppUtils.STATUS_APPROVED) || (action.equals(AppUtils.STATUS_DECLINE)
						&& decisionmemo != null && decisionmemo.trim().length() > 0)) {
					Session session = StoreHibernateUtil.openSession();
					Transaction transaction = session.beginTransaction();
					try {
						for (Torder obj : objSelected) {
							if (action.equals(AppUtils.STATUS_APPROVED)) {
								obj.setDecisionby(oUser.getUserid());
								obj.setDecisiontime(new Date());
								obj.setStatus(AppUtils.STATUS_ORDER_REQUESTORDER);

//								if (isOPR) {
//									obj.setStatus(AppUtils.STATUS_ORDER_OUTGOINGAPPROVAL);
//
//									Toutgoing toutgoing = new Toutgoing();
//									toutgoing.setMproduct(obj.getMproduct());
//									toutgoing.setTorder(obj);
//									toutgoing.setEntryby(obj.getDecisionby());
//									toutgoing.setEntrytime(new Date());
//									toutgoing.setItemqty(obj.getItemqty());
//									toutgoing.setLastupdated(new Date());
//									toutgoing.setOutgoingid(obj.getOrderid());
//									toutgoing.setProductgroup(obj.getMproduct().getProductgroup());
//									if (obj.getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN)
//											&& obj.getOrdertype().equals(AppUtils.ENTRYTYPE_MANUAL))
//										toutgoing.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGWAITAPPROVALOPR);
//									else
//										toutgoing.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGWAITAPPROVAL);
//									toutgoing.setUpdatedby(oUser.getUserid());
//									toutgoingDao.save(session, toutgoing);
//								} else if (isJAL) {
//									obj.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVALOPR);
//								} else {
//									if (productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)) {
//										obj.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVALJAL);
//									} else if (productgroup.equals(AppUtils.PRODUCTGROUP_TOKEN)) {
//										obj.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVALOPR);
//									} else if (productgroup.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
//										obj.setStatus(AppUtils.STATUS_ORDER_OUTGOINGAPPROVAL);
//
//										Toutgoing toutgoing = new Toutgoing();
//										toutgoing.setMproduct(obj.getMproduct());
//										toutgoing.setTorder(obj);
//										toutgoing.setEntryby(obj.getDecisionby());
//										toutgoing.setEntrytime(new Date());
//										toutgoing.setItemqty(obj.getItemqty());
//										toutgoing.setLastupdated(new Date());
//										toutgoing.setOutgoingid(obj.getOrderid());
//										toutgoing.setProductgroup(obj.getMproduct().getProductgroup());
//										toutgoing.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGWAITAPPROVAL);
//										toutgoing.setUpdatedby(oUser.getUserid());
//										toutgoingDao.save(session, toutgoing);
//									}
//								}
							} else if (action.equals(AppUtils.STATUS_DECLINE)) {
								obj.setStatus(AppUtils.STATUS_ORDER_DECLINE);

								if (obj.getMbranch().getBranchlevel() == 2) {
									Mproducttype objStock = mproducttypeDao
											.findByPk(obj.getMproduct().getMproducttype().getMproducttypepk());
									if (objStock != null) {
										objStock.setStockreserved(objStock.getStockreserved() - obj.getItemqty());
										mproducttypeDao.save(session, objStock);
									}
								} else if (obj.getMbranch().getBranchlevel() == 3) {
									Mregion region = new MregionDAO()
											.findByPk(obj.getMbranch().getMregion().getMregionpk());
									Mbranch branch = new MbranchDAO()
											.findByFilter("branchid = '" + region.getRegionid() + "'");

									Tbranchstock objStock = tbranchstockDao
											.findByFilter("mbranchfk = " + branch.getMbranchpk() + " and mproductfk = "
													+ obj.getMproduct().getMproductpk());
									if (objStock != null) {
										objStock.setStockreserved(objStock.getStockreserved() - obj.getItemqty());
										tbranchstockDao.save(session, objStock);
									}
								}
							}
							oDao.save(session, obj);

							if (decisionmemo != null && decisionmemo.trim().length() > 0) {
								Tordermemo objMemo = new Tordermemo();
								objMemo.setMemo(decisionmemo);
								objMemo.setMemoby(oUser.getUsername());
								objMemo.setMemotime(new Date());
								objMemo.setTorder(obj);
								new TordermemoDAO().save(session, objMemo);
							}
						}
						transaction.commit();
						Clients.showNotification("Submit data approval berhasil", "info", null, "middle_center", 3000);
						doReset();
					} catch (Exception e) {
						transaction.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}
				} else {
					Messagebox.show("Anda harus mengisi field Decision Memo", "Info", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("Silahkan pilih action", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} else

		{
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

	public String getDecisionmemo() {
		return decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
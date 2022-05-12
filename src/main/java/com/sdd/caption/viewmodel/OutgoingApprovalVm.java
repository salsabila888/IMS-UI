package com.sdd.caption.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
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

import com.sdd.caption.dao.MbranchDAO;
import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.MregionDAO;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TembossbranchDAO;
import com.sdd.caption.dao.TembossproductDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.ToutgoingDAO;
import com.sdd.caption.dao.TpersoDAO;
import com.sdd.caption.dao.TpersodataDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Mregion;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Toutgoing;
import com.sdd.caption.domain.Tpersodata;
import com.sdd.caption.handler.FlowHandler;
import com.sdd.caption.model.ToutgoingListModel;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class OutgoingApprovalVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private ToutgoingListModel model;

	private ToutgoingDAO oDao = new ToutgoingDAO();
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();
	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();
	private TorderDAO torderDao = new TorderDAO();
	private TpersoDAO tpersoDao = new TpersoDAO();
	private TpersodataDAO tpersodataDao = new TpersodataDAO();
	private TembossbranchDAO tebDao = new TembossbranchDAO();
	private TembossproductDAO tepDao = new TembossproductDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;
	private String action;
	private String decisionmemo;
	private String productgroup;
	private String productcode;
	private String producttype;
	private String productname;
	private String outgoingid;
	private boolean isOPR = false;

	private Muser oUser;
	private List<Toutgoing> objSelected = new ArrayList<Toutgoing>();

	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Radiogroup rgapproval;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg,
			@ExecutionArgParam("isOPR") String opr) throws Exception {
		Selectors.wireComponents(view, this, false);
		productgroup = arg;
		oUser = (Muser) zkSession.getAttribute("oUser");

		if (opr != null && opr.equals("Y"))
			isOPR = true;

		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);

			}
		});
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Toutgoing>() {

				@Override
				public void render(Row row, final Toutgoing data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
					Checkbox check = new Checkbox();
					check.setAttribute("obj", data);
					check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Checkbox checked = (Checkbox) event.getTarget();
							if (checked.isChecked()) {
								objSelected.add((Toutgoing) checked.getAttribute("obj"));
							} else
								objSelected.remove((Toutgoing) checked.getAttribute("obj"));
						}
					});
					row.getChildren().add(check);
//					A a = new A(data.getOutgoingid());
//					a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//
//						@Override
//						public void onEvent(Event event) throws Exception {
//							String page = "";
//							Map<String, Object> map = new HashMap<>();
//							if (data.getTperso() != null) {
//								page = "/view/perso/persodata.zul";
//								map.put("obj", data.getTperso());
//							} else if (data.getProductgroup().equals(AppUtils.PRODUCTGROUP_PINMAILER)) {
//								page = "/view/pinmailer/pinmailerbranch.zul";
//								//map.put("obj", data.getTorder().getTpinmailerfile());
//							}
//
//							Window win = (Window) Executions.createComponents(page, null, map);
//							win.setWidth("90%");
//							win.setClosable(true);
//							win.doModal();
//						}
//					});
//
//					if (productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)
//							|| productgroup.equals(AppUtils.PRODUCTGROUP_TOKEN))
//						row.getChildren().add(new Label(data.getOutgoingid()));
//					else 
//						row.getChildren().add(a);
					row.getChildren().add(new Label(data.getOutgoingid()));
					row.getChildren().add(new Label(data.getMproduct().getMproducttype().getProducttype()));
					row.getChildren().add(new Label(data.getMproduct().getProductcode()));
					row.getChildren().add(new Label(data.getMproduct().getProductname()));
					row.getChildren().add(new Label(datetimeLocalFormatter.format(data.getEntrytime())));
					row.getChildren().add(new Label(data.getEntryby()));
					row.getChildren().add(new Label(NumberFormat.getInstance().format(data.getItemqty())));
					row.getChildren().add(new Label(data.getMemo()));
				}

			});
		}
		doReset();
	}

	@Command
	@NotifyChange({ "pageTotalSize", "totaldata" })
	public void doSearch() {
		filter = "toutgoing.productgroup = '" + productgroup + "' and toutgoing.status = '"
				+ AppUtils.STATUS_INVENTORY_OUTGOINGWAITAPPROVAL + "'";
		if (oUser.getMbranch().getBranchlevel() == 1)
			filter += " and orderlevel = " + (oUser.getMbranch().getBranchlevel() + 1);
		else if (oUser.getMbranch().getBranchlevel() == 2)
			filter += " and orderlevel = " + (oUser.getMbranch().getBranchlevel() + 1) + " and mregionfk = "
					+ oUser.getMbranch().getMregion().getMregionpk();
		else if (oUser.getMbranch().getBranchlevel() == 3)
			filter += " and orderoutlet != null and mbranchpk = " + oUser.getMbranch().getMbranchpk();

		if (outgoingid != null && outgoingid.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "cardno like '%" + outgoingid.trim().toUpperCase() + "%'";
		}

		if (producttype != null && producttype.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "mproducttype.producttype like '%" + producttype.trim().toUpperCase() + "%'";
		}
		if (productname != null && productname.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "mproduct.productname like '%" + productname.trim().toUpperCase() + "%'";
		}
		if (productcode != null && productcode.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "mproduct.productcode like '%" + productcode.trim().toUpperCase() + "%'";
		}

		if (productgroup.equals(AppUtils.PRODUCTGROUP_CARD)) {
			filter += " and tderivatifproductfk is null";
		}

		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			objSelected = new ArrayList<Toutgoing>();
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				if (checked) {
					chk.setChecked(true);
					objSelected.add((Toutgoing) chk.getAttribute("obj"));
				} else {
					chk.setChecked(false);
					objSelected.remove((Toutgoing) chk.getAttribute("obj"));
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
		action = null;
		objSelected = new ArrayList<Toutgoing>();
		doSearch();
	}

	@NotifyChange({ "pageTotalSize", "totaldata" })
	public void refreshModel(int activePage) {
		try {
			orderby = "toutgoingpk";
			paging.setPageSize(SysUtils.PAGESIZE);
			model = new ToutgoingListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
			if (needsPageUpdate) {
				pageTotalSize = model.getTotalSize(filter);
				needsPageUpdate = false;
			}
			paging.setTotalSize(pageTotalSize);
			grid.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		if (objSelected.size() > 0) {
			boolean isError = false;
			String strError = "";
			if (action != null && action.length() > 0) {
				if (action.equals(AppUtils.STATUS_DECLINE)
						&& (decisionmemo == null || decisionmemo.trim().length() == 0)) {
					Messagebox.show("Anda harus mengisi alasan decline pada field Memo", "Info", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					if (action.equals(AppUtils.STATUS_APPROVED)) {

						for (Toutgoing obj : objSelected) {
							Session session = StoreHibernateUtil.openSession();
							Transaction transaction = session.beginTransaction();
							try {
								obj.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGAPPROVED);
								obj.setDecisionby(oUser.getUserid());
								obj.setDecisiontime(new Date());
								obj.setDecisionmemo(decisionmemo);
								oDao.save(session, obj);

								if (obj.getTperso() != null) {
									obj.getTperso().setStatus(AppUtils.STATUS_PERSO_PRODUKSI);
									tpersoDao.save(session, obj.getTperso());

									List<Tpersodata> listPersodata = tpersodataDao.listByFilter(
											"tperso.tpersopk = " + obj.getTperso().getTpersopk(), "tpersodatapk");
									for (Tpersodata persodata : listPersodata) {
										persodata.getTembossbranch().setStatus(AppUtils.STATUS_PERSO_PRODUKSI);
										new TembossbranchDAO().save(session, persodata.getTembossbranch());
										FlowHandler.doFlow(session, persodata.getTembossbranch(), null,
												AppUtils.PROSES_OUTGOING, decisionmemo, oUser.getUserid());
									}

									obj.getMproduct().getMproducttype().setStockreserved(
											obj.getMproduct().getMproducttype().getStockreserved() - obj.getItemqty());
									obj.getMproduct().getMproducttype().setLaststock(
											obj.getMproduct().getMproducttype().getLaststock() - obj.getItemqty());

									if (obj.getMproduct().getMproducttype().getStockreserved() < 0) {
										obj.getMproduct().getMproducttype().setStockreserved(0);
									}

									mproducttypeDao.save(session, obj.getMproduct().getMproducttype());

								} else if (obj.getTorder() != null) {
									if (productgroup.equals(AppUtils.PRODUCTGROUP_TOKEN)
											|| productgroup.equals(AppUtils.PRODUCTGROUP_PINPAD)
											|| productgroup.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
										obj.getTorder().setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGWAITSCAN);

										if (obj.getTorder().getMbranch().getBranchlevel() == 2) {
											Mproducttype objStock = mproducttypeDao
													.findByPk(obj.getMproduct().getMproducttype().getMproducttypepk());
											if (objStock != null) {
												objStock.setStockreserved(
														objStock.getStockreserved() - obj.getItemqty());
												mproducttypeDao.save(session, objStock);
											}
										} else if (obj.getTorder().getMbranch().getBranchlevel() == 3) {
											Mregion region = new MregionDAO()
													.findByPk(obj.getTorder().getMbranch().getMregion().getMregionpk());
											Mbranch branch = new MbranchDAO()
													.findByFilter("branchid = '" + region.getRegionid() + "'");

											Tbranchstock objStock = tbranchstockDao.findByFilter(
													"mbranchfk = " + branch.getMbranchpk() + " and mproductfk = "
															+ obj.getTorder().getMproduct().getMproductpk());
											if (objStock != null) {
												objStock.setStockreserved(
														objStock.getStockreserved() - obj.getItemqty());
												tbranchstockDao.save(session, objStock);
											}
										}
									} else {
										obj.getTorder().setStatus(AppUtils.STATUS_ORDER_PRODUKSI);

										if (obj.getTorder().getMbranch().getBranchlevel() == 2) {
											Mproducttype objStock = mproducttypeDao
													.findByPk(obj.getMproduct().getMproducttype().getMproducttypepk());
											if (objStock != null) {
												objStock.setStockreserved(
														objStock.getStockreserved() - obj.getItemqty());
												mproducttypeDao.save(session, objStock);
											}
										} else if (obj.getTorder().getMbranch().getBranchlevel() == 3) {
											Mregion region = new MregionDAO()
													.findByPk(obj.getTorder().getMbranch().getMregion().getMregionpk());
											Mbranch branch = new MbranchDAO()
													.findByFilter("branchid = '" + region.getRegionid() + "'");

											Tbranchstock objStock = tbranchstockDao.findByFilter(
													"mbranchfk = " + branch.getMbranchpk() + " and mproductfk = "
															+ obj.getTorder().getMproduct().getMproductpk());
											if (objStock != null) {
												objStock.setStockreserved(
														objStock.getStockreserved() - obj.getItemqty());
												tbranchstockDao.save(session, objStock);
											}
										}
									}
									torderDao.save(session, obj.getTorder());

								}

								transaction.commit();
							} catch (HibernateException e) {
								transaction.rollback();
								isError = true;
								if (strError.length() > 0)
									strError += ". \n";
								strError += e.getMessage();
								e.printStackTrace();
							} catch (Exception e) {
								transaction.rollback();
								isError = true;
								if (strError.length() > 0)
									strError += ". \n";
								strError += e.getMessage();
								e.printStackTrace();
							} finally {
								session.close();
							}

						}
					} else if (action.equals(AppUtils.STATUS_DECLINE)) {
						for (Toutgoing obj : objSelected) {
							Session session = StoreHibernateUtil.openSession();
							Transaction transaction = session.beginTransaction();
							try {
								obj.setStatus(AppUtils.STATUS_INVENTORY_OUTGOINGDECLINE);
								obj.setDecisionby(oUser.getUserid());
								obj.setDecisiontime(new Date());
								obj.setDecisionmemo(decisionmemo);
								oDao.save(session, obj);

								obj.getMproduct().getMproducttype().setStockreserved(
										obj.getMproduct().getMproducttype().getStockreserved() - obj.getItemqty());

								if (obj.getMproduct().getMproducttype().getStockreserved() < 0) {
									obj.getMproduct().getMproducttype().setStockreserved(0);
								}

								mproducttypeDao.save(session, obj.getMproduct().getMproducttype());

								if (obj.getTperso() != null) {
									obj.getTperso().setStatus(AppUtils.STATUS_PERSO_OUTGOINGDECLINE);
									tpersoDao.save(session, obj.getTperso());

									obj.getTperso().getTembossproduct().setStatus(AppUtils.STATUS_ORDER);
									obj.getTperso().getTembossproduct()
											.setTotalproses(obj.getTperso().getTembossproduct().getTotalproses()
													- obj.getTperso().getTotaldata());
									obj.getTperso().getTembossproduct()
											.setOrderos(obj.getTperso().getTembossproduct().getTotaldata()
													- obj.getTperso().getTembossproduct().getTotalproses());
									tepDao.save(session, obj.getTperso().getTembossproduct());

									List<Tpersodata> listPersodata = tpersodataDao.listByFilter(
											"tperso.tpersopk = " + obj.getTperso().getTpersopk(), "tpersodatapk");
									for (Tpersodata persodata : listPersodata) {
										persodata.getTembossbranch().setTotalproses(0);
										persodata.getTembossbranch()
												.setTotalos(persodata.getTembossbranch().getTotaldata());
										persodata.getTembossbranch().setStatus(AppUtils.STATUSBRANCH_PENDINGPRODUKSI);
										tebDao.save(session, persodata.getTembossbranch());

									}
								} else if (obj.getTorder() != null) {
									obj.getTorder().setStatus(AppUtils.STATUS_ORDER_OUTGOINGDECLINE);
									torderDao.save(session, obj.getTorder());

//									if (obj.getTorder().getTpinmailerfile() != null) {
//										obj.getTorder().getTpinmailerfile().setStatus(AppUtils.STATUS_ORDER);
//										tpinmailerDao.save(session, obj.getTorder().getTpinmailerfile());
//									}

								}
								transaction.commit();
							} catch (HibernateException e) {
								transaction.rollback();
								isError = true;
								if (strError.length() > 0)
									strError += ". \n";
								strError += e.getMessage();
								e.printStackTrace();
							} catch (Exception e) {
								transaction.rollback();
								isError = true;
								if (strError.length() > 0)
									strError += ". \n";
								strError += e.getMessage();
								e.printStackTrace();
							} finally {
								session.close();
							}
						}
					}

					if (isError)
						Messagebox.show(strError, WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
					else {
						Clients.showNotification("Proses approval order data berhasil", "info", null, "middle_center",
								3000);

						doReset();
					}
				}

			} else {
				Messagebox.show("Silahkan pilih keputusan", "Info", Messagebox.OK, Messagebox.INFORMATION);
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

	public String getDecisionmemo() {
		return decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}

	public String getOutgoingid() {
		return outgoingid;
	}

	public void setOutgoingid(String outgoingid) {
		this.outgoingid = outgoingid;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
}

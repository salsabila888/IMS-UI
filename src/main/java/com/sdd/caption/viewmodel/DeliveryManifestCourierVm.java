package com.sdd.caption.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.McourierDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TdeliveryDAO;
import com.sdd.caption.dao.TdeliverycourierDAO;
import com.sdd.caption.domain.Mcourier;
import com.sdd.caption.domain.Mcouriervendor;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tdeliverycourier;
import com.sdd.caption.domain.Vcouriervendorsumdata;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class DeliveryManifestCourierVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private TdeliveryDAO oDao = new TdeliveryDAO();
	
	private Mcourier objCourier;

	private String productgroup;
	private String couriercode;
	private int totalcard;
	private int totaltoken;
	private int totalpinpad;
	private int totaldoc;
	private int totalsupplies;
	private int totalpinmailer;
	private String isurgent;

	private Map<String, Integer> map = new HashMap<>();
	private List<Mcouriervendor> listVendor = new ArrayList<>();
	private List<Tdelivery> objList = new ArrayList<>();
	private List<Tdelivery> listSelected = new ArrayList<>();

	private SimpleDateFormat datelocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	
	@Wire
	private Grid gridCard;
	@Wire
	private Grid gridToken;
	@Wire
	private Grid gridPinpad;
	@Wire
	private Grid gridPinmailer;
	@Wire
	private Grid gridDocument;
	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		try {
			listVendor = AppData.getMcouriervendor();
			doReset();
			grid.setRowRenderer(new RowRenderer<Tdelivery>() {

				@Override
				public void render(Row row, final Tdelivery data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					Checkbox chkbox = new Checkbox();
					chkbox.setChecked(true);
					chkbox.setAttribute("obj", data);
					chkbox.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Checkbox checked = (Checkbox) event.getTarget();
							Tdelivery obj = (Tdelivery) checked.getAttribute("obj");
							if (checked.isChecked()) {
								listSelected.add(obj);
							} else {
								listSelected.remove(obj);
							}
							BindUtils.postNotifyChange(null, null, DeliveryManifestCourierVm.this, "total");
						}
					});
					row.getChildren().add(chkbox);

					A a = new A(data.getDlvid());
					a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							Map<String, Object> map = new HashMap<>();
							map.put("obj", data);

							Window win = (Window) Executions.createComponents("/view/delivery/deliverydata.zul", null,
									map);
							win.setWidth("90%");
							win.setClosable(true);
							win.doModal();
						}
					});
					row.getChildren().add(a);
					row.getChildren().add(new Label(datelocalFormatter.format(data.getProcesstime())));
					row.getChildren()
							.add(new Label(
									data.getTotaldata() != null ? NumberFormat.getInstance().format(data.getTotaldata())
											: "0"));
					row.getChildren().add(new Label(data.getMbranch().getBranchname()));
					row.getChildren().add(new Label(data.getMbranch().getBranchaddress()));
					row.getChildren().add(new Label(data.getMbranch().getBranchcity()));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	private void refreshSummary() {
		try {
			totalcard = 0;
			totaltoken = 0;
			totalpinpad = 0;
			totaldoc = 0;
			totalsupplies = 0;
			totalpinmailer = 0;

			gridCard.getRows().getChildren().clear();
			for (Mcouriervendor obj : listVendor) {
				Row row = new Row();
				row.appendChild(new Label(obj.getVendorcode()));
				row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_CARD + obj.getVendorcode() + "M") != null
						? String.valueOf(map.get(AppUtils.PRODUCTGROUP_CARD + obj.getVendorcode() + "M"))
						: "0"));
				// row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_CARD +
				// obj.getVendorcode() + "D") != null ?
				// String.valueOf(map.get(AppUtils.PRODUCTGROUP_CARD + obj.getVendorcode() +
				// "D")) : "0"));
				gridCard.getRows().appendChild(row);

				totalcard += map.get(AppUtils.PRODUCTGROUP_CARD + obj.getVendorcode() + "D") != null
						? map.get(AppUtils.PRODUCTGROUP_CARD + obj.getVendorcode() + "D")
						: 0;
			}

			gridToken.getRows().getChildren().clear();
			for (Mcouriervendor obj : listVendor) {
				Row row = new Row();
				row.appendChild(new Label(obj.getVendorcode()));
				row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_TOKEN + obj.getVendorcode() + "M") != null
						? String.valueOf(map.get(AppUtils.PRODUCTGROUP_TOKEN + obj.getVendorcode() + "M"))
						: "0"));
				// row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_TOKEN +
				// obj.getVendorcode() + "D") != null ?
				// String.valueOf(map.get(AppUtils.PRODUCTGROUP_TOKEN + obj.getVendorcode() +
				// "D")) : "0"));
				gridToken.getRows().appendChild(row);

				totaltoken += map.get(AppUtils.PRODUCTGROUP_TOKEN + obj.getVendorcode() + "D") != null
						? map.get(AppUtils.PRODUCTGROUP_TOKEN + obj.getVendorcode() + "D")
						: 0;

			}

			gridPinpad.getRows().getChildren().clear();
			for (Mcouriervendor obj : listVendor) {
				Row row = new Row();
				row.appendChild(new Label(obj.getVendorcode()));
				row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_PINPAD + obj.getVendorcode() + "M") != null
						? String.valueOf(map.get(AppUtils.PRODUCTGROUP_PINPAD + obj.getVendorcode() + "M"))
						: "0"));
				// row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_PINPAD +
				// obj.getVendorcode() + "D") != null ?
				// String.valueOf(map.get(AppUtils.PRODUCTGROUP_PINPAD + obj.getVendorcode() +
				// "D")) : "0"));
				gridPinpad.getRows().appendChild(row);

				totalpinpad += map.get(AppUtils.PRODUCTGROUP_PINPAD + obj.getVendorcode() + "D") != null
						? map.get(AppUtils.PRODUCTGROUP_PINPAD + obj.getVendorcode() + "D")
						: 0;
			}
			
//			gridPinmailer.getRows().getChildren().clear();
//			for (Mcouriervendor obj : listVendor) {
//				Row row = new Row();
//				row.appendChild(new Label(obj.getVendorcode()));
//				row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_PINMAILER + obj.getVendorcode() + "M") != null
//						? String.valueOf(map.get(AppUtils.PRODUCTGROUP_PINMAILER + obj.getVendorcode() + "M"))
//						: "0"));
//				// row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_SUPPLIES +
//				// obj.getVendorcode() + "D") != null ?
//				// String.valueOf(map.get(AppUtils.PRODUCTGROUP_SUPPLIES + obj.getVendorcode() +
//				// "D")) : "0"));
//				gridPinmailer.getRows().appendChild(row);
//
//				totalpinmailer += map.get(AppUtils.PRODUCTGROUP_PINMAILER + obj.getVendorcode() + "D") != null
//						? map.get(AppUtils.PRODUCTGROUP_PINMAILER + obj.getVendorcode() + "D")
//						: 0;
//			}
			
			gridDocument.getRows().getChildren().clear();
			for (Mcouriervendor obj : listVendor) {
				Row row = new Row();
				row.appendChild(new Label(obj.getVendorcode()));
				row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_DOCUMENT + obj.getVendorcode() + "M") != null
						? String.valueOf(map.get(AppUtils.PRODUCTGROUP_DOCUMENT + obj.getVendorcode() + "M"))
						: "0"));
				// row.appendChild(new Label(map.get(AppUtils.PRODUCTGROUP_SUPPLIES +
				// obj.getVendorcode() + "D") != null ?
				// String.valueOf(map.get(AppUtils.PRODUCTGROUP_SUPPLIES + obj.getVendorcode() +
				// "D")) : "0"));
				gridDocument.getRows().appendChild(row);

				totaldoc += map.get(AppUtils.PRODUCTGROUP_DOCUMENT + obj.getVendorcode() + "D") != null
						? map.get(AppUtils.PRODUCTGROUP_DOCUMENT + obj.getVendorcode() + "D")
						: 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doScan() {
		if (productgroup != null && productgroup.length() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productgroup", productgroup);
			Window win = (Window) Executions.createComponents("/view/delivery/kurirscan.zul", null, map);
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getData() != null) {
						objCourier = (Mcourier) event.getData();
						BindUtils.postNotifyChange(null, null, DeliveryManifestCourierVm.this, "objCourier");
						BindUtils.postNotifyChange(null, null, DeliveryManifestCourierVm.this, "total");
						refreshModel();
					}
				}
			});
		} else {
			Messagebox.show("Silahkan pilih isian grup produk", "Info", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	@Command
	@NotifyChange({ "objCourier", "couriercode" })
	public void doIdentify() {
		try {
			if (productgroup != null && productgroup.length() > 0) {
				if (couriercode != null && couriercode.trim().length() > 0) {
					objCourier = new McourierDAO().findById(couriercode.trim().toUpperCase());
					if (objCourier != null) {
						couriercode = null;
						refreshModel();
					} else {
						Messagebox.show("Data kurir tidak dikenal", "Info", Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			} else {
				Messagebox.show("Silahkan pilih isian grup produk", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshModel() {
		try {
			listSelected = new ArrayList<>();
			objList = oDao.listByFilter("mcouriervendor.mcouriervendorpk = "
					+ objCourier.getMcouriervendor().getMcouriervendorpk() + " and " + "productgroup = '" + productgroup
					+ "' and status = '" + AppUtils.STATUS_DELIVERY_EXPEDITIONORDER + "'", "tdeliverypk");
			listSelected.addAll(objList);
			grid.setModel(new ListModelList<>(objList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			if (grid.getRows() != null && grid.getRows().getChildren() != null) {
				List<Row> components = grid.getRows().getChildren();
				for (Row comp : components) {
					Checkbox chk = (Checkbox) comp.getChildren().get(1);
					Tdelivery obj = (Tdelivery) chk.getAttribute("obj");
					if (checked) {
						if (!chk.isChecked()) {
							chk.setChecked(true);
							listSelected.add(obj);
						}
					} else {
						if (chk.isChecked()) {
							chk.setChecked(false);
							listSelected.remove(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		try {
			objCourier = null;
			listSelected = new ArrayList<>();
			List<Vcouriervendorsumdata> listSum = oDao
					.getCouriervendorsumdata("status = '" + AppUtils.STATUS_DELIVERY_EXPEDITIONORDER + "'");
			for (Vcouriervendorsumdata objSum : listSum) {
				map.put(objSum.getProductgroup() + objSum.getVendorcode() + "M",
						objSum.getTotalmanifest() != null ? objSum.getTotalmanifest() : 0);
				map.put(objSum.getProductgroup() + objSum.getVendorcode() + "D",
						objSum.getTotaldata() != null ? objSum.getTotaldata() : 0);
			}
			if (grid.getRows() != null)
				grid.getRows().getChildren().clear();
			refreshSummary();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			if (listSelected.size() == 0) {
				Messagebox.show("Tidak ada data", "Info", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				Map<String, Object> map = new HashMap<>();

				Window win = (Window) Executions.createComponents("/view/delivery/deliverymanifestprint.zul", null,
						map);
				win.setClosable(true);
				win.doModal();
				win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getData() != null) {
							Map<String, Object> map = (Map<String, Object>) event.getData();
							isurgent = (String) map.get("isurgent");
							doManifest();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange("*")
	public void doManifest() {
		Session session = StoreHibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Tdeliverycourier objForm = new Tdeliverycourier();
			objForm.setMcourier(objCourier);
			objForm.setMcouriervendor(objCourier.getMcouriervendor());
			objForm.setProductgroup(productgroup);
			objForm.setDlvcourierid(new TcounterengineDAO().generateYearMonthCounter(AppUtils.CE_EXPEDITION));
			objForm.setTotaldata(listSelected.size());
			objForm.setStatus(AppUtils.STATUS_DELIVERY_DELIVERY);
			objForm.setProcessedby(oUser.getUserid());
			objForm.setProcesstime(new Date());
			objForm.setIsurgent(isurgent);
			new TdeliverycourierDAO().save(session, objForm);

			for (Tdelivery data : listSelected) {
				data.setTdeliverycourier(objForm);
				data.setStatus(AppUtils.STATUS_DELIVERY_DELIVERY);
				data.setIsurgent(isurgent);
				oDao.save(session, data);
			}

			transaction.commit();
			Clients.showNotification("Submit data manifest delivery kurir berhasil", "info", null, "middle_center",
					2000);
			doReset();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
		} finally {
			session.close();
		}

	}

	public int getTotalcard() {
		return totalcard;
	}

	public void setTotalcard(int totalcard) {
		this.totalcard = totalcard;
	}

	public int getTotaltoken() {
		return totaltoken;
	}

	public void setTotaltoken(int totaltoken) {
		this.totaltoken = totaltoken;
	}

	public int getTotalpinpad() {
		return totalpinpad;
	}

	public void setTotalpinpad(int totalpinpad) {
		this.totalpinpad = totalpinpad;
	}

	public int getTotaldoc() {
		return totaldoc;
	}

	public void setTotaldoc(int totaldoc) {
		this.totaldoc = totaldoc;
	}

	public int getTotalsupplies() {
		return totalsupplies;
	}

	public void setTotalsupplies(int totalsupplies) {
		this.totalsupplies = totalsupplies;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Mcourier getObjCourier() {
		return objCourier;
	}

	public void setObjCourier(Mcourier objCourier) {
		this.objCourier = objCourier;
	}

	public String getCouriercode() {
		return couriercode;
	}

	public void setCouriercode(String couriercode) {
		this.couriercode = couriercode;
	}

	public String getIsurgent() {
		return isurgent;
	}

	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}

	public int getTotalpinmailer() {
		return totalpinmailer;
	}

	public void setTotalpinmailer(int totalpinmailer) {
		this.totalpinmailer = totalpinmailer;
	}

}

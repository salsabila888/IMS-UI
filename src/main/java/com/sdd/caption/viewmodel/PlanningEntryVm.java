package com.sdd.caption.viewmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TplanDAO;
import com.sdd.caption.dao.TplanproductDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tplan;
import com.sdd.caption.domain.Tplanproduct;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PlanningEntryVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Tplan objForm;
	private Tplanproduct objData;

	private TplanDAO oDao = new TplanDAO();
	private TplanproductDAO dataDao = new TplanproductDAO();;
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();

	private String productgroup;
	private String arg;
	private Integer totaldata;
	private String totalrecord;
	private Date memodate;
	private String unit;
	private String filename;
	private String producttype;
	private String cabang;

	private String filter;
	private String orderby;
	private boolean isEdit = false;
	private Media media;

	private List<Mproducttype> objList = new ArrayList<Mproducttype>();
	private List<Tplanproduct> productList = new ArrayList<Tplanproduct>();
	private Map<Integer, Mproducttype> mapData = new HashMap<Integer, Mproducttype>();
	private Map<Integer, Tplanproduct> mapProduct = new HashMap<Integer, Tplanproduct>();
	private Map<Integer, Integer> mapQty = new HashMap<Integer, Integer>();

	@Wire
	private Grid grid;
	@Wire
	private Caption caption;
	@Wire
	private Window winPlanEntry;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg,
			@ExecutionArgParam("isDetail") String isDetail, @ExecutionArgParam("obj") Tplan obj) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		this.arg = arg;
		doReset();

		if (isDetail != null && isDetail.equals("Y")) {
			caption.setVisible(true);
		}

		if (obj != null) {
			objForm = obj;
			productList = dataDao.listByFilter("tplanfk = " + obj.getTplanpk(), "tplanfk desc");
			for (Tplanproduct data : productList) {
				mapData.put(data.getMproducttype().getMproducttypepk(), data.getMproducttype());
				mapQty.put(data.getMproducttype().getMproducttypepk(), data.getUnitqty());
				mapProduct.put(data.getMproducttype().getMproducttypepk(), data);
			}

			totaldata = obj.getTotalqty();
			memodate = obj.getMemodate();
			cabang = obj.getMbranch().getBranchname();

			if (obj.getMemofileori() != null && obj.getMemofileori().trim().length() > 0) {
				filename = obj.getMemofileori();
			}

			isEdit = true;
			caption.setVisible(true);
		}

		grid.setRowRenderer(new RowRenderer<Mproducttype>() {

			@Override
			public void render(Row row, final Mproducttype data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				Intbox intbox = new Intbox();
				intbox.setValue(0);
				intbox.setDisabled(true);
				intbox.setCols(30);
				intbox.setStyle("text-align:right");
				intbox.setFormat("#,###");

				Checkbox check = new Checkbox();
				check.setAttribute("obj", data);
				check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Checkbox checked = (Checkbox) event.getTarget();
						Mproducttype obj = (Mproducttype) checked.getAttribute("obj");
						if (checked.isChecked()) {
							mapData.put(data.getMproducttypepk(), obj);
							intbox.setDisabled(false);

							intbox.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									Intbox intBox = (Intbox) event.getTarget();

									if (mapQty.get(data.getMproducttypepk()) != null) {
										totaldata -= mapQty.get(data.getMproducttypepk());
										mapQty.remove(data.getMproducttypepk());
									}

									mapQty.put(data.getMproducttypepk(), intBox.getValue());
									totaldata += mapQty.get(data.getMproducttypepk());
									System.out.println("TOTAL DATA : " + totaldata);
									BindUtils.postNotifyChange(null, null, PlanningEntryVm.this, "totaldata");
								}
							});
						} else {
							if (mapQty.get(data.getMproducttypepk()) != null) {
								totaldata -= mapQty.get(data.getMproducttypepk());
								mapQty.remove(data.getMproducttypepk());
							}
							mapData.remove(data.getMproducttypepk());
							intbox.setValue(0);
							intbox.setDisabled(true);
							System.out.println("TOTAL DATA : " + totaldata);
							BindUtils.postNotifyChange(null, null, PlanningEntryVm.this, "totaldata");
						}

					}
				});

				if (mapData.get(data.getMproducttypepk()) != null) {
					check.setChecked(true);
					intbox.setDisabled(false);
					intbox.setValue(mapQty.get(data.getMproducttypepk()));
				}

				row.getChildren().add(check);
				row.getChildren().add(new Label(data.getProducttype()));
				row.getChildren().add(intbox);
			}
		});

	}

//	@Command
//	public void doCheckedall(@BindingParam("checked") Boolean checked) {
//		try {
//			if (grid.getRows() != null && grid.getRows().getChildren() != null) {
//				List<Row> components = grid.getRows().getChildren();
//				for (Row comp : components) {
//					Checkbox chk = (Checkbox) comp.getChildren().get(1);
//					Intbox intbox = (Intbox) comp.getChildren().get(3);
//					Mproducttype obj = (Mproducttype) chk.getAttribute("obj");
//					if (checked) {
//						if (!chk.isChecked()) {
//							chk.setChecked(true);
//							intbox.setDisabled(false);
//							mapData.put(obj.getMproducttypepk(), obj);
//						}
//					} else {
//						if (chk.isChecked()) {
//							chk.setChecked(false);
//							totaldata -= mapQty.get(obj.getMproducttypepk());
//							mapData.remove(obj.getMproducttypepk());
//							mapQty.remove(obj.getMproducttypepk());
//							intbox.setValue(0);
//							intbox.setDisabled(true);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Command
	@NotifyChange("filename")
	public void doBrowseProduct(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		try {
			UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
			media = event.getMedia();
			filename = media.getName();
			if (media != null) {
				objForm.setMemofileid(new TcounterengineDAO().generateCounter("MEMO"));
				objForm.setMemofileori(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			if (mapData.size() > 0) {
				String prefix = "";
				if (arg.equals("01"))
					prefix = "PC";
				else if (arg.equals("02"))
					prefix = "PT";
				else if (arg.equals("03"))
					prefix = "PP";
				else if (arg.equals("04"))
					prefix = "PD";

				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();
				if (!isEdit) {
					objForm.setPlanno(new TcounterengineDAO().generateCounter(prefix));
					objForm.setProductgroup(arg);
				}
				objForm.setMbranch(oUser.getMbranch());
				objForm.setMemodate(memodate);
				objForm.setTotalqty(totaldata);
				objForm.setStatus(AppUtils.STATUS_PLANNING_WAITAPPROVAL);
				oDao.save(session, objForm);

//				if (grid.getRows() != null && grid.getRows().getChildren() != null) {
//					List<Row> components = grid.getRows().getChildren();
//					for (Row comp : components) {
//						Checkbox chk = (Checkbox) comp.getChildren().get(1);
//						if (chk.isChecked()) {
//							Intbox intbox = (Intbox) comp.getChildren().get(3);
//							Mproducttype mproducttype = (Mproducttype) chk.getAttribute("obj");
//							objData = mapProduct.get(mproducttype.getMproducttypepk());
//							if (objData == null) {
//								objData = new Tplanproduct();
//								objData.setTplan(objForm);
//								objData.setMproducttype(mproducttype);
//							}
//							objData.setUnitqty(intbox.getValue());
//							dataDao.save(session, objData);
//						}
//					}
//				}

				for (Entry<Integer, Mproducttype> entry : mapData.entrySet()) {
					Mproducttype mproducttype = entry.getValue();
					objData = mapProduct.get(mproducttype.getMproducttypepk());
					if (objData == null) {
						objData = new Tplanproduct();
						objData.setTplan(objForm);
						objData.setMproducttype(mproducttype);
					}
					objData.setUnitqty(mapQty.get(mproducttype.getMproducttypepk()));
					dataDao.save(session, objData);
				}

				transaction.commit();
				session.close();

				String path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.MEMO_PATH);

				if (media != null) {
					if (media.isBinary()) {
						Files.copy(new File(path + media.getName()), media.getStreamData());
					} else {
						BufferedWriter writer = new BufferedWriter(new FileWriter(path + media.getName()));
						Files.copy(writer, media.getReaderData());
						writer.close();
					}
				}

				if (isEdit) {
					Event closeEvent = new Event("onClose", winPlanEntry, null);
					Events.postEvent(closeEvent);
				}

				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 5000);

				doReset();
			} else {
				Messagebox.show("Tidak ada data yang dipilih.", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSearch() {
		try {
			orderby = "producttype";
			filter = "productgroupcode = '" + arg + "'";

			if (producttype != null && producttype.trim().length() > 0)
				filter += " and producttype like '%" + producttype.toUpperCase() + "%'";

			objList = mproducttypeDao.listByFilter(filter, orderby);
			grid.setModel(new ListModelList<Mproducttype>(objList));

			totalrecord = NumberFormat.getInstance().format(objList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {
		productgroup = AppData.getProductgroupLabel(arg);
		cabang = oUser.getMbranch().getBranchname();
		totaldata = 0;
		totalrecord = "0";
		filename = "";
		memodate = null;
		if (arg.equals("04"))
			unit = arg;
		else
			unit = "02";

		objForm = new Tplan();
		objForm.setInputtime(new Date());
		objForm.setInputer(oUser.getUsername());

		mapData = new HashMap<Integer, Mproducttype>();
		mapQty = new HashMap<Integer, Integer>();

		doSearch();
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				BigDecimal anggaran = (BigDecimal) ctx.getProperties("anggaran")[0].getValue();
				String memono = (String) ctx.getProperties("memono")[0].getValue();

				if (anggaran == null)
					this.addInvalidMessage(ctx, "anggaran", Labels.getLabel("common.validator.empty"));
				if (memono == null || "".equals(memono.trim()))
					this.addInvalidMessage(ctx, "memono", Labels.getLabel("common.validator.empty"));
				if (memodate == null)
					this.addInvalidMessage(ctx, "memodate", Labels.getLabel("common.validator.empty"));

			}
		};
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Tplan getObjForm() {
		return objForm;
	}

	public void setObjForm(Tplan objForm) {
		this.objForm = objForm;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Date getMemodate() {
		return memodate;
	}

	public void setMemodate(Date memodate) {
		this.memodate = memodate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(String totalrecord) {
		this.totalrecord = totalrecord;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}
}

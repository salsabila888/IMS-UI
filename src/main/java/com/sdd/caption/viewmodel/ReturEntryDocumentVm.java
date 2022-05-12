package com.sdd.caption.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TbranchstockitemDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TreturnDAO;
import com.sdd.caption.dao.TreturnitemDAO;
import com.sdd.caption.dao.TreturntrackDAO;
import com.sdd.caption.domain.Mlettertype;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Mreturnreason;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.domain.Treturnmemo;
import com.sdd.caption.domain.Treturntrack;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class ReturEntryDocumentVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Treturn objForm;
	private Mproduct mproduct;
	private Mreturnreason mreturnreason;
	private Mlettertype mlettertype;
	private Integer branchlevel;

	private TreturnDAO oDao = new TreturnDAO();
	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();

	private List<Tbranchstockitem> inList = new ArrayList<>();
	private List<Tbranchstockitem> objList = new ArrayList<>();
	private List<String> listData = new ArrayList<>();

	private String unit;
	private String itemno;
	private String itemnoend;
	private String memo;
	private Integer totaldata;
	private String lettertype;
	private String prefix;

	@Wire
	private Row outlet, rowLettertype;
	@Wire
	private Combobox cbProduct, cbReason, cbLettertype;
	@Wire
	private Grid grid;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		branchlevel = oUser.getMbranch().getBranchlevel();

		doReset();

		grid.setRowRenderer(new RowRenderer<Tbranchstockitem>() {

			@Override
			public void render(final Row row, final Tbranchstockitem data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				row.getChildren().add(new Label(data.getItemno()));
				Button btn = new Button("Cancel");
				btn.setAutodisable("self");
				btn.setSclass("btn btn-danger btn-sm");
				btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
								Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

									@Override
									public void onEvent(Event event) throws Exception {
										inList.remove(data);
										listData.remove(data.getItemno().trim());
										totaldata = totaldata - 1;
										refresh();

										BindUtils.postNotifyChange(null, null, ReturEntryDocumentVm.this, "totaldata");
									}
								});
					}
				});

				Div div = new Div();
				div.appendChild(btn);
				row.getChildren().add(div);
			}
		});

	}

	@NotifyChange("*")
	@Command
	public void doRegister() {
		try {
			if (branchlevel == 2) {
				Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk()
						+ " and mproductfk = " + mproduct.getMproductpk());

				objList = new TbranchstockitemDAO().listNativeByFilter(
						"tbranchstockfk = " + objStock.getTbranchstockpk() + " and numerator between " + itemno.trim()
								+ " and " + itemnoend.trim() + " and status = '" + AppUtils.STATUS_SERIALNO_ENTRY + "'",
						"tbranchstockitempk");

				if (objList.size() > 0) {
					for (Tbranchstockitem data : objList) {
						if (!listData.contains(data.getItemno().trim())) {
							inList.add(data);
							listData.add(data.getItemno().trim());
							totaldata++;
						}
					}
				} else {
					Messagebox.show("Data tidak ditemukan.", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk()
						+ " and mproductfk = " + mproduct.getMproductpk() + " and outlet = '00'");

				objList = new TbranchstockitemDAO().listNativeByFilter(
						"tbranchstockfk = " + objStock.getTbranchstockpk() + " and numerator between " + itemno.trim()
								+ " and " + itemnoend.trim() + " and status = '" + AppUtils.STATUS_SERIALNO_ENTRY + "'",
						"tbranchstockitempk");

				if (objList.size() > 0) {
					for (Tbranchstockitem data : objList) {
						if (!listData.contains(data.getItemno().trim())) {
							if (data.getItemno().equals(prefix.trim().toUpperCase() + data.getNumerator().toString())) {
								inList.add(data);
								listData.add(data.getItemno().trim());
								totaldata++;
							}
						}
					}
				} else {
					Messagebox.show("Data tidak ditemukan.", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			}

			refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		if (inList.size() > 0) {
			try {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();

				objForm.setRegid(new TcounterengineDAO().generateCounter(AppUtils.ID_DOCUMENT_PRODUCTION));
				objForm.setReturnlevel(oUser.getMbranch().getBranchlevel());
				objForm.setStatus(AppUtils.STATUS_RETUR_WAITAPPROVAL);
				objForm.setItemqty(totaldata);
				objForm.setMproduct(mproduct);
				objForm.setMreturnreason(mreturnreason);
				objForm.setInsertedby(oUser.getUsername());
				System.out.println(objForm.getMreturnreason().getReturnreason());
				if (objForm.getMreturnreason().getIsDestroy().equals("Y")) {
					objForm.setLettertype(mlettertype.getLettertype());
				} else {
					objForm.setLettertype(null);
				}

				oDao.save(session, objForm);

				for (Tbranchstockitem data : inList) {
					Treturnitem objData = new Treturnitem();
					objData.setItemno(data.getItemno());
					objData.setItemstatus(AppUtils.STATUS_RETUR_WAITAPPROVAL);
					objData.setTreturn(objForm);
					new TreturnitemDAO().save(session, objData);
				}

				Treturnmemo objMemo = new Treturnmemo();
				objMemo.setMemo(memo);
				objMemo.setMemoby(oUser.getUsername());
				objMemo.setMemotime(new Date());
				objMemo.setTreturn(objForm);
				new TreturnDAO().save(session, objForm);

				Treturntrack objTrack = new Treturntrack();
				objTrack.setTreturn(objForm);
				objTrack.setTrackstatus(AppUtils.STATUS_RETUR_WAITAPPROVAL);
				objTrack.setTracktime(new Date());
				new TreturntrackDAO().save(session, objTrack);

				transaction.commit();
				session.close();

				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 5000);

				doReset();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("Tidak ada data.", "Info", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	@NotifyChange("*")
	public void refresh() {
		grid.setModel(new ListModelList<Tbranchstockitem>(inList));
	}

	@Command
	@NotifyChange("*")
	public void doChange() {
		if (mreturnreason.getIsDestroy().equals("Y")) {
			rowLettertype.setVisible(true);
		} else {
			rowLettertype.setVisible(false);
		}
	}

	@NotifyChange("*")
	public void doReset() {
		objForm = new Treturn();
		objForm.setInserttime(new Date());
		objForm.setMbranch(oUser.getMbranch());
		memo = "";
		totaldata = 0;
		itemno = "";
		itemnoend = "";
		inList = new ArrayList<Tbranchstockitem>();
		objList = new ArrayList<Tbranchstockitem>();
		listData = new ArrayList<String>();
		cbProduct.setValue(null);
		cbReason.setValue(null);
		cbLettertype.setValue(null);
		mproduct = null;
		mreturnreason = null;

		unit = "04";
		refresh();
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
//				try {
//					Mproduct mproduct = (Mproduct) ctx.getProperties("mproduct")[0].getValue();
//					if (mproduct == null)
//						this.addInvalidMessage(ctx, "mproduct", Labels.getLabel("common.validator.empty"));
//
//					Mreturnreason mreturnreason = (Mreturnreason) ctx.getProperties("mreturnreason")[0].getValue();
//					if (mreturnreason == null)
//						this.addInvalidMessage(ctx, "mreturnreason", Labels.getLabel("common.validator.empty"));
//
//					if (memo == null || "".trim().equals(memo))
//						this.addInvalidMessage(ctx, "memo", Labels.getLabel("common.validator.empty"));
//					
//					if(objForm.getMreturnreason().getIsDestroy().equals("Y")) {
//						Mlettertype mlettertype = (Mlettertype) ctx.getProperties("mlettertype")[0].getValue();
//						if (mlettertype == null)
//							this.addInvalidMessage(ctx, "mlettertype", Labels.getLabel("common.validator.empty"));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		};
	}

	public ListModelList<Mproduct> getMproductmodel() {
		ListModelList<Mproduct> lm = null;
		try {
			lm = new ListModelList<Mproduct>(AppData.getMproduct("productgroup = '04'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mreturnreason> getMreturnreasonmodel() {
		ListModelList<Mreturnreason> lm = null;
		try {
			lm = new ListModelList<Mreturnreason>(AppData.getMreturnreason());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mlettertype> getMlettertypemodel() {
		ListModelList<Mlettertype> lm = null;
		try {
			lm = new ListModelList<Mlettertype>(AppData.getMlettertype("productgroup = '04'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getItemnoend() {
		return itemnoend;
	}

	public void setItemnoend(String itemnoend) {
		this.itemnoend = itemnoend;
	}

	public Treturn getObjForm() {
		return objForm;
	}

	public void setObjForm(Treturn objForm) {
		this.objForm = objForm;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	public Mreturnreason getMreturnreason() {
		return mreturnreason;
	}

	public void setMreturnreason(Mreturnreason mreturnreason) {
		this.mreturnreason = mreturnreason;
	}

	public Mlettertype getMlettertype() {
		return mlettertype;
	}

	public void setMlettertype(Mlettertype mlettertype) {
		this.mlettertype = mlettertype;
	}

	public String getLettertype() {
		return lettertype;
	}

	public void setLettertype(String lettertype) {
		this.lettertype = lettertype;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}

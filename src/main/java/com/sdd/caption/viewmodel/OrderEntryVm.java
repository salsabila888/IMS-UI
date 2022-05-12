package com.sdd.caption.viewmodel;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;

import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TordermemoDAO;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Tordermemo;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class OrderEntryVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Torder objForm;
	private TorderDAO oDao = new TorderDAO();

	private String cabang;
	private String wilayah;
	private String productgroup;
	private String unit;
	private String memo;
	private String arg;

	@Wire
	private Row outlet;
	@Wire
	private Combobox cbProduct;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg)
			throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		this.arg = arg;
		doReset();
		if (oUser.getMbranch().getBranchlevel() == 3) {
			outlet.setVisible(true);
			objForm.setOrderoutlet("00");
		} else {
			objForm.setOrderoutlet("00");
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {

			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			if (arg.equals(AppUtils.PRODUCTGROUP_CARD))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.BATCHCODE_CARD));
			else if (arg.equals(AppUtils.PRODUCTGROUP_TOKEN))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.ID_TOKEN_BRANCH));
			else if (arg.equals(AppUtils.PRODUCTGROUP_PINPAD))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.ID_PINPAD_PRODUCTION));
			else if (arg.equals(AppUtils.PRODUCTGROUP_DOCUMENT))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.ID_DOCUMENT_PRODUCTION));
			else if (arg.equals(AppUtils.PRODUCTGROUP_PINMAILER))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.BATCHCODE_PINMAILER));
			else if (arg.equals(AppUtils.PRODUCTGROUP_CARDPHOTO))
				objForm.setOrderid(new TcounterengineDAO().generateCounter(AppUtils.ID_CARD_PRODUCTION));

			if (arg.equals(AppUtils.PRODUCTGROUP_TOKEN)) {
				objForm.setOrdertype(AppUtils.ENTRYTYPE_MANUAL);
				objForm.setOrderlevel(oUser.getMbranch().getBranchlevel() - 1);
			} else {
				objForm.setOrdertype(AppUtils.ENTRYTYPE_MANUAL_BRANCH);
				objForm.setOrderlevel(oUser.getMbranch().getBranchlevel());
			}

			objForm.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVAL);
			objForm.setMbranch(oUser.getMbranch());
			objForm.setProductgroup(arg);
			objForm.setOrderdate(new Date());
			objForm.setInsertedby(oUser.getUsername());

			oDao.save(session, objForm);

			Tordermemo objMemo = new Tordermemo();
			objMemo.setMemo(memo);
			objMemo.setMemoby(oUser.getUsername());
			objMemo.setMemotime(new Date());
			objMemo.setTorder(objForm);
			new TordermemoDAO().save(session, objMemo);

			transaction.commit();

			session.close();
			
			doReset();
			Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {
		objForm = new Torder();
		objForm.setInserttime(new Date());
		memo = "";
		productgroup = AppData.getProductgroupLabel(arg);
		cabang = oUser.getMbranch().getBranchname();
		wilayah = oUser.getMbranch().getMregion().getRegionname();
		cbProduct.setValue(null);

		if (arg.equals("04"))
			unit = arg;
		else
			unit = "02";
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				try {
					if (oUser.getMbranch().getBranchlevel() == 3) {
						String orderoutlet = (String) ctx.getProperties("orderoutlet")[0].getValue();
						if (orderoutlet == null || "".trim().equals(orderoutlet))
							this.addInvalidMessage(ctx, "orderoutlet", Labels.getLabel("common.validator.empty"));
					}

					Mproduct mproduct = (Mproduct) ctx.getProperties("mproduct")[0].getValue();
					if (mproduct == null)
						this.addInvalidMessage(ctx, "mproduct", Labels.getLabel("common.validator.empty"));

					Integer itemqty = (Integer) ctx.getProperties("itemqty")[0].getValue();
					if (itemqty == null)
						this.addInvalidMessage(ctx, "itemqty", Labels.getLabel("common.validator.empty"));

					if (memo == null || "".trim().equals(memo))
						this.addInvalidMessage(ctx, "memo", Labels.getLabel("common.validator.empty"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	public ListModelList<Mproduct> getMproductmodel() {
		ListModelList<Mproduct> lm = null;
		try {
			lm = new ListModelList<Mproduct>(AppData.getMproduct("productgroup = '" + arg + "'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Torder getObjForm() {
		return objForm;
	}

	public void setObjForm(Torder objForm) {
		this.objForm = objForm;
	}

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}

	public String getWilayah() {
		return wilayah;
	}

	public void setWilayah(String wilayah) {
		this.wilayah = wilayah;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}

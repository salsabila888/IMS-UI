package com.sdd.caption.viewmodel;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TswitchDAO;
import com.sdd.caption.dao.TswitchmemoDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Tswitch;
import com.sdd.caption.domain.Tswitchmemo;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class SwitchingEntryVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Torder obj;
	private Tswitch objForm;
	private Mbranch mbranch;

	private TorderDAO oDao = new TorderDAO();
	private TswitchDAO switchDao = new TswitchDAO();
	private TswitchmemoDAO switchMemoDao = new TswitchmemoDAO();

	private String outletreq;
	private String outletpool;
	private String stockbranchpool;
	private String cabang;
	private String wilayah;
	private String productgroup;
	private String unit;
	private String memo;
	private String arg;
	private Integer branchlevel;

	@Wire
	private Combobox cbBranch;
	@Wire
	private Row rowCabang, rowOutlet;
	@Wire
	private Window winSwitchentry;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Torder obj,
			@ExecutionArgParam("arg") String arg) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		branchlevel = oUser.getMbranch().getBranchlevel();

		this.obj = obj;
		this.arg = arg;
		doReset();

		if (branchlevel == 2) {
			rowOutlet.setVisible(false);
		}

	}

	@Command
	@NotifyChange("stockbranchpool")
	public void doSelect() {
		try {
			String filter = "";
			Tbranchstock objStock = null;
			if (branchlevel == 3) {
				filter = "mbranchfk = " + mbranch.getMbranchpk() + " and mproductfk = "
						+ obj.getMproduct().getMproductpk() + " and outlet = '" + outletpool.trim() + "'";
			} else {
				filter = "mbranchfk = " + mbranch.getMbranchpk() + " and mproductfk = "
						+ obj.getMproduct().getMproductpk();
			}
			objStock = new TbranchstockDAO().findByFilter(filter);

			if (objStock != null) {
				stockbranchpool = String.valueOf(objStock.getStockcabang() - objStock.getStockreserved());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		if (Integer.valueOf(stockbranchpool) > objForm.getItemqty()) {
			try {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();

				obj.setStatus(AppUtils.STATUS_SWITCH_WAITAPPROVAL);
				oDao.save(session, obj);

				objForm.setRegid(new TcounterengineDAO().generateCounter("SW"));
				objForm.setMbranch(oUser.getMbranch());
				objForm.setBranchidreq(obj.getMbranch().getBranchid());
				objForm.setBranchidpool(mbranch.getBranchid());
				objForm.setOutletreq(outletreq);
				if(outletpool != null)
					objForm.setOutletpool(outletpool);
				else
					objForm.setOutletpool("00");
				objForm.setInsertedby(oUser.getUsername());
				objForm.setStatus(AppUtils.STATUS_SWITCH_WAITAPPROVAL);
				objForm.setTorder(obj);
				switchDao.save(session, objForm);

				Tswitchmemo objMemo = new Tswitchmemo();
				objMemo.setMemo(memo);
				objMemo.setMemoby(oUser.getUsername());
				objMemo.setMemotime(new Date());
				objMemo.setTswitch(objForm);
				switchMemoDao.save(session, objMemo);

				transaction.commit();
				session.close();
				Event closeEvent = new Event("onClose", winSwitchentry, null);
				Events.postEvent(closeEvent);
				
				Clients.showNotification("Entri data switching berhasil", "info", null, "middle_center", 5000);
				doReset();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("Stok dari cabang " + mbranch.getBranchname() + " tidak mencukupi.", "Info", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public void doReset() {
		objForm = new Tswitch();
		objForm.setInserttime(new Date());
		objForm.setMproduct(obj.getMproduct());
		objForm.setItemqty(obj.getItemqty());
		
		memo = "";
		productgroup = AppData.getProductgroupLabel(arg);
		outletreq = obj.getOrderoutlet();
		cabang = obj.getMbranch().getBranchname();
		wilayah = obj.getMbranch().getMregion().getRegionname();
//		cbProduct.setValue(null);
		cbBranch.setValue(null);

		if (arg.equals("04"))
			unit = arg;
		else
			unit = "02";
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

	public ListModelList<Mbranch> getMbranchmodel() {
		ListModelList<Mbranch> lm = null;
		try {
			lm = new ListModelList<Mbranch>(
					AppData.getMbranch("mregionfk = " + oUser.getMbranch().getMregion().getMregionpk()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Torder getObj() {
		return obj;
	}

	public void setObj(Torder obj) {
		this.obj = obj;
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

	public Tswitch getObjForm() {
		return objForm;
	}

	public void setObjForm(Tswitch objForm) {
		this.objForm = objForm;
	}

	public String getOutletreq() {
		return outletreq;
	}

	public void setOutletreq(String outletreq) {
		this.outletreq = outletreq;
	}

	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public String getOutletpool() {
		return outletpool;
	}

	public void setOutletpool(String outletpool) {
		this.outletpool = outletpool;
	}

	public String getStockbranchpool() {
		return stockbranchpool;
	}

	public void setStockbranchpool(String stockbranchpool) {
		this.stockbranchpool = stockbranchpool;
	}
}

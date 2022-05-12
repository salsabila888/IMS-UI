package com.sdd.caption.handler;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sdd.caption.dao.TbranchitemtrackDAO;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TbranchstockitemDAO;
import com.sdd.caption.dao.TderivatifdataDAO;
import com.sdd.caption.dao.TembossdataDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.domain.Tbranchitemtrack;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Tembossbranch;
import com.sdd.caption.domain.Tembossdata;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class BranchStockManager {

	static TbranchstockDAO oDao = new TbranchstockDAO();

	public static void manageNonCard(Torder obj, String productgroup) {
		try {
			Session session = StoreHibernateUtil.openSession();
			Transaction transaction = session.beginTransaction();

			Tbranchstock objStock = oDao
					.findByFilter("mbranchfk = " + obj.getMbranch().getMbranchpk() + " and mproductfk = "
							+ obj.getMproduct().getMproductpk() + " and outlet = '" + obj.getOrderoutlet() + "'");
			if (objStock == null) {
				objStock = new Tbranchstock();
				objStock.setMbranch(obj.getMbranch());
				objStock.setMproduct(obj.getMproduct());
				objStock.setStockdelivered(obj.getTotalproses());
				objStock.setStockactivated(0);
				objStock.setStockreserved(0);
				objStock.setProductgroup(obj.getProductgroup());
				objStock.setStockcabang(objStock.getStockdelivered() - objStock.getStockactivated());
				objStock.setOutlet(obj.getOrderoutlet());

			} else {
				objStock.setStockcabang(objStock.getStockcabang() + obj.getTotalproses());
				objStock.setStockdelivered(objStock.getStockdelivered() + obj.getTotalproses());
			}
			oDao.save(session, objStock);

			transaction.commit();
			session.close();

			List<Torderitem> objList = new TorderitemDAO().listByFilter("torderfk = " + obj.getTorderpk(),
					"torderitempk");
			for (Torderitem data : objList) {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();

				Tbranchstockitem item = new Tbranchstockitem();
				item.setItemno(data.getItemno());
				item.setNumerator(data.getNumerator());
				item.setProductgroup(productgroup);
				item.setStatus(AppUtils.STATUS_SERIALNO_ENTRY);
				item.setTbranchstock(objStock);
				new TbranchstockitemDAO().save(session, item);

				Tbranchitemtrack track = new Tbranchitemtrack();
				track.setMproduct(obj.getMproduct());
				track.setItemno(data.getItemno());
				track.setProductgroup(obj.getProductgroup());
				track.setTrackstatus(AppUtils.STATUS_SERIALNO_ENTRY);
				track.setTrackdesc(AppData.getStatusLabel(AppUtils.STATUS_SERIALNO_ENTRY));
				track.setTracktime(new Date());
				new TbranchitemtrackDAO().save(session, track);

				transaction.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void manageCard(Tembossbranch obj) {
		try {
			Session session = null;
			Transaction transaction = null;

			List<Tembossdata> cardList = new TembossdataDAO()
					.listByFilter("tembossbranchfk = " + obj.getTembossbranchpk(), "tembossdatapk");
			for (Tembossdata embossdata : cardList) {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();

				Tbranchstock tbranchstock = oDao.findByFilter("mbranchfk = " + embossdata.getMbranch().getMbranchpk()
						+ " and mproductfk = " + embossdata.getMproduct().getMproductpk() + " and outlet = '"
						+ embossdata.getKlncode() + "'");

				if (tbranchstock == null) {
					tbranchstock = new Tbranchstock();
					tbranchstock.setMbranch(embossdata.getMbranch());
					tbranchstock.setMproduct(embossdata.getMproduct());
					tbranchstock.setStockdelivered(1);
					tbranchstock.setStockactivated(0);
					tbranchstock.setStockreserved(0);
					tbranchstock.setProductgroup(embossdata.getMproduct().getProductgroup());
					tbranchstock.setStockcabang(1);
					tbranchstock.setOutlet(embossdata.getKlncode());
					tbranchstock.setProductgroup(embossdata.getMproduct().getProductgroup());
				} else {
					tbranchstock.setStockcabang(tbranchstock.getStockcabang() + 1);
					tbranchstock.setStockdelivered(tbranchstock.getStockdelivered() + 1);
				}
				oDao.save(session, tbranchstock);

				Tbranchstockitem item = new Tbranchstockitem();
				item.setItemno(embossdata.getCardno());
				item.setProductgroup(embossdata.getMproduct().getProductgroup());
				item.setStatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
				item.setTbranchstock(tbranchstock);
				new TbranchstockitemDAO().save(session, item);

				Tbranchitemtrack track = new Tbranchitemtrack();
				track.setMproduct(embossdata.getMproduct());
				track.setItemno(embossdata.getCardno());
				track.setProductgroup(embossdata.getMproduct().getProductgroup());
				track.setTrackstatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
				track.setTrackdesc(AppData.getStatusLabel(AppUtils.STATUS_DELIVERY_DELIVERYORDER));
				track.setTracktime(new Date());
				new TbranchitemtrackDAO().save(session, track);

				transaction.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void manageCardDerivatif(Tderivatif obj) {
		try {
			Session session = null;
			Transaction transaction = null;

			List<Tderivatifdata> cardList = new TderivatifdataDAO()
					.listByFilter("tderivatiffk = " + obj.getTderivatifpk(), "tderivatifdatapk");
			for (Tderivatifdata data : cardList) {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();

				Tbranchstock tbranchstock = oDao.findByFilter("mbranchfk = " + obj.getMbranch().getMbranchpk()
						+ " and mproductfk = " + obj.getMproduct().getMproductpk() + " and outlet = '"
						+ data.getTembossdata().getKlncode() + "'");

				if (tbranchstock == null) {
					tbranchstock = new Tbranchstock();
					tbranchstock.setMbranch(obj.getMbranch());
					tbranchstock.setMproduct(obj.getMproduct());
					tbranchstock.setOutlet(data.getTembossdata().getKlncode());
					tbranchstock.setStockdelivered(1);
					tbranchstock.setStockactivated(0);
					tbranchstock.setStockreserved(0);
					tbranchstock.setProductgroup(obj.getMproduct().getProductgroup());
					tbranchstock.setStockcabang(1);
					tbranchstock.setProductgroup(obj.getMproduct().getProductgroup());
				} else {
					tbranchstock.setStockcabang(tbranchstock.getStockcabang() + 1);
					tbranchstock.setStockdelivered(tbranchstock.getStockdelivered() + 1);
				}
				oDao.save(session, tbranchstock);

				Tbranchstockitem item = new Tbranchstockitem();
				item.setItemno(data.getCardno());
				item.setProductgroup(obj.getMproduct().getProductgroup());
				item.setStatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
				item.setTbranchstock(tbranchstock);
				new TbranchstockitemDAO().save(session, item);

				Tbranchitemtrack track = new Tbranchitemtrack();
				track.setMproduct(obj.getMproduct());
				track.setItemno(data.getCardno());
				track.setProductgroup(obj.getMproduct().getProductgroup());
				track.setTrackstatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
				track.setTrackdesc(AppData.getStatusLabel(AppUtils.STATUS_DELIVERY_DELIVERYORDER));
				track.setTracktime(new Date());
				new TbranchitemtrackDAO().save(session, track);

				transaction.commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

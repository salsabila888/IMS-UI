package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mproducttype;
/*import com.sdd.caption.domain.Vproductorgstock;
import com.sdd.caption.domain.Vproducttypestock;
import com.sdd.caption.domain.Vproducttypestocksum;
import com.sdd.caption.domain.Vreportestimasi;
import com.sdd.caption.domain.Vreportpagu;
import com.sdd.caption.domain.Vstockproducthistory;*/
import com.sdd.caption.utils.AppUtils;
import com.sdd.caption.domain.Vproductorgstock;
import com.sdd.caption.domain.Vproducttypestock;
import com.sdd.caption.domain.Vproducttypestocksum;
import com.sdd.caption.domain.Vstockproducthistory;
import com.sdd.utils.db.StoreHibernateUtil;

public class MproducttypeDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mproducttype> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mproducttype> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mproducttype "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mproducttype.class).list();		

		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Vproductorgstock> listProductorgstock(String productgroup) throws Exception {		
    	List<Vproductorgstock> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select a.productorg, sum(laststock) as totalstock, totalincoming, totaloutgoing from mproducttype as a left join (" + 
				"select productorg, sum(incomingtotal) as totalincoming, sum(outgoingtotal) as totaloutgoing from Tstockcard where productgroup = '" + productgroup + "' group by productorg order by productorg) as b on a.productorg = b.productorg " + 
				"where a.productgroupcode = '" + productgroup + "' group by a.productorg,totalincoming,totaloutgoing order by a.productorg").addEntity(Vproductorgstock.class).list();
		session.close();
        return oList;
    }
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mproducttype "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int getSumm(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(laststock),0) from Mproducttype "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mproducttype> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mproducttype> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mproducttype where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	public Mproducttype findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mproducttype oForm = (Mproducttype) session.createQuery("From Mproducttype where mproducttypepk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mproducttype order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mproducttype oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mproducttype oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

	/*@SuppressWarnings("unchecked")
	public List<Vreportpagu> getReportPagu(String filter) throws Exception {		
    	List<Vreportpagu> oList = null;
    	session = StoreHibernateUtil.openSession();
		//oList = session.createSQLQuery("select mproducttypepk, productgroupcode, productgroupname, producttype, laststock, stockmin from mproducttype where " + filter).addEntity(Vreportpagu.class).list();
		oList = session.createSQLQuery("select mproducttypepk, productgroupcode, productgroupname, producttype, laststock, stockmin, cast(laststock as decimal)/stockmin as rasio from mproducttype where " + filter).addEntity(Vreportpagu.class).list();
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Vreportestimasi> getReportEstimasi(String filter) throws Exception {		
    	List<Vreportestimasi> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select mproducttypepk, productgroupcode, productgroupname, producttype, laststock, stockmin, laststock/50 as estimasi from mproducttype where " + filter).addEntity(Vreportestimasi.class).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Vproductorgstock> listProductorgstock(String productgroup) throws Exception {		
    	List<Vproductorgstock> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select a.productorg, sum(laststock) as totalstock, totalincoming, totaloutgoing from mproducttype as a left join (" + 
				"select productorg, sum(incomingtotal) as totalincoming, sum(outgoingtotal) as totaloutgoing from Tstockcard where productgroup = '" + productgroup + "' group by productorg order by productorg) as b on a.productorg = b.productorg " + 
				"where a.productgroupcode = '" + productgroup + "' group by a.productorg,totalincoming,totaloutgoing order by a.productorg").addEntity(Vproductorgstock.class).list();
		session.close();
        return oList;
    }*/
	
	public int pageCountStockHistory(String filter) throws Exception {		
		int count = 0;
    	session = StoreHibernateUtil.openSession();
    	count = Integer.parseInt((String) session.createSQLQuery("select count(*) from (select producttype, 'INCOMING' as trxtype, entrytime as trxtime, itemqty, memo "
				+ "from mproducttype join tincoming on mproducttypepk = mproducttypefk where " + filter + " and tincoming.status = '" + AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED + "' " +  
				"union all " + 
				"select mproducttype.producttype, 'OUTGOING' as trxtype, entrytime as trxtime, itemqty, toutgoing.memo " + 
				"from mproducttype join toutgoing on mproducttypepk = mproducttypefk where " + filter + " and toutgoing.status = '" + AppUtils.STATUS_INVENTORY_OUTGOINGAPPROVED + "') as a ").uniqueResult().toString());
		session.close();
        return count;
	}
	
	public Integer sumStockHistory(String filter) throws Exception {		
		Integer count = 0;
    	session = StoreHibernateUtil.openSession();
    	count = Integer.parseInt((String) session.createSQLQuery("select sum(itemqty) from (select producttype, 'INCOMING' as trxtype, entrytime as trxtime, itemqty, memo "
				+ "from mproducttype join tincoming on mproducttypepk = mproducttypefk where " + filter + " and tincoming.status = '" + AppUtils.STATUS_INVENTORY_INCOMINGAPPROVED + "' " +  
				"union all " + 
				"select mproducttype.producttype, 'OUTGOING' as trxtype, entrytime as trxtime, itemqty, toutgoing.memo " + 
				"from mproducttype join toutgoing on mproducttypepk = mproducttypefk where " + filter + " and toutgoing.status = '" + AppUtils.STATUS_INVENTORY_OUTGOINGAPPROVED + "') as a ").uniqueResult().toString());
		session.close();
        return count;
	}
	
	public void updateBlockPagu(Session session) throws Exception {
		session.createQuery("update Mproducttype set isalertstockpagu = 'Y', alertstockpagudate = date(now()), isblockpagu = 'Y', blockpagutime = now() "
				+ "where laststock < stockmin and isalertstockpagu = 'N' and productgroupcode = '" + AppUtils.PRODUCTGROUP_CARD + "'").executeUpdate();   
	}
	
	@SuppressWarnings("unchecked")
	public List<Mproducttype> startsWith(int maxrow, String value, String param) {
		List<Mproducttype> oList = new ArrayList<Mproducttype>();
       	session = StoreHibernateUtil.openSession();
       	if (param != null)
       		oList = session.createSQLQuery("select * from Mproducttype where " + param + " and producttype like '%" + value + "%' order by producttype limit " + maxrow).addEntity(Mproducttype.class).list();       	
       	else oList = session.createSQLQuery("select * from Mproducttype where producttype like '%" + value + "%' order by producttype limit " + maxrow).addEntity(Mproducttype.class).list();
        session.close();
        return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vproducttypestock> listProducttypestock(String productgroup, String productorg, String producttype) throws Exception {
		String filter = producttype.length() == 0 ? "0=0" : "producttype like '%" + producttype.trim().toUpperCase() + "%'";
		String filtersub = producttype.length() == 0 ? "0=0" : "Tstockcard.producttype like '%" + producttype.trim().toUpperCase() + "%'";
    	List<Vproducttypestock> oList = null;    	
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select mproducttypepk, producttype, sum(laststock) as totalstock, totalincoming, totaloutgoing from mproducttype as a left join (" + 
				"select mproducttypefk, sum(incomingtotal) as totalincoming, sum(outgoingtotal) as totaloutgoing from Tstockcard left join Mproducttype on mproducttypefk = mproducttypepk where Tstockcard.productgroup = '" + productgroup + "' and Tstockcard.productorg = '" + productorg + "' and " + filtersub + " group by mproducttypefk order by mproducttypefk) as b on a.mproducttypepk = b.mproducttypefk " + 
				"where productgroupcode = '" + productgroup + "' and productorg = '" + productorg + "' and " + filter + " group by a.mproducttypepk, a.producttype,totalincoming,totaloutgoing order by a.producttype").addEntity(Vproducttypestock.class).list();
		session.close();
        return oList;
    }
	 
	public Vproducttypestocksum getSumProducttypestock(String productgroup, String productorg, String producttype) throws Exception {
		String filter = producttype.length() == 0 ? "0=0" : "producttype like '%" + producttype.trim().toUpperCase() + "%'";
		String filtersub = producttype.length() == 0 ? "0=0" : "Tstockcard.producttype like '%" + producttype.trim().toUpperCase() + "%'";
    	session = StoreHibernateUtil.openSession();
    	Vproducttypestocksum obj = (Vproducttypestocksum) session.createSQLQuery("select sum(totalstock) as totalstock, sum(totalincoming) as totalincoming, sum(totaloutgoing) as totaloutgoing from (select mproducttypepk, producttype, sum(laststock) as totalstock, totalincoming, totaloutgoing from mproducttype as a left join (" + 
				"select mproducttypefk, sum(incomingtotal) as totalincoming, sum(outgoingtotal) as totaloutgoing from Tstockcard left join Mproducttype on mproducttypefk = mproducttypepk where Tstockcard.productgroup = '" + productgroup + "' and Tstockcard.productorg = '" + productorg + "' and " + filtersub + " group by mproducttypefk order by mproducttypefk) as b on a.mproducttypepk = b.mproducttypefk " + 
				"where productgroupcode = '" + productgroup + "' and productorg = '" + productorg + "' and " + filter + " group by a.mproducttypepk, a.producttype,totalincoming,totaloutgoing order by a.producttype) as a").addEntity(Vproducttypestocksum.class).uniqueResult();
		session.close();
        return obj;
    }
	
	@SuppressWarnings("unchecked")
	public List<Vstockproducthistory> listStockProductHistory(int first, int second, String filter, String orderby) throws Exception {		
    	List<Vstockproducthistory> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from (select producttype, 'INCOMING' as trxtype, entrytime as trxtime, itemqty, memo "
				+ "from mproducttype join tincoming on mproducttypepk = mproducttypefk where " + filter + " and tincoming.status = '" + AppUtils.STATUS_APPROVED + "' " + 
				"union all " + 
				"select mproducttype.producttype, 'OUTGOING' as trxtype, entrytime as trxtime, itemqty, toutgoing.memo " + 
				"from mproducttype join toutgoing on mproducttypepk = mproducttypefk where " + filter + " and toutgoing.status = '" + AppUtils.STATUS_APPROVED + "') as a order by " + orderby + " limit " + second + " offset " + first).addEntity(Vstockproducthistory.class).list();
		session.close();
        return oList;
    }
}

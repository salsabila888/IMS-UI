package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tdeliverydata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TdeliverydataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tdeliverydata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tdeliverydata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tdeliverydata join Tpaketdata on tpaketdatafk = tpaketdatapk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tdeliverydata.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(tdeliverydatapk) from Tdeliverydata join Tpaketdata on tpaketdatafk = tpaketdatapk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pageCount2(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tdeliverydata join mproduct on mproductpk = mproductfk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pageCountDlv(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) FROM (SELECT TDELIVERYDATA.*, DLVSLA FROM TDELIVERYDATA JOIN MBRANCH ON MBRANCHFK = MBRANCHPK JOIN MPRODUCT ON MPRODUCTFK = MPRODUCTPK JOIN " + 
				"TDELIVERY ON TDELIVERYDATA.TDELIVERYFK = TDELIVERYPK JOIN TDELIVERYCOURIER ON TDELIVERYCOURIERFK = TDELIVERYCOURIERPK JOIN " + 
				"MCOURIERVENDOR ON TDELIVERYCOURIER.MCOURIERVENDORFK = MCOURIERVENDORPK JOIN TORDERDATA ON TORDERDATA.TDELIVERYFK = TDELIVERYPK " + 
				"AND TORDERDATA.ORDERDATE = TDELIVERYDATA.ORDERDATE AND TORDERDATA.MPRODUCTFK = TDELIVERYDATA.MPRODUCTFK " + 
				"WHERE " + filter + " GROUP BY TDELIVERYDATAPK, DLVSLA, MBRANCH.BRANCHID) as v").uniqueResult().toString());
		session.close();
        return count;
    }
	
	public Integer getSumDlv(String filter) throws Exception {
		Integer count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("SELECT coalesce(sum(quantity),0) FROM (SELECT QUANTITY FROM TDELIVERYDATA JOIN MBRANCH ON MBRANCHFK = MBRANCHPK JOIN MPRODUCT ON MPRODUCTFK = MPRODUCTPK JOIN " + 
    			"TDELIVERY ON TDELIVERYDATA.TDELIVERYFK = TDELIVERYPK JOIN TDELIVERYCOURIER ON TDELIVERYCOURIERFK = TDELIVERYCOURIERPK JOIN " + 
    			"MCOURIERVENDOR ON TDELIVERYCOURIER.MCOURIERVENDORFK = MCOURIERVENDORPK JOIN TORDERDATA ON TORDERDATA.TDELIVERYFK = TDELIVERYPK " + 
    			"AND TORDERDATA.ORDERDATE = TDELIVERYDATA.ORDERDATE AND TORDERDATA.MPRODUCTFK = TDELIVERYDATA.MPRODUCTFK " + 
    			"WHERE " + filter + " GROUP BY TDELIVERYDATAPK, DLVSLA, MBRANCH.BRANCHID) as v").uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tdeliverydata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tdeliverydata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tdeliverydata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tdeliverydata> listManifestDelivery(String filter) throws Exception {		
    	List<Tdeliverydata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tdeliverydata "
				+ "where " + filter )
				.addEntity(Tdeliverydata.class).list();		

		session.close();
        return oList;
    }
	
	public Tdeliverydata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdeliverydata oForm = (Tdeliverydata) session.createQuery("from Tdeliverydata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tdeliverydata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdeliverydata oForm = (Tdeliverydata) session.createQuery("from Tdeliverydata where tdeliverydatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tdeliverydata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tdeliverydata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tdeliverydata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Vsumdate;
import com.sdd.utils.db.StoreHibernateUtil;

public class TderivatifdataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tderivatifdata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tderivatifdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifdata join Tderivatif on tderivatiffk = tderivatifpk "
    			+ "join Tembossdata on tembossdatafk = tembossdatapk join mbranch on tderivatif.mbranchfk = mbranchpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tderivatifdata.class).list();		

		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifdata> inqList(String filter, String orderby) throws Exception {		
    	List<Tderivatifdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifdata join Tderivatif on tderivatiffk = tderivatifpk "
    			+ "join Tembossdata on tembossdatafk = tembossdatapk join mbranch on tderivatif.mbranchfk = mbranchpk "
				+ "where " + filter + " order by " + orderby)
				.addEntity(Tderivatifdata.class).list();		

		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifdata> dataStockList(String filter, String orderby) throws Exception {		
    	List<Tderivatifdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifdata join Tderivatif on tderivatiffk = tderivatifpk "
    			+ "join Tembossdata on tembossdatafk = tembossdatapk where " + filter + " order by " + orderby)
				.addEntity(Tderivatifdata.class).list();		

		session.close();
        return oList;
    }
	
	public Tderivatifdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatifdata oForm = (Tderivatifdata) session.createQuery("from Tderivatifdata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(tderivatifdatapk) from Tderivatifdata join Tderivatif on tderivatiffk = tderivatifpk "
				+ "join Tembossdata on tembossdatafk = tembossdatapk join mbranch on tderivatif.mbranchfk = mbranchpk where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifdata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tderivatifdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tderivatifdata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tderivatifdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatifdata oForm = (Tderivatifdata) session.createQuery("from Tderivatifdata where tderivatifdatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tderivatifdata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tderivatifdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tderivatifdata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<String> listCardno(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		List<String> oList = session.createQuery("select cardno from Tderivatifdata where " + filter).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vsumdate> getGroupOrderdate(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		List<Vsumdate> oList = session.createSQLQuery("select orderdate as date, count(*) as total from Tderivatifdata "
				+ "where " + filter + " group by orderdate order by orderdate").addEntity(Vsumdate.class).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifdata> listDataLampiran(String filter, String orderby) throws Exception {		
    	List<Tderivatifdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifdata join Tderivatifproduct on tderivatifproductfk = tderivatifproductpk "
    			+ "join Mproduct on mproductfk = mproductpk "
				+ "where " + filter + " order by " + orderby)
				.addEntity(Tderivatifdata.class).list();		

		session.close();
        return oList;
    }	
	
}
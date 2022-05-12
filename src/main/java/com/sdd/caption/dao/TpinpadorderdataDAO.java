package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpinpadorderdata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinpadorderdataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinpadorderdata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpinpadorderdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinpadorderdata "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpinpadorderdata.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpinpadorderdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinpadorderdata oForm = (Tpinpadorderdata) session.createQuery("from Tpinpadorderdata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpinpadorderdata "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinpadorderdata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpinpadorderdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinpadorderdata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpinpadorderdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinpadorderdata oForm = (Tpinpadorderdata) session.createQuery("from Tpinpadorderdata where tpinpadorderdatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpinpadorderdata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpinpadorderdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpinpadorderdata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinpadorderdata> listSerialnoLetter(String filter) throws Exception {		
    	List<Tpinpadorderdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from tpinpadorderdata join tpinpaditem on tpinpaditemfk = tpinpaditempk " + 
    			"join torder on tpinpadorderdata.torderfk = torderpk join tpaket on tpaket.torderfk = torderpk " + 
    			"join tpaketdata on tpaketfk = tpaketpk join tdeliverydata on tpaketdatafk = tpaketdatapk " + 
    			"join tdelivery on tdeliveryfk = tdeliverypk where " + filter)
				.addEntity(Tpinpadorderdata.class).list();		

		session.close();
        return oList;
    }

}

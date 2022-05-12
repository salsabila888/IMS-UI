package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mlettertype;
import com.sdd.utils.db.StoreHibernateUtil;

public class MlettertypeDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mlettertype> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mlettertype> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mlettertype "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mlettertype.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mlettertype "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mlettertype> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mlettertype> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mlettertype where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mlettertype findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mlettertype oForm = (Mlettertype) session.createQuery("from Mlettertype where mlettertypepk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mlettertype order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mlettertype oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mlettertype oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


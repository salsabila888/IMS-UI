package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tdeliverycourier;
import com.sdd.utils.db.StoreHibernateUtil;

public class TdeliverycourierDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tdeliverycourier> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tdeliverycourier> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tdeliverycourier "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tdeliverycourier.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tdeliverycourier "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tdeliverycourier> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tdeliverycourier> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tdeliverycourier where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tdeliverycourier findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdeliverycourier oForm = (Tdeliverycourier) session.createQuery("from Tdeliverycourier where tdeliverycourierpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tdeliverycourier findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdeliverycourier oForm = (Tdeliverycourier) session.createQuery("from Tdeliverycourier where deliverycourierid = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tdeliverycourier order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tdeliverycourier oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tdeliverycourier oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

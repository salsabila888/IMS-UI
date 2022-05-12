package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpersouploaddata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpersouploaddataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpersouploaddata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpersouploaddata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpersouploaddata "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpersouploaddata.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpersouploaddata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersouploaddata oForm = (Tpersouploaddata) session.createQuery("from Tpersouploaddata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(Tpersouploaddatapk) from Tpersouploaddata "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpersouploaddata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpersouploaddata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpersouploaddata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpersouploaddata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersouploaddata oForm = (Tpersouploaddata) session.createQuery("from Tpersouploaddata where Tpersouploaddatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpersouploaddata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpersouploaddata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpersouploaddata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<String> listCardno(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		List<String> oList = session.createQuery("select cardno from Tpersouploaddata where " + filter).list();
		session.close();
		return oList;
	}
	
}
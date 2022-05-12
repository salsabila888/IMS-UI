package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mpersovendor;
import com.sdd.utils.db.StoreHibernateUtil;

public class MpersovendorDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mpersovendor> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mpersovendor> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mpersovendor "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mpersovendor.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mpersovendor "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mpersovendor> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mpersovendor> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mpersovendor where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mpersovendor findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mpersovendor oForm = (Mpersovendor) session.createQuery("from Mpersovendor where mpersovendorpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mpersovendor order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mpersovendor oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mpersovendor oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


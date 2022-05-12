package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tordermemo;
import com.sdd.utils.db.StoreHibernateUtil;

public class TordermemoDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tordermemo> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tordermemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tordermemo join Torder on torderfk = torderpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tordermemo.class).list();		

		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tordermemo> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Tordermemo> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tordermemo where " + filter + " order by " + orderby)
				.addEntity(Tordermemo.class).list();
		session.close();
		return oList;
	}
	
	public Tordermemo findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tordermemo oForm = (Tordermemo) session.createQuery("from Tordermemo where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tordermemo join Torder on torderfk = torderpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tordermemo> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tordermemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tordermemo where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tordermemo findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tordermemo oForm = (Tordermemo) session.createQuery("from Tordermemo where Tordermemopk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tordermemo order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tordermemo oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tordermemo oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	
}

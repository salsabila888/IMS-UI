package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpinpaditem;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinpaditemDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinpaditem> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpinpaditem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinpaditem join Tincoming on tincomingfk = tincomingpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpinpaditem.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpinpaditem findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinpaditem oForm = (Tpinpaditem) session
				.createQuery("from Tpinpaditem where itemno = '" + id + "'")
				.uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpinpaditem findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinpaditem oForm = (Tpinpaditem) session.createQuery("from Tpinpaditem where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpinpaditem join Tincoming on tincomingfk = tincomingpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinpaditem> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpinpaditem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinpaditem where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpinpaditem findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinpaditem oForm = (Tpinpaditem) session.createQuery("from Tpinpaditem where Tpinpaditempk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpinpaditem order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpinpaditem oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpinpaditem oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

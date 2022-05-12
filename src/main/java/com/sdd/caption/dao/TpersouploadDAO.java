package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpersoupload;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpersouploadDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpersoupload> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpersoupload> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpersoupload "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpersoupload.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpersoupload findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersoupload oForm = (Tpersoupload) session.createQuery("from Tpersoupload where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(Tpersouploadpk) from Tpersoupload "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpersoupload> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpersoupload> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpersoupload where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpersoupload findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersoupload oForm = (Tpersoupload) session.createQuery("from Tpersoupload where Tpersouploadpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpersoupload order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpersoupload oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpersoupload oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<String> listCardno(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		List<String> oList = session.createQuery("select cardno from Tpersoupload where " + filter).list();
		session.close();
		return oList;
	}
	
}
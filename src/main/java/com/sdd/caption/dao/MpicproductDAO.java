package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mpicproduct;
import com.sdd.utils.db.StoreHibernateUtil;

public class MpicproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mpicproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mpicproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mpicproduct "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mpicproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mpicproduct "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mpicproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mpicproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mpicproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mpicproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mpicproduct oForm = (Mpicproduct) session.createQuery("from Mpicproduct where mpicproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mpicproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mpicproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mpicproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


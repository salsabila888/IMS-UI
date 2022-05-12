package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tproductmm;
import com.sdd.utils.db.StoreHibernateUtil;

public class TproductmmDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tproductmm> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tproductmm> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tproductmm "
				+ "where " + filter + " order by " + orderby + " offset " + first +" rows fetch next " + second + " rows only")
				.addEntity(Tproductmm.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tproductmm "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tproductmm> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tproductmm> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tproductmm where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tproductmm findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tproductmm oForm = (Tproductmm) session.createQuery("from Tproductmm where Tproductmmpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tproductmm order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	public Tproductmm findByFilterSession(Session session, String filter) throws Exception {
		Tproductmm oForm = (Tproductmm) session.createQuery("from Tproductmm where " + filter).uniqueResult();
		return oForm;
	}
	
	public Tproductmm findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tproductmm oForm = (Tproductmm) session.createQuery("from Tproductmm where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
		
	public void save(Session session, Tproductmm oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tproductmm oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpinmailerfile;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinmailerfileDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinmailerfile> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpinmailerfile> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinmailerfile "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpinmailerfile.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpinmailerfile findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerfile oForm = (Tpinmailerfile) session.createQuery("from Tpinmailerfile where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpinmailerfile "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinmailerfile> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpinmailerfile> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinmailerfile where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpinmailerfile findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerfile oForm = (Tpinmailerfile) session.createQuery("from Tpinmailerfile where tpinmailerfilepk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpinmailerfile order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpinmailerfile oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpinmailerfile oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

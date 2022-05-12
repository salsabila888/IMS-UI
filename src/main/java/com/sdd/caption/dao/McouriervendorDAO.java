package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mcouriervendor;
import com.sdd.utils.db.StoreHibernateUtil;

public class McouriervendorDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mcouriervendor> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mcouriervendor> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mcouriervendor "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mcouriervendor.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mcouriervendor "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mcouriervendor> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mcouriervendor> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mcouriervendor where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mcouriervendor findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mcouriervendor oForm = (Mcouriervendor) session.createQuery("from Mcouriervendor where mcouriervendorpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Mcouriervendor findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mcouriervendor oForm = (Mcouriervendor) session.createQuery("from Mcouriervendor where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mcouriervendor order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mcouriervendor oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mcouriervendor oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


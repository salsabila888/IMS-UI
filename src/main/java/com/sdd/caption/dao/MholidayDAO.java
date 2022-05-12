package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mholiday;
import com.sdd.utils.db.StoreHibernateUtil;

public class MholidayDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mholiday> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mholiday> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mholiday "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mholiday.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mholiday "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mholiday> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mholiday> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mholiday where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mholiday findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mholiday oForm = (Mholiday) session.createQuery("from Mholiday where mholidaypk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mholiday order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mholiday oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mholiday oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


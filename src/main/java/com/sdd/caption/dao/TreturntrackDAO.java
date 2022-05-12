package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.domain.Treturntrack;
import com.sdd.utils.db.StoreHibernateUtil;

public class TreturntrackDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Treturntrack> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Treturntrack> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Treturntrack join Mproduct on mproductfk = mproductpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Treturn.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Treturntrack join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Treturntrack> listByFilter(String filter, String orderby) throws Exception {		
    	List<Treturntrack> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Treturntrack where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Treturntrack> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Treturntrack> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Treturntrack.* from Treturntrack join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Treturntrack.class).list();
		session.close();
        return oList;
    }	
	
	public Treturntrack findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Treturntrack oForm = (Treturntrack) session.createQuery("from Treturntrack where treturntrackpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Treturntrack order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Treturntrack oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Treturntrack oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

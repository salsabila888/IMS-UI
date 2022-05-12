package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Musergroupnotif;
import com.sdd.utils.db.StoreHibernateUtil;

public class MusergroupnotifDAO {
	
	private Session session;
	
	@SuppressWarnings("unchecked")
	public List<Musergroupnotif> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Musergroupnotif> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Musergroupnotif "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Musergroupnotif.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Musergroupnotif "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Musergroupnotif> listByFilter(String filter, String orderby) throws Exception {		
    	List<Musergroupnotif> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Musergroupnotif where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Musergroupnotif findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Musergroupnotif oForm = (Musergroupnotif) session.createQuery("from Musergroupnotif where musergroupnotifpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname, String filter) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Musergroupnotif where " + filter + " order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Musergroupnotif oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Musergroupnotif oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void deleteBySQL(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("delete from Musergroupnotif where "+ filter).executeUpdate();    
    }

}


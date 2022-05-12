package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Trollback;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TrollbackDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Trollback> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Trollback> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from trollback "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Trollback.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from trollback "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Trollback> listByFilter(String filter, String orderby) throws Exception {		
    	List<Trollback> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from trollback where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Trollback findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Trollback oForm = (Trollback) session.createQuery("from trollback where trollbackpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from trollback order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Trollback oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Trollback oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void updateComplaintSql(Session session, Integer fk, String date)
			throws HibernateException, Exception {
		session.createSQLQuery("update trollback set closedtime = '" + date
				+ "' where trollbackpk = " + fk).executeUpdate();
	}
	
	public void updateStatusComplaintSql(Session session, Integer fk, String date)
			throws HibernateException, Exception {
		session.createSQLQuery("update trollback set status = '" + AppUtils.STATUS_CLOSED_COMPLAINT + "', closedtime = '" + date
				+ "' where trollbackpk = " + fk).executeUpdate();
	}

}


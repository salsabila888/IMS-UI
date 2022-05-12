package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mcouriervendorbranch;
import com.sdd.utils.db.StoreHibernateUtil;

public class McouriervendorbranchDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mcouriervendorbranch> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mcouriervendorbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mcouriervendorbranch "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mcouriervendorbranch.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mcouriervendorbranch "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mcouriervendorbranch> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mcouriervendorbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mcouriervendorbranch where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mcouriervendorbranch findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mcouriervendorbranch oForm = (Mcouriervendorbranch) session.createQuery("from Mcouriervendorbranch where mcouriervendorbranchpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mcouriervendorbranch order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mcouriervendorbranch oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mcouriervendorbranch oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

	public void deleteBySQL(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("delete from Mcouriervendorbranch where "+ filter).executeUpdate();
	}
}


package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tembossfile;
import com.sdd.utils.db.StoreHibernateUtil;

public class TembossfileDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tembossfile> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tembossfile> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tembossfile "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tembossfile.class).list();		

		session.close();
        return oList;
    }	
	
	public Tembossfile findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossfile oForm = (Tembossfile) session.createQuery("from Tembossfile where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tembossfile "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tembossfile> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tembossfile> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tembossfile where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tembossfile findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossfile oForm = (Tembossfile) session.createQuery("from Tembossfile where torderpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tembossfile order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tembossfile oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tembossfile oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void updateSql(Session session, String status, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossfile set status = '" + status + "' where " + filter)
				.executeUpdate();
	}
	
	public void deleteByPk(Session session, Integer pk) throws HibernateException, Exception {
		session.createSQLQuery("delete from Tembossfile where torderpk = " + pk)
				.executeUpdate();
    }
}

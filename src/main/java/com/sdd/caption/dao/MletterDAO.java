package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mletter;
import com.sdd.utils.db.StoreHibernateUtil;

public class MletterDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mletter> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mletter> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mletter "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mletter.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mletter "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mletter> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mletter> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mletter where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mletter findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mletter oForm = (Mletter) session.createQuery("from Mletter where mletterpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mletter order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mletter oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mletter oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


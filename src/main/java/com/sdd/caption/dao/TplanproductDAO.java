package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tplanproduct;
import com.sdd.utils.db.StoreHibernateUtil;

public class TplanproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tplanproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tplanproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tplanproduct "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tplanproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tplanproduct "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tplanproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tplanproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tplanproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tplanproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tplanproduct oForm = (Tplanproduct) session.createQuery("from Tplanproduct where Tplanproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tplanproduct findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tplanproduct oForm = (Tplanproduct) session.createQuery("from Tplanproduct where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public String getField(String code) throws Exception {
		session = StoreHibernateUtil.openSession();
		String data = (String) session.createQuery("select description from Tplanproduct where org = '" + code + "'").uniqueResult();
		session.close();
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tplanproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tplanproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tplanproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


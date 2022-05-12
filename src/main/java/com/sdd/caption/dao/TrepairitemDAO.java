package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Trepairitem;
import com.sdd.utils.db.StoreHibernateUtil;

public class TrepairitemDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Trepairitem> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Trepairitem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Trepairitem "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Trepairitem.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Trepairitem "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Trepairitem> listByFilter(String filter, String orderby) throws Exception {		
    	List<Trepairitem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Trepairitem where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Trepairitem> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Trepairitem> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Trepairitem.* from Trepairitem where " + filter + " order by " + orderby).addEntity(Trepairitem.class).list();
		session.close();
        return oList;
    }	
	
	public Trepairitem findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Trepairitem oForm = (Trepairitem) session.createQuery("from Trepairitem where trepairitempk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Trepairitem order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Trepairitem oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Trepairitem oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

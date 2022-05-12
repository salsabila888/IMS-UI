package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Trepairmemo;
import com.sdd.utils.db.StoreHibernateUtil;

public class TrepairmemoDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Trepairmemo> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Trepairmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Trepairmemo "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Trepairmemo.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Trepairmemo "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Trepairmemo> listByFilter(String filter, String orderby) throws Exception {		
    	List<Trepairmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Trepairmemo where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Trepairmemo> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Trepairmemo> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Trepairmemo.* from Trepairmemo where " + filter + " order by " + orderby).addEntity(Trepairmemo.class).list();
		session.close();
        return oList;
    }	
	
	public Trepairmemo findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Trepairmemo oForm = (Trepairmemo) session.createQuery("from Trepairmemo where trepairmemopk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Trepairmemo order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Trepairmemo oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Trepairmemo oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

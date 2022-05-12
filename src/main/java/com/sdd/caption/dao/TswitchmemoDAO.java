package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Tswitchitem;
import com.sdd.caption.domain.Tswitchmemo;
import com.sdd.utils.db.StoreHibernateUtil;

public class TswitchmemoDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tswitchmemo> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tswitchmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tswitchmemo "
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tswitchmemo "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tswitchmemo> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tswitchmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tswitchmemo where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tswitchmemo> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Tswitchmemo> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Tswitchmemo.* from Tswitchmemo where " + filter + " order by " + orderby).addEntity(Tswitchmemo.class).list();
		session.close();
        return oList;
    }	
	
	public Tswitchmemo findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tswitchmemo oForm = (Tswitchmemo) session.createQuery("from Tswitchmemo where tswitchmemopk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tswitchmemo order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tswitchmemo oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tswitchmemo oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

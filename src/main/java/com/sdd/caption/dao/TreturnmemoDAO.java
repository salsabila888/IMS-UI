package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.domain.Treturnmemo;
import com.sdd.utils.db.StoreHibernateUtil;

public class TreturnmemoDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Treturnmemo> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Treturnmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Treturnmemo join Mproduct on mproductfk = mproductpk "
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Treturnmemo join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Treturnmemo> listByFilter(String filter, String orderby) throws Exception {		
    	List<Treturnmemo> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Treturnmemo where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Treturnmemo> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Treturnmemo> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Treturnmemo.* from Treturnmemo join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Treturnmemo.class).list();
		session.close();
        return oList;
    }	
	
	public Treturnmemo findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Treturnmemo oForm = (Treturnmemo) session.createQuery("from Treturnmemo where treturnmemopk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Treturnmemo order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Treturnmemo oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Treturnmemo oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

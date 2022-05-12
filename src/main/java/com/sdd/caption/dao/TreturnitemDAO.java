package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.utils.db.StoreHibernateUtil;

public class TreturnitemDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Treturnitem> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Treturnitem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Treturnitem "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Treturnitem.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Treturnitem "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Treturnitem> listByFilter(String filter, String orderby) throws Exception {		
    	List<Treturnitem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Treturnitem where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Treturnitem> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Treturnitem> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Treturnitem.* from Treturnitem join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Treturnitem.class).list();
		session.close();
        return oList;
    }	
	
	public Treturnitem findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Treturnitem oForm = (Treturnitem) session.createQuery("from Treturnitem where treturnitempk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Treturnitem order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Treturnitem> listDataLampiran(String filter, String orderby) throws Exception {		
    	List<Treturnitem> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Treturnitem where " + filter + " order by " + orderby)
				.addEntity(Treturnitem.class).list();		

		session.close();
        return oList;
    }
		
	public void save(Session session, Treturnitem oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Treturnitem oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mreturnreason;
import com.sdd.utils.db.StoreHibernateUtil;

public class MreturnreasonDAO {

	
	private Session session;
	
	@SuppressWarnings("unchecked")
	public List<Mreturnreason> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mreturnreason> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mreturnreason "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mreturnreason.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mreturnreason "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mreturnreason> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mreturnreason> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mreturnreason where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }
	
	
	public Mreturnreason findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mreturnreason oForm = (Mreturnreason) session.createQuery("from Mreturnreason where mreturnreasonpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mreturnreason order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mreturnreason oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mreturnreason oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }


}

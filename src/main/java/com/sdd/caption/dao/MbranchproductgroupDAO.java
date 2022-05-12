package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mbranchproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class MbranchproductgroupDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mbranchproductgroup> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mbranchproductgroup> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Mbranchproductgroup "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mbranchproductgroup.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mbranchproductgroup "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mbranchproductgroup> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mbranchproductgroup> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mbranchproductgroup where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mbranchproductgroup findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mbranchproductgroup oForm = (Mbranchproductgroup) session.createQuery("from Mbranchproductgroup where Mbranchproductgrouppk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Mbranchproductgroup findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mbranchproductgroup oForm = (Mbranchproductgroup) session.createQuery("from Mbranchproductgroup where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public String getField(String code) throws Exception {
		session = StoreHibernateUtil.openSession();
		String data = (String) session.createQuery("select description from Mbranchproductgroup where org = '" + code + "'").uniqueResult();
		session.close();
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mbranchproductgroup order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Mbranchproductgroup oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mbranchproductgroup oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}


package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tmissingbranch;
import com.sdd.caption.domain.Tmissingproduct;
import com.sdd.caption.domain.Vmissingproduct;
import com.sdd.utils.db.StoreHibernateUtil;

public class TmissingproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tmissingproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tmissingproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tmissingproduct "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tmissingproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select tmissingproductpk from Tmissingproduct "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tmissingproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tmissingproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tmissingproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tmissingproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tmissingproduct oForm = (Tmissingproduct) session.createQuery("from Tmissingproduct where tmissingproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tmissingproduct findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tmissingproduct oForm = (Tmissingproduct) session.createQuery("from Tmissingproduct where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tmissingproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tmissingproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tmissingproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<Vmissingproduct> listGroupby(String filter) throws Exception {		
    	List<Vmissingproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select productcode, isinstant, sum(totaldata) as totaldata from tmissingproduct where " + filter
				+ " group by productcode, isinstant order by productcode, isinstant").addEntity(Vmissingproduct.class).list();
		session.close();
        return oList;
    }

}


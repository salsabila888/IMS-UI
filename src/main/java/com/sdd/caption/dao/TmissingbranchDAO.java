package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tmissingbranch;
import com.sdd.caption.domain.Vmissingbranch;
import com.sdd.utils.db.StoreHibernateUtil;

public class TmissingbranchDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tmissingbranch> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tmissingbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tmissingbranch "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tmissingbranch.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select tmissingbranchpk from Tmissingbranch "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tmissingbranch> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tmissingbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tmissingbranch where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tmissingbranch findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tmissingbranch oForm = (Tmissingbranch) session.createQuery("from Tmissingbranch where tmissingbranchpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tmissingbranch findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tmissingbranch oForm = (Tmissingbranch) session.createQuery("from Tmissingbranch where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tmissingbranch order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tmissingbranch oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tmissingbranch oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<Vmissingbranch> listGroupby(String filter) throws Exception {		
    	List<Vmissingbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select branchid, sum(totaldata) as totaldata from tmissingbranch where " + filter
				+ " group by branchid order by branchid").addEntity(Vmissingbranch.class).list();
		session.close();
        return oList;
    }

}


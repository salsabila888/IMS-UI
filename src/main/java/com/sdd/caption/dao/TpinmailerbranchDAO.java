package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpinmailerbranch;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinmailerbranchDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinmailerbranch> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpinmailerbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinmailerbranch "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpinmailerbranch.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpinmailerbranch findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerbranch oForm = (Tpinmailerbranch) session.createQuery("from Tpinmailerbranch where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpinmailerbranch "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinmailerbranch> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpinmailerbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinmailerbranch where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpinmailerbranch findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerbranch oForm = (Tpinmailerbranch) session.createQuery("from Tpinmailerbranch where tpinmailerbranchpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpinmailerbranch order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpinmailerbranch oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpinmailerbranch oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public Integer sum(String filter) throws Exception {
		Integer sum = 0;
		session = StoreHibernateUtil.openSession();
		sum = Integer.parseInt((String) session.createSQLQuery("select sum(totaldata) from Tpinmailerbranch left join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return sum;
    }

}

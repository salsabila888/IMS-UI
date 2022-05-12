package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpersoopr;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpersooprDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpersoopr> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpersoopr> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpersoopr join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpersoopr.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpersoopr join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpersoopr> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpersoopr> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpersoopr where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpersoopr findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersoopr oForm = (Tpersoopr) session.createQuery("from Tpersoopr where tpersooprpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpersoopr findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersoopr oForm = (Tpersoopr) session.createQuery("from Tpersoopr where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpersoopr order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpersoopr oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpersoopr oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void deleteByFilter(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("delete from Tpersoopr where " + filter).executeUpdate();
	}
	
	public void updatePendingAllSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersoopr set totalpending = quantity where " + filter).executeUpdate();
	}
	
	public void updatePendingSingleSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersoopr set totalpending = totalpending + 1 where " + filter).executeUpdate();
	}

}

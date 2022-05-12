package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class TreturnDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Treturn> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Treturn> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Treturn join Mproduct on mproductfk = mproductpk "
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Treturn join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Treturn> listByFilter(String filter, String orderby) throws Exception {		
    	List<Treturn> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Treturn where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Treturn> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Treturn> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Treturn.* from Treturn join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Treturn.class).list();
		session.close();
        return oList;
    }	
	
	public Treturn findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Treturn oForm = (Treturn) session.createQuery("from Treturn where treturnpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Treturn findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Treturn oForm = (Treturn) session.createQuery("from Treturn where returnid = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Treturn order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Treturn oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Treturn oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<Vsumbyproductgroup> getSumdataByProductgroup(String filter) throws Exception {
		List<Vsumbyproductgroup> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select productgroup, count(treturnpk) as total from Treturn join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk where "
						+ filter + " group by productgroup")
				.addEntity(Vsumbyproductgroup.class).list();

		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Treturn> listLampiran(String filter, String orderby) throws Exception {
		List<Treturn> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Treturn join Mbranch on mbranchfk = mbranchpk where " + filter
				+ " order by " + orderby).addEntity(Tderivatif.class).list();

		session.close();
		return oList;
	}

}

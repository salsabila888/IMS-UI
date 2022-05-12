package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tfilebranchactv;
import com.sdd.utils.db.StoreHibernateUtil;

public class TfilebranchactvDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tfilebranchactv> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tfilebranchactv> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tfilebranchactv "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tfilebranchactv.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tfilebranchactv "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tfilebranchactv> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tfilebranchactv> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tfilebranchactv where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tfilebranchactv findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tfilebranchactv oForm = (Tfilebranchactv) session.createQuery("from Tfilebranchactv where tfilebranchactvpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tfilebranchactv findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tfilebranchactv oForm = (Tfilebranchactv) session.createQuery("from Tfilebranchactv where filename = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tfilebranchactv>list() throws Exception {
       	List<Tfilebranchactv> oList = new ArrayList<Tfilebranchactv>();
       	session = StoreHibernateUtil.openSession();
        oList = session.createQuery("from Tfilebranchactv order by tfilebranchactvpk").list();                 
        session.close();        
        return oList;
	} 
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tfilebranchactv order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tfilebranchactv oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tfilebranchactv oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Torderflow;
import com.sdd.utils.db.StoreHibernateUtil;

public class TorderflowDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Torderflow> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Torderflow> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Torderflow left join Tembossbranch on tembossbranchfk = tembossbranchpk "
    			+ "left join Tembossfile on tembossfilefk = tembossfilepk left join Torder on torderfk = torderpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Torderflow.class).list();		

		session.close();
        return oList;
    }	
	
	public Torderflow findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderflow oForm = (Torderflow) session.createQuery("from Torderflow where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Torderflow "
				+ "left join Tembossbranch on tembossbranchfk = tembossbranchpk left join Tembossfile on tembossfilefk = tembossfilepk "
				+ "left join Torder on torderfk = torderpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Torderflow> listByFilter(String filter, String orderby) throws Exception {		
    	List<Torderflow> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Torderflow where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Torderflow findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderflow oForm = (Torderflow) session.createQuery("from Torderflow where torderflowpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Torderflow order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Torderflow oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Torderflow oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void updateSql(Session session, String status, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Torderflow set status = '" + status + "' where " + filter)
				.executeUpdate();
	}
	
}

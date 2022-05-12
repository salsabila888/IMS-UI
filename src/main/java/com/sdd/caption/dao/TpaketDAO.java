package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpaket;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpaketDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpaket> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpaket> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpaket join Mproduct on mproductfk = mproductpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpaket.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpaket join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpaket> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpaket> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpaket where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tpaket> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Tpaket> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tpaket join Tembossproduct on tembossproductfk = tembossproductpk join Mproduct on tembossproduct.mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Tpaket.class).list();
		session.close();
        return oList;
	}
	
	public int getSum(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(totaldata),0) from Tpaket "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public Tpaket findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpaket oForm = (Tpaket) session.createQuery("from Tpaket where tpaketpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpaket findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpaket oForm = (Tpaket) session.createQuery("from Tpaket where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpaket order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	public Integer getSumDlv(String filter) throws Exception {
		Integer count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(quantity),0) from Tpaket where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public void updatePaketFinishSql(Session session, String status, Integer totalDone, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpaket set status = '" + status + "', totalDone = '" + totalDone + "' where " + filter).executeUpdate();
	}
		
	public void save(Session session, Tpaket oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpaket oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	public void updateStatusSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpaket set status = '" + status + "' where " + filter).executeUpdate();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Tpaket> getPaketByDelivery(final String filter) throws Exception {
        List<Tpaket> oList = null;
        this.session = StoreHibernateUtil.openSession();
        oList = (List<Tpaket>)this.session.createSQLQuery("select distinct tpaket.* from Torderdata join Tpaket on tpaketfk = tpaketpk where " + filter).addEntity((Class)Tpaket.class).list();
        return oList;
    }

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.domain.Vproductgroupsumdata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpaketdataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpaketdata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpaketdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpaketdata join Tpaket on tpaketfk = tpaketpk "
    			+ "join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpaketdata.class).list();		

		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tpaketdata> listDelivery(String filter) throws Exception {		
    	List<Tpaketdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpaketdata join Tpaket on tpaketfk = tpaketpk "
    			+ "join Mbranch on mbranchfk = mbranchpk where " + filter)
				.addEntity(Tpaketdata.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpaketdata "
				+ "join Tpaket on tpaketfk = tpaketpk "
				+ "join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpaketdata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpaketdata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpaketdata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpaketdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpaketdata oForm = (Tpaketdata) session.createQuery("from Tpaketdata where tpaketdatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpaketdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpaketdata oForm = (Tpaketdata) session.createQuery("from Tpaketdata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpaketdata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpaketdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpaketdata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }	
	
	public void updateStatusDeliverySql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpaketdata set status = '" + status + "' where " + filter).executeUpdate();
	}
	
	public void updateProdfinishDateSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpaketdata set status = '" + status + "' where " + filter).executeUpdate();
	}
	
	public void updatePaketFinishSql(Session session, String status, String date, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpaketdata set status = '" + status + "', paketfinishdate = '" + date + "' where " + filter).executeUpdate();
	}
	
	public int getSumOrder(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(quantity),0) from Tpaketdata join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int sumOrderBranch(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(quantity),0) from Tpaketdata join Tpaket on tpaketfk = tpaketpk join Tembossproduct on tembossproductfk = tembossproductpk "
				+ "join Mproduct on mproductfk = mproductpk join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vproductgroupsumdata> geSumPaketByProductGroup(String filter) throws Exception {
		List<Vproductgroupsumdata> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Tpaketdata.productgroup, count(*) as total from Tpaketdata join Tpaket on tpaketfk = tpaketpk where " + filter
				+ " group by Tpaketdata.productgroup order by Tpaketdata.productgroup").addEntity(Vproductgroupsumdata.class).list();
		session.close();
		return oList;
	}
}

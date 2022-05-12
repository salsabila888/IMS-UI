package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tperso;
import com.sdd.caption.domain.Vpersoproduct;
import com.sdd.caption.domain.Vpersostatus;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpersoDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tperso> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tperso> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tperso "
    			+ "join Mproduct on mproductfk = mproductpk "
    			+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tperso.class).list();		

		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Vpersoproduct> listPagingPersoProduct(int first, int second, String filter, String orderby) throws Exception {		
    	List<Vpersoproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select tpersopk, tperso.totaldata, status, tembossproductfk, orderdate, productgroup from Tperso "
				+ "join Tembossproduct on tembossproductfk = tembossproductpk "
				+ "join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Vpersoproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(tpersopk) from Tperso "
				+ "join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pageCountPersoProduct(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tperso "
				+ "join Tembossproduct on tembossproductfk = tembossproductpk "
				+ "join Mproduct on mproductfk = mproductpk where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pageCount2(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tperso "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int getSum(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(totaldata),0) from Tperso join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tperso> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tperso> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tperso where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tperso> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Tperso> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tperso join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby).addEntity(Tperso.class).list();
		session.close();
        return oList;
    }	
	
	public Tperso findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tperso oForm = (Tperso) session.createQuery("from Tperso where tpersopk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tperso findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tperso oForm = (Tperso) session.createQuery("from Tperso where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tperso findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tperso oForm = (Tperso) session.createQuery("from Tperso where persoid = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tperso order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vpersoproduct> listPersoProduct() throws Exception {
		List<Vpersoproduct> oList = null;
		this.session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select tpersopk, tperso.totaldata, status, tembossproductfk, orderdate, productgroup from tperso "
				+ "join tembossproduct on tembossproductfk = tembossproductpk "
				+ "join mproduct on mproductfk = mproductpk").addEntity(Vpersoproduct.class).list();
		    
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vpersostatus> listSumOutstangingPerso() throws Exception {		
    	List<Vpersostatus> oList = null;
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select extract(year from persostarttime) as year, extract(month from persostarttime) as month, status, count(*) as count, sum(totaldata) as sum "
				+ "from tperso where status != '" + AppUtils.STATUS_PERSO_DONE + "' and status != '" + AppUtils.STATUS_PERSO_PERSODECLINE + "' and status != '" + AppUtils.STATUS_PERSO_OUTGOINGDECLINE + "' group by year, month, status order by year, month, status").addEntity(Vpersostatus.class).list();
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tperso> getProductgroupsumdata(String filter) throws Exception {
		List<Tperso> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select sum(totaldata) as totaldata from tperso " 
				+ "where " + filter).addEntity(Tperso.class).list();
		session.close();
		return oList;
	}
		
	public void save(Session session, Tperso oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tperso oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void updatePersotypeSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tperso set persotype = 'I' where " + filter).executeUpdate();
	}
	
	public void updateSql(Session session, Integer fk, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tperso set " + filter + " where tpersopk = " + fk).executeUpdate();
	}
}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tembossproduct;
import com.sdd.caption.domain.Vorderperso;
import com.sdd.utils.db.StoreHibernateUtil;

public class TembossproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tembossproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tembossproduct> oList = null;
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tembossproduct join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tembossproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tembossproduct join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public Tembossproduct findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossproduct oForm = (Tembossproduct) session.createQuery("from Tembossproduct where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tembossproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tembossproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tembossproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tembossproduct> sumTotalByFilter(String filter) throws Exception {		
		List<Tembossproduct> oList = null;
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tembossproduct join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter).addEntity(Tembossproduct.class).list();		
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tembossproduct> listByFilterType(String filter, String orderby) throws Exception {		
    	List<Tembossproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tembossproduct join Mproduct on mproductfk = mproductpk "
				+ "join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter + " order by " + orderby).addEntity(Tembossproduct.class).list();
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tembossproduct> listReportOrder(String filter) throws Exception {		
    	List<Tembossproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select tembossproduct.productcode, mproduct.productname, tembossbranch.branchid, "
				+ "mbranch.branchname, tembossproduct.totaldata from Tembossproduct join Tembossbranch on tembossproductfk = tembossproductpk "
				+ "join Mproduct on tembossproduct.mproductfk = mproductpk join Mbranch on tembossbranch.mbranchfk = mbranchpk"
				+ "where " + filter).addEntity(Tembossproduct.class).list();
		session.close();
        return oList;
    }
	
	public Tembossproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossproduct oForm = (Tembossproduct) session.createQuery("from Tembossproduct where tembossproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tembossproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tembossproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tembossproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	@SuppressWarnings("unchecked")
	public List<Vorderperso> listSumRegularOrder() throws Exception {
		List<Vorderperso> oList = null;
		this.session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select extract(year from orderdate) as year, extract(month from orderdate) "
				+ "as month, sum(orderos) as total, org as productorg from tembossproduct where orderos > 0 and isneeddoc = 'N'"
				+ "group by year, month, org order by year, month").addEntity(Vorderperso.class).list();
		    
		session.close();
		return oList;
	}
	
	public int sumOrderData(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(tembossproduct.orderos),0) from Tembossproduct join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}
	
	public void updateEmbossProduct(Session session, String isneeddoc, Integer mproductfk)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set isneeddoc = " + isneeddoc + " where mproductfk = '" + mproductfk)
				.executeUpdate();
	}
}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tderivatifproduct;
import com.sdd.caption.domain.Vorderperso;
import com.sdd.utils.db.StoreHibernateUtil;

public class TderivatifproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tderivatifproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tderivatifproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifproduct join Tderivatif on tderivatiffk = tderivatifpk "
    			+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tderivatifproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public Tderivatifproduct findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatifproduct oForm = (Tderivatifproduct) session.createQuery("from Tderivatifproduct where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tderivatifproduct join tderivatif on tderivatiffk = tderivatifpk "
				+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tderivatifproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tderivatifproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tderivatifproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatifproduct oForm = (Tderivatifproduct) session.createQuery("from Tderivatifproduct where tderivatifproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tderivatifproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	public int sumOrderData(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(tderivatifproduct.totaldata),0) from Tderivatifproduct join Tderivatif on Tderivatiffk = Tderivatifpk "
						+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tderivatifproduct> sumTotalByFilter(String filter) throws Exception {		
		List<Tderivatifproduct> oList = null;
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tderivatifproduct join Tderivatif on tderivatiffk = tderivatifpk "
    			+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter).addEntity(Tderivatifproduct.class).list();		
		session.close();
        return oList;
    }
	
	@SuppressWarnings("unchecked")
	public List<Vorderperso> listOrder() throws Exception {
		List<Vorderperso> oList = null;
		this.session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select extract(year from orderdate) as year, extract(month from orderdate) "
				+ "as month, sum(tderivatif.totaldata) as total, productorg from Tderivatif "
				+ "join Tderivatifproduct on tderivatiffk = tderivatifpk "
				+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where status = 6 "
				+ "group by year, month, productorg").addEntity(Vorderperso.class).list();
		    
		session.close();
		return oList;
	}
		
	public void save(Session session, Tderivatifproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tderivatifproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
}
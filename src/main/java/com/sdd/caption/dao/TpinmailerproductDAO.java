package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpinmailerproduct;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinmailerproductDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinmailerproduct> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpinmailerproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinmailerproduct "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpinmailerproduct.class).list();		

		session.close();
        return oList;
    }	
	
	public Tpinmailerproduct findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerproduct oForm = (Tpinmailerproduct) session.createQuery("from Tpinmailerproduct where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpinmailerproduct "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpinmailerproduct> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpinmailerproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinmailerproduct where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpinmailerproduct findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerproduct oForm = (Tpinmailerproduct) session.createQuery("from Tpinmailerproduct where tpinmailerproductpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}	
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpinmailerproduct order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tpinmailerproduct> listSerialnoLetter(String filter) throws Exception {		
    	List<Tpinmailerproduct> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpinmailerproduct join Tpinmailerfile on tpinmailerfilefk = tpinmailerfilepk " + 
    			"join torder on tpinmailerfile.torderfk = torderpk join tpaket on tpaket.torderfk = torderpk " + 
    			"join tpaketdata on tpaketfk = tpaketpk join tdeliverydata on tpaketdatafk = tpaketdatapk " + 
    			"join tdelivery on tdeliveryfk = tdeliverypk where " + filter)
				.addEntity(Tpinmailerproduct.class).list();		

		session.close();
        return oList;
    }
		
	public void save(Session session, Tpinmailerproduct oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpinmailerproduct oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public Integer sum(String filter) throws Exception {
		Integer sum = 0;
		session = StoreHibernateUtil.openSession();
		sum = Integer.parseInt((String) session.createSQLQuery("select sum(totaldata) from Tpinmailerproduct left join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return sum;
    }

}

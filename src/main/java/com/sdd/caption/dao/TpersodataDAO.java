package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tpersodata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpersodataDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpersodata> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Tpersodata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from Tpersodata join Mbranch on mbranchfk = mbranchpk join  Tperso on tpersofk = tpersopk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpersodata.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(tpersodatapk) from Tpersodata join Mbranch on mbranchfk = mbranchpk join Tperso on tpersofk = tpersopk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pagePersoProductCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from ("
				+ "select producttype, productcode, productname, count(*) as total from Tpersodata "
				+ "where " + filter + " group by producttype, productcode, productname) as a").uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int getSum(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(quantity),0) from Tpersodata "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpersodata> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpersodata> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpersodata where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tpersodata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersodata oForm = (Tpersodata) session.createQuery("from Tpersodata where tpersodatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpersodata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpersodata oForm = (Tpersodata) session.createQuery("from Tpersodata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpersodata order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpersodata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpersodata oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }
	
	public void updateStatusSql(Session session, Integer fk, String status) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersodata set status = '" + status + "' where tpersofk = " + fk).executeUpdate();
	}
	
	public void updatePendingAllSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersodata set totalpending = quantity where " + filter).executeUpdate();
	}
	
	public void updatePendingResumeAllSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersodata set totalpending = 0 where " + filter).executeUpdate();
	}
	
	public void updatePendingResumeGroupSql(Session session, Integer totalresume, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersodata set totalpending = totalpending - " + totalresume + " where " + filter).executeUpdate();
	}
	
	public void updatePendingSingleSql(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tpersodata set totalpending = totalpending + 1 where " + filter).executeUpdate();
	}

	public int getSumqtyprod(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery(
								"select coalesce(sum(quantity),0) from Tpersodata "
										+ "where " + filter).uniqueResult()
						.toString());
		session.close();
		return count;
	}
	
	public Integer getSumOrder(String filter) throws Exception {
		Integer sum = 0;
		session = StoreHibernateUtil.openSession();
		sum = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(quantity),0) from Tpersodata join Mbranch on mbranchfk = mbranchpk join Tperso on tpersofk = tpersopk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return sum;
    }
}

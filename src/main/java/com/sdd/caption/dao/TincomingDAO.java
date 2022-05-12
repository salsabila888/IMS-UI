package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Tincoming;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class TincomingDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tincoming> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tincoming> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Tincoming join mproducttype on mproducttypefk = mproducttypepk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Tincoming.class).list();

		session.close();
		return oList;
	}

	public Tincoming findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tincoming oForm = (Tincoming) session.createQuery("from Tincoming where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from Tincoming join mproducttype on mproducttypefk = mproducttypepk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tincoming> listByFilter(String filter, String orderby) throws Exception {
		List<Tincoming> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tincoming where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tincoming> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Tincoming> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tincoming join mproducttype on mproducttypefk = mproducttypepk "
				+ "where " + filter + " order by " + orderby).addEntity(Tincoming.class).list();

		session.close();
		return oList;
	}

	public Tincoming findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tincoming oForm = (Tincoming) session.createQuery("from Tincoming where tincomingpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Tincoming findByPkProduct(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tincoming oForm = (Tincoming) session.createQuery("from Tincoming where mproducttypefk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tincoming order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void saveMproducttype(Session session, Mproducttype oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void save(Session session, Tincoming oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tincoming oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void deleteBySQL(Session session, Integer pk) throws HibernateException, Exception {
		session.createSQLQuery("delete from tincoming where tincomingpk = " + pk).executeUpdate();
	}

	public int getSumqtyprod(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt(
				(String) session.createSQLQuery("select coalesce(sum(itemqty),0) from Tincoming " + "where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vsumbyproductgroup> getSumdataByProductgroup(String filter) throws Exception {
		List<Vsumbyproductgroup> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select productgroup, count(tincomingpk) as total from Tincoming join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " group by productgroup")
				.addEntity(Vsumbyproductgroup.class).list();

		session.close();
		return oList;
	}

}

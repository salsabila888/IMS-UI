package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tplan;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class TplanDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tplan> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tplan> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tplan join mbranch on mbranchfk = mbranchpk " + "where " + filter
						+ " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Tplan.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tplan " + "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tplan> listByFilter(String filter, String orderby) throws Exception {
		List<Tplan> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tplan where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Tplan findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tplan oForm = (Tplan) session.createQuery("from Tplan where Tplanpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Tplan findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tplan oForm = (Tplan) session.createQuery("from Tplan where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public String getField(String code) throws Exception {
		session = StoreHibernateUtil.openSession();
		String data = (String) session.createQuery("select description from Tplan where org = '" + code + "'")
				.uniqueResult();
		session.close();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tplan order by " + fieldname).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Vsumbyproductgroup> getSumdataByProductgroup(String filter) throws Exception {
		List<Vsumbyproductgroup> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select productgroup, count(tplanpk) as total from Tplan join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " group by productgroup")
				.addEntity(Vsumbyproductgroup.class).list();

		session.close();
		return oList;
	}

	public void save(Session session, Tplan oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tplan oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

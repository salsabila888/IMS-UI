package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Trepair;
import com.sdd.utils.db.StoreHibernateUtil;

public class TrepairDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Trepair> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Trepair> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Trepair join Mproduct on mproductfk = mproductpk " + "where " + filter
						+ " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Trepair.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session
				.createSQLQuery(
						"select count(*) from Trepair join Mproduct on mproductfk = mproductpk " + "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Trepair> listByFilter(String filter, String orderby) throws Exception {
		List<Trepair> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Trepair where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Trepair> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Trepair> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select Trepair.* from Trepair join Mproduct on mproductfk = mproductpk where "
				+ filter + " order by " + orderby).addEntity(Trepair.class).list();
		session.close();
		return oList;
	}

	public Trepair findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Trepair oForm = (Trepair) session.createQuery("from Trepair where trepairpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Trepair findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Trepair oForm = (Trepair) session.createQuery("from Trepair where regid = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Trepair order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Trepair oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Trepair oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

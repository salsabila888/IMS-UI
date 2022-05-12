package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tbranchitemtrack;
import com.sdd.utils.db.StoreHibernateUtil;

public class TbranchitemtrackDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tbranchitemtrack> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tbranchitemtrack> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchitemtrack join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Tbranchitemtrack.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchitemtrack> listbranchitemtrackByProduct(String filter, String orderby) throws Exception {
		List<Tbranchitemtrack> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchitemtrack join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk join Mregion on mregionfk = mregionpk where " + filter
				+ " order by " + orderby).addEntity(Tbranchitemtrack.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery("select count(*) from Tbranchitemtrack join Mbranch on mbranchfk = mbranchpk "
								+ "join Mproduct on mproductfk = mproductpk where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchitemtrack> listByFilter(String filter, String orderby) throws Exception {
		List<Tbranchitemtrack> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tbranchitemtrack where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Tbranchitemtrack findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchitemtrack oForm = (Tbranchitemtrack) session.createQuery("from Tbranchitemtrack where tbranchitemtrackpk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchitemtrack findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchitemtrack oForm = (Tbranchitemtrack) session.createQuery("from Tbranchitemtrack where branchitemtrackid = '" + id + "'")
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchitemtrack findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchitemtrack oForm = (Tbranchitemtrack) session.createQuery("from Tbranchitemtrack where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tbranchitemtrack order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tbranchitemtrack oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tbranchitemtrack oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

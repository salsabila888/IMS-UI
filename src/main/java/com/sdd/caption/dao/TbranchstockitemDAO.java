package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.utils.db.StoreHibernateUtil;

public class TbranchstockitemDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tbranchstockitem> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tbranchstockitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchstockitem join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Tbranchstockitem.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstockitem> listbranchstockitemByProduct(String filter, String orderby) throws Exception {
		List<Tbranchstockitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchstockitem join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk join Mregion on mregionfk = mregionpk where " + filter
				+ " order by " + orderby).addEntity(Tbranchstockitem.class).list();

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
						.createSQLQuery("select count(*) from Tbranchstockitem join Mbranch on mbranchfk = mbranchpk "
								+ "join Mproduct on mproductfk = mproductpk where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstockitem> listByFilter(String filter, String orderby) throws Exception {
		List<Tbranchstockitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery(
				"from Tbranchstockitem join Tbranchstock on tbranchstockfk = tbranchstockpk join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " order by " + orderby)
				.list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstockitem> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Tbranchstockitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Tbranchstockitem  join Tbranchstock on tbranchstockfk = tbranchstockpk join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " order by " + orderby)
				.addEntity(Tbranchstockitem.class).list();

		session.close();
		return oList;
	}

	public Tbranchstockitem findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstockitem oForm = (Tbranchstockitem) session
				.createQuery("from Tbranchstockitem where tbranchstockitempk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchstockitem findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstockitem oForm = (Tbranchstockitem) session
				.createQuery("from Tbranchstockitem where branchstockitemid = '" + id + "'").uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchstockitem findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstockitem oForm = (Tbranchstockitem) session.createQuery("from Tbranchstockitem where " + filter)
				.uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tbranchstockitem order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tbranchstockitem oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tbranchstockitem oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class TorderDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Torder> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Torder> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Torder join mbranch on mbranchfk = mbranchpk " + "where " + filter
						+ " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Torder.class).list();

		session.close();
		return oList;
	}

	public Torder findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torder oForm = (Torder) session.createQuery("from Torder where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Torder join mbranch on mbranchfk = mbranchpk " + "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Torder> listByFilter(String filter, String orderby) throws Exception {
		List<Torder> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Torder where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Torder> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Torder> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Torder join mbranch on mbranchfk = mbranchpk " + "where " + filter
						+ " order by " + orderby)
				.addEntity(Torder.class).list();

		session.close();
		return oList;
	}

	public Torder findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torder oForm = (Torder) session.createQuery("from Torder where torderpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Torder order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Torder oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Torder oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void updateSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torder set status = '" + status + "' where " + filter).executeUpdate();
	}

	public void deleteByPk(Session session, Integer pk) throws HibernateException, Exception {
		session.createSQLQuery("delete from Torder where torderpk = " + pk).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Vsumbyproductgroup> getSumdataByProductgroup(String filter) throws Exception {
		List<Vsumbyproductgroup> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select productgroup, count(torderpk) as total from Torder join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " group by productgroup")
				.addEntity(Vsumbyproductgroup.class).list();

		session.close();
		return oList;
	}

}

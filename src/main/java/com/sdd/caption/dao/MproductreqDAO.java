package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mproductreq;
import com.sdd.utils.db.StoreHibernateUtil;

public class MproductreqDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mproductreq> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Mproductreq> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Mproductreq where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Mproductreq.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mproductreq where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Mproductreq> listByFilter(String filter, String orderby) throws Exception {
		List<Mproductreq> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mproductreq where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Mproductreq findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mproductreq oForm = (Mproductreq) session.createQuery("from Mproductreq where Mproductreqpk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Mproductreq findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mproductreq oForm = (Mproductreq) session.createQuery("from Mproductreq where filter" + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Mproductreq order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Mproductreq oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Mproductreq oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Tpinmailerdata;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpinmailerdataDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpinmailerdata> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tpinmailerdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tpinmailerdata join tpinmailerbranch on tpinmailerbranchfk = tpinmailerbranchpk "
						+ "join mbranch on mbranchfk = mbranchpk where " + filter + " order by " + orderby + " limit "
						+ second + " offset " + first)
				.addEntity(Tpinmailerdata.class).list();

		session.close();
		return oList;
	}

	public Tpinmailerdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerdata oForm = (Tpinmailerdata) session.createQuery("from Tpinmailerdata where " + filter)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from Tpinmailerdata join tpinmailerbranch on tpinmailerbranchfk = tpinmailerbranchpk "
				+ "join mbranch on mbranchfk = mbranchpk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tpinmailerdata> listByFilter(String filter, String orderby) throws Exception {
		List<Tpinmailerdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpinmailerdata where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Tpinmailerdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpinmailerdata oForm = (Tpinmailerdata) session
				.createQuery("from Tpinmailerdata where tpinmailerdatapk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tpinmailerdata order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tpinmailerdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tpinmailerdata oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void updateMbranchByPinmailerBranch(Session session, Mbranch mbranch) throws HibernateException, Exception {
		session.createSQLQuery("update Tpinmailerbranch set mbranchfk = " + mbranch.getMbranchpk()
				+ " where branchid = '" + mbranch.getBranchid().trim().toUpperCase() + "'").executeUpdate();
	}

	public void updateMproductByPinmailerBranch(Session session, Mproduct mproduct)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tpinmailerdata set mproductfk = " + mproduct.getMproductpk() + " where productcode = '"
						+ mproduct.getProductcode() + "' and isinstant = '" + mproduct.getIsinstant() + "'")
				.executeUpdate();
	}

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Torderitem;
import com.sdd.utils.db.StoreHibernateUtil;

public class TorderitemDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Torderitem> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Torderitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Torderitem where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Torderitem.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Torderitem> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Torderitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Torderitem join Torder on torderfk = torderpk join Mproduct on mproductfk = mproductpk where "
						+ filter + " order by " + orderby)
				.addEntity(Torderitem.class).list();
		session.close();
		return oList;
	}

	public Torderitem findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderitem oForm = (Torderitem) session.createQuery("from Torderitem where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Torderitem where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Torderitem> listByFilter(String filter, String orderby) throws Exception {
		List<Torderitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Torderitem where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Torderitem findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderitem oForm = (Torderitem) session.createQuery("from Torderitem where Torderitempk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Torderitem order by " + fieldname).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Torderitem> listSerialnoLetter(String filter) throws Exception {
		List<Torderitem> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Torderitem join tpinpaditem on tpinpaditemfk = tpinpaditempk "
						+ "join torder on Torderitem.torderfk = torderpk join tpaket on tpaket.torderfk = torderpk "
						+ "join tpaketdata on tpaketfk = tpaketpk join tdeliverydata on tpaketdatafk = tpaketdatapk "
						+ "join tdelivery on tdeliveryfk = tdeliverypk where " + filter)
				.addEntity(Torderitem.class).list();

		session.close();
		return oList;
	}

	public void save(Session session, Torderitem oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Torderitem oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}

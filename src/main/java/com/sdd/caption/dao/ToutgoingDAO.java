package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Toutgoing;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.utils.db.StoreHibernateUtil;

public class ToutgoingDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Toutgoing> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Toutgoing> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Toutgoing join mproduct on mproductfk = mproductpk join mproducttype on mproduct.mproducttypefk = mproducttypepk "
				+ "left join Tperso on  tpersofk = tpersopk left join Torder on torderfk = torderpk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Toutgoing.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Toutgoing> listFilter(String filter, String orderby) throws Exception {
		List<Toutgoing> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Toutgoing join mproduct on mproductfk = mproductpk join mproducttype on  mproduct.mproducttypefk = mproducttypepk "
				+ "left join Tperso on  tpersofk = tpersopk left join Torder on torderfk = torderpk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter + " order by " + orderby)
				.addEntity(Toutgoing.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(toutgoingpk) from Toutgoing join mproduct on mproductfk = mproductpk join "
						+ "mproducttype on mproduct.mproducttypefk = mproducttypepk left join Tperso on  tpersofk = tpersopk "
						+ "left join Torder on torderfk = torderpk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Toutgoing> listByFilter(String filter, String orderby) throws Exception {
		List<Toutgoing> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Toutgoing where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Toutgoing findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Toutgoing oForm = (Toutgoing) session.createQuery("from Toutgoing where toutgoingpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Toutgoing findByPkProduct(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Toutgoing oForm = (Toutgoing) session.createQuery("from Toutgoing where mproducttypefk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Toutgoing findByPkPerso(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Toutgoing oForm = (Toutgoing) session.createQuery("from Toutgoing where tperso.tpersopk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Toutgoing order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void saveMproducttype(Session session, Mproducttype oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void save(Session session, Toutgoing oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Toutgoing oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void deleteByOrder(Session session, Integer id) throws HibernateException, Exception {
		session.createSQLQuery("delete from Toutgoing where torderfk = " + id).executeUpdate();
	}

	public int getSumqtyprod(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt(
				(String) session.createSQLQuery("select coalesce(sum(itemqty),0) from Toutgoing " + "where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	public int getSum(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(itemqty),0) from Toutgoing join mproduct on mproductfk = mproductpk join mproducttype on  mproduct.mproducttypefk = mproducttypepk "
						+ "left join Tperso on tpersofk = tpersopk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vsumbyproductgroup> getSumdataByProductgroup(String filter) throws Exception {
		List<Vsumbyproductgroup> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select mproduct.productgroup, count(toutgoingpk) as total from Toutgoing join Mproduct on toutgoing.mproductfk = mproductpk "
				+ "left join Torder on torderfk = torderpk join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " group by mproduct.productgroup")
				.addEntity(Vsumbyproductgroup.class).list();

		session.close();
		return oList;
	}

}

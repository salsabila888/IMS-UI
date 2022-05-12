package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Vderivatifgenerate;
import com.sdd.caption.domain.Vorderperso;
import com.sdd.caption.domain.Vstatusderivatif;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TderivatifDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tderivatif> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tderivatif> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tderivatif join Mbranch on mbranchfk = mbranchpk "
				+ "left join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Tderivatif.class).list();

		session.close();
		return oList;
	}

	public Tderivatif findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatif oForm = (Tderivatif) session.createQuery("from Tderivatif where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery("select count(*) from Tderivatif" + " join Mbranch on mbranchfk = mbranchpk "
								+ "left join Mproduct on mproductfk = mproductpk where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vderivatifgenerate> listDerivatifGeneratePaging(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vderivatifgenerate> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tderivatifpk, tderivatif.orderno, branchname, tderivatif.orderdate, totalorder, batchno, date(entrytime) as batchtime, sum(tderivatifproduct.totaldata) as totaldata, isgenerate from Tderivatif "
						+ "join Tderivatifproduct on tderivatifpk = tderivatiffk "
						+ "join Mbranch on mbranchfk = mbranchpk where " + filter
						+ " group by tderivatifpk, tderivatif.orderno, branchname, tderivatif.orderdate,totalorder, batchno, batchtime, isgenerate "
						+ "order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Vderivatifgenerate.class).list();

		session.close();
		return oList;
	}

	public int pageDerivatifGenerateCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from ("
				+ "select tderivatifpk, tderivatif.orderno, branchname, tderivatif.orderdate, totalorder, batchno, date(entrytime) as batchtime, sum(tderivatifproduct.totaldata) as totaldata, isgenerate from Tderivatif "
				+ "join Tderivatifproduct on tderivatifpk = tderivatiffk "
				+ "join Mbranch on mbranchfk = mbranchpk where " + filter
				+ " group by tderivatifpk, tderivatif.orderno, branchname, tderivatif.orderdate,totalorder, batchno, batchtime, isgenerate) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tderivatif> listByFilter(String filter, String orderby) throws Exception {
		List<Tderivatif> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tderivatif where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tderivatif> listLetterByFilter(String filter, String orderby) throws Exception {
		List<Tderivatif> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery(
				"from Tderivatif join Tdelivery on tdeliveryfk = tdeliverypk join Mbranch on mbranchfk = mbranchpk where "
						+ filter + " order by " + orderby)
				.list();
		session.close();
		return oList;
	}

	public Tderivatif findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tderivatif oForm = (Tderivatif) session.createQuery("from Tderivatif where tderivatifpk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tderivatif order by " + fieldname).list();
		session.close();
		return oList;
	}

	public int sumOrderData(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(tderivatif.totaldata),0) from Tderivatif join Tderivatifproduct on Tderivatiffk = Tderivatifpk "
						+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk where "
						+ filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vorderperso> listOrder() throws Exception {
		List<Vorderperso> oList = null;
		this.session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select extract(year from orderdate) as year, extract(month from orderdate) "
				+ "as month, sum(tderivatifproduct.totaldata) as total, productorg from Tderivatif "
				+ "join Tderivatifproduct on tderivatiffk = tderivatifpk "
				+ "join Mproduct on mproductfk = mproductpk join Mproducttype on mproducttypefk = mproducttypepk "
				+ "where tderivatifproduct.status = 7 " + "group by year, month, productorg")
				.addEntity(Vorderperso.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tderivatif> sumTotalByFilter(String filter) throws Exception {
		List<Tderivatif> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tderivatif join Mbranch on mbranchfk = mbranchpk " + "where " + filter)
				.addEntity(Tderivatif.class).list();
		session.close();
		return oList;
	}

	public Vstatusderivatif countStatus() throws Exception {
		session = StoreHibernateUtil.openSession();
		Vstatusderivatif oForm = (Vstatusderivatif) session.createSQLQuery("select count(case when status = "
				+ AppUtils.STATUS_DERIVATIF_SCAN + " then 1 end) as scan, count(case when status = "
				+ AppUtils.STATUS_DERIVATIF_CROP + " then 1 end) as crop, count(case when status = "
				+ AppUtils.STATUS_DERIVATIF_MERGE + " then 1 end) as merging, count(case when status in ("
				+ AppUtils.STATUS_DERIVATIF_ORDERPERSO + "" + ", " + AppUtils.STATUS_DERIVATIF_ORDERPERSOAPPROVAL + ", "
				+ AppUtils.STATUS_DERIVATIF_ORDERPERSOINVENTORYAPPROVAL + ") then 1 end) as perso, "
				+ "count(case when status = " + AppUtils.STATUS_DERIVATIF_PAKET + " then 1 end) as paket, "
				+ "count(case when status = " + AppUtils.STATUS_DERIVATIF_DELIVERY
				+ " then 1 end) as delivery from Tderivatif").addEntity(Vstatusderivatif.class).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("unchecked")
	public List<Tderivatif> listLampiran(String filter, String orderby) throws Exception {
		List<Tderivatif> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tderivatif join Mbranch on mbranchfk = mbranchpk where " + filter
				+ " order by " + orderby).addEntity(Tderivatif.class).list();

		session.close();
		return oList;
	}

	public void save(Session session, Tderivatif oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tderivatif oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void updateRollbackSql(Session session, Integer status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tderivatif set status = '" + status
				+ "', tdeliveryfk = null, dlvfinishtime = null where " + filter).executeUpdate();
	}

}
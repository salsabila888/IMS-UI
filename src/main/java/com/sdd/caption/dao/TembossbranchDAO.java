package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tembossbranch;
import com.sdd.caption.domain.Vembossbranch;
import com.sdd.caption.domain.Vreportorder;
import com.sdd.caption.domain.Vsla;
import com.sdd.caption.domain.Vstatusembossbranch;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TembossbranchDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tembossbranch> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tembossbranch> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tembossbranch " + "where " + filter + " order by " + orderby
				+ " limit " + second + " offset " + first).addEntity(Tembossbranch.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session
				.createSQLQuery("select count(*) from Tembossbranch " + "where " + filter).uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vembossbranch> listPagingInquiry(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vembossbranch> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select tembossdata.mbranchfk, producttype, tembossdata.productcode, productname, "
						+ "tembossdata.orderdate, sum(totaldata) as total, tembossbranch.status from tembossdata "
						+ "left join tderivatifdata on tembossdatafk = tembossdatapk join mproduct on mproductfk = mproductpk "
						+ "join mproducttype on mproducttypefk = mproducttypepk join tembossbranch on tembossbranchfk = tembossbranchpk "
						+ "where " + filter
						+ " group by tembossdata.mbranchfk, producttype, tembossdata.productcode, productname, "
						+ "tembossdata.orderdate, tembossbranch.status order by " + orderby + " limit " + second
						+ " offset " + first)
				.addEntity(Vembossbranch.class).list();

		session.close();
		return oList;
	}

	public int pageCountInquiry(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select tembossdata.mbranchfk, producttype, tembossdata.productcode, productname, "
						+ "tembossdata.orderdate, sum(totaldata) as total, tembossbranch.status from tembossdata "
						+ "left join tderivatifdata on tembossdatafk = tembossdatapk join mproduct on mproductfk = mproductpk "
						+ "join mproducttype on mproducttypefk = mproducttypepk join tembossbranch on tembossbranchfk = tembossbranchpk "
						+ "where " + filter
						+ " group by tembossdata.mbranchfk, producttype, tembossdata.productcode, productname, "
						+ " tembossdata.orderdate, tembossbranch.status) as a ")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportorder> listReportOrder(int first, int second, String filter, String orderby) throws Exception {
		List<Vreportorder> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tembossbranchpk, tembossproduct.org, mproduct.productcode, productname, tembossbranch.orderdate, "
						+ "regioncode, regionname, tembossbranch.branchid, branchname, tembossbranch.totaldata, tembossbranch.status from Tembossbranch "
						+ "join Tembossproduct on tembossproductfk = tembossproductpk join Mbranch on mbranchfk = mbranchpk "
						+ "join Mproduct on tembossbranch.mproductfk = mproductpk join Mregion on mregionfk = mregionpk "
						+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Vreportorder.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportorder> listTotalReport(String filter) throws Exception {
		List<Vreportorder> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tembossbranchpk, tembossproduct.org, mproduct.productcode, productname, tembossbranch.orderdate, "
						+ "regioncode, regionname, tembossbranch.branchid, branchname, tembossbranch.totaldata, tembossbranch.status from Tembossbranch "
						+ "join Tembossproduct on tembossproductfk = tembossproductpk join Mbranch on mbranchfk = mbranchpk "
						+ "join Mproduct on tembossbranch.mproductfk = mproductpk join Mregion on mregionfk = mregionpk "
						+ "where " + filter)
				.addEntity(Vreportorder.class).list();

		session.close();
		return oList;
	}

	public int pageCountReport(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tembossbranch "
				+ "join Tembossproduct on tembossproductfk = tembossproductpk join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on tembossbranch.mproductfk = mproductpk join Mregion on mregionfk = mregionpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
		return count;
	}

	public Tembossbranch findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossbranch oForm = (Tembossbranch) session.createQuery("from Tembossbranch where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("unchecked")
	public List<Tembossbranch> listByFilter(String filter, String orderby) throws Exception {
		List<Tembossbranch> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tembossbranch where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Tembossbranch findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossbranch oForm = (Tembossbranch) session.createQuery("from Tembossbranch where tembossbranchpk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tembossbranch order by " + fieldname).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Vsla> getPerformansiBySLA(String filter) throws Exception {
		List<Vsla> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select extract(month from orderdate) as id, (dlvstarttime - orderdate) as sla,count(*) as total from Tembossbranch "
						+ "where " + filter + " group by extract(month from orderdate), sla")
				.addEntity(Vsla.class).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tembossbranch oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tembossbranch oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void updateSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossbranch set status = '" + status + "' where " + filter).executeUpdate();
	}

	public void deleteByPk(Session session, Integer pk) throws HibernateException, Exception {
		session.createSQLQuery("delete from Tembossbranch where tembossbranchpk = " + pk).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Vstatusembossbranch> countStatus() throws Exception {
		List<Vstatusembossbranch> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select count(case when status = '" + AppUtils.STATUSBRANCH_PENDINGORDER
				+ "' then 1 end) as pendingorder, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGPRODUKSI + "' then 1 end) as pendingproduksi, "
				+ "count(case when status = '" + AppUtils.STATUSBRANCH_PROSESPRODUKSI
				+ "' then 1 end) as prosesproduksi, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGPAKET + "' then 1 end) as pendingpaket, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PROSESPAKET + "' then 1 end) as prosespaket, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGDELIVERY + "' then 1 end) as pendingdelivery, "
				+ "count(case when status = '" + AppUtils.STATUSBRANCH_PROSESDELIVERY
				+ "' then 1 end) as prosesdelivery, " + "count(case when status = '" + AppUtils.STATUSBRANCH_DELIVERED
				+ "' then 1 end) as delivered, " + "from Tembossbranch").addEntity(Vstatusembossbranch.class).list();
		session.close();
		return oList;
	}

	public Vstatusembossbranch coundStatusBranch(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Vstatusembossbranch oForm = (Vstatusembossbranch) session.createSQLQuery("select count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGPRODUKSI + "' then 1 end) as pendingproduksi, "
				+ "count(case when status = '" + AppUtils.STATUSBRANCH_PROSESPRODUKSI
				+ "' then 1 end) as prosesproduksi, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGPAKET + "' then 1 end) as pendingpaket, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PROSESPAKET + "' then 1 end) as prosespaket, " + "count(case when status = '"
				+ AppUtils.STATUSBRANCH_PENDINGDELIVERY + "' then 1 end) as pendingdelivery, "
				+ "count(case when status = '" + AppUtils.STATUSBRANCH_PROSESDELIVERY
				+ "' then 1 end) as prosesdelivery, " + "count(case when status = '" + AppUtils.STATUSBRANCH_DELIVERED
				+ "' then 1 end) as delivered " + "from Tembossbranch where " + filter)
				.addEntity(Vstatusembossbranch.class).uniqueResult();
		session.close();
		return oForm;
	}
}

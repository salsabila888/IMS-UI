package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Vbranchdelivery;
import com.sdd.caption.domain.Vcouriervendorsumdata;
import com.sdd.caption.domain.Vreportdlv;
import com.sdd.caption.domain.Vreportdlvpinmailer;
import com.sdd.utils.db.StoreHibernateUtil;

public class TdeliveryDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tdelivery> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tdelivery> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tdelivery join Mbranch on mbranchfk = mbranchpk join "
				+ "mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk left join tdeliverycourier on tdeliverycourierfk = tdeliverycourierpk "
				+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Tdelivery.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tdelivery> listExport(String filter, String orderby) throws Exception {
		List<Tdelivery> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tdelivery join Mbranch on mbranchfk = mbranchpk join "
				+ "mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk left join tdeliverycourier on tdeliverycourierfk = tdeliverycourierpk "
				+ "where " + filter + " order by " + orderby).addEntity(Tdelivery.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt(
				(String) session.createSQLQuery("select count(*) from Tdelivery join Mbranch on mbranchfk = mbranchpk "
						+ "join mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk left join tdeliverycourier on tdeliverycourierfk = tdeliverycourierpk "
						+ "where " + filter).uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdlv> listPagingReportdlv(int first, int second, String filter, String orderby) throws Exception {
		List<Vreportdlv> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tdeliverydatapk, dlvid, tdelivery.productgroup, branchid, branchname, nopaket, mproduct.productcode, "
						+ "mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tdeliverydata.quantity from Tdeliverydata join Tdelivery on tdeliveryfk = tdeliverypk "
						+ "join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk join Mregion on mregionfk = mregionpk "
						+ "join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk join Tpaketdata on tpaketdatafk = tpaketdatapk "
						+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Vreportdlv.class).list();

		session.close();
		return oList;
	}

	public int pageCountReportdlv(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select tdeliverydatapk, dlvid, tdelivery.productgroup, branchid, branchname, "
						+ "nopaket, mproduct.productcode, mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tdeliverydata.quantity as quantity from Tdeliverydata "
						+ "join Tdelivery on tdeliveryfk = tdeliverypk join Mbranch on mbranchfk = mbranchpk "
						+ "join Tpaketdata on tpaketdatafk = tpaketdatapk join Mregion on mregionfk = mregionpk "
						+ "join Mproduct on mproductfk = mproductpk join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk "
						+ "where " + filter + ") as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdlvpinmailer> listPagingReportdlvPm(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vreportdlvpinmailer> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tpinmailerproductpk, tdeliverydatapk, dlvid, tdelivery.productgroup, mbranch.branchid, mbranch.branchname, nopaket, tpinmailerproduct.productcode, "
						+ "mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tpinmailerproduct.totaldata as quantity from Tdeliverydata join Tdelivery on tdeliveryfk = tdeliverypk "
						+ "join Mbranch on Tdelivery.mbranchfk = mbranchpk join Mregion on mregionfk = mregionpk "
						+ "join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk join Tpaketdata on tpaketdatafk = tpaketdatapk "
						+ "join tpinmailerbranch on Tpaketdata.tpinmailerbranchfk = tpinmailerbranchpk join tpinmailerproduct on tpinmailerproduct.tpinmailerbranchfk = tpinmailerbranchpk "
						+ "join Mproduct on tpinmailerproduct.mproductfk = mproductpk " + "where " + filter
						+ " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Vreportdlvpinmailer.class).list();

		session.close();
		return oList;
	}

	public int pageCountReportdlvPm(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select tpinmailerproductpk, tdeliverydatapk, dlvid, tdelivery.productgroup, mbranch.branchid, "
						+ "mbranch.branchname, nopaket, tpinmailerproduct.productcode, "
						+ "mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tdeliverydata.quantity "
						+ "from Tdeliverydata join Tdelivery on tdeliveryfk = tdeliverypk join Mbranch on Tdelivery.mbranchfk = mbranchpk "
						+ "join Mregion on mregionfk = mregionpk join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk "
						+ "join Tpaketdata on tpaketdatafk = tpaketdatapk join tpinmailerbranch on Tpaketdata.tpinmailerbranchfk = tpinmailerbranchpk "
						+ "join tpinmailerproduct on tpinmailerproduct.tpinmailerbranchfk = tpinmailerbranchpk join Mproduct on tpinmailerproduct.mproductfk = mproductpk "
						+ "where " + filter + ") as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vbranchdelivery> listBranchPaketPaging(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vbranchdelivery> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select Mbranch.branchcode, Mbranch.branchid, Mbranch.branchname, min(tpaketdata.orderdate) as orderdate, count(*) as total from Tpaketdata "
						+ "join tpaket on tpaketfk = tpaketpk " + "join Mbranch on mbranchfk = mbranchpk left join Tswitch on tswitchfk = tswitchpk "
						+ "left join Torder on tpaket.torderfk = torderpk left join Treturn on treturnfk = treturnpk where "
						+ filter + " group by Mbranch.branchcode, Mbranch.branchid, Mbranch.branchname "
						+ "order by Mbranch.branchid limit " + second + " offset " + first)
				.addEntity(Vbranchdelivery.class).list();

		session.close();
		return oList;
	}

	public int listBranchPaketCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select Mbranch.branchcode, Mbranch.branchid, Mbranch.branchname, min(tpaketdata.orderdate) as orderdate, count(*) as total from Tpaketdata "
						+ "join tpaket on tpaketfk = tpaketpk " + "join Mbranch on mbranchfk = mbranchpk left join Tswitch on tswitchfk = tswitchpk "
						+ "left join Torder on tpaket.torderfk = torderpk left join Treturn on treturnfk = treturnpk where "
						+ filter + " group by Mbranch.branchcode, Mbranch.branchid, Mbranch.branchname) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vbranchdelivery> listBranchPaging(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vbranchdelivery> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tpaketpk, mbranchfk, mcouriervendorfk, branchid, branchname, tpaket.productgroup, tpaket.orderdate, quantity, status "
						+ "from tpaket join tpaketdata on tpaketfk = tpaketpk "
						+ "join mbranch on mbranchfk  = mbranchpk join mcouriervendor on mcouriervendorfk = mcouriervendorpk "
						+ "where " + filter + " order by " + orderby + " limit " + second + " offset " + first)
				.addEntity(Vbranchdelivery.class).list();

		session.close();
		return oList;
	}

	public int pageBranchCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select tpaketpk, mbranchfk, mcouriervendorfk, branchid, branchname, tpaket.productgroup, tpaket.orderdate, quantity, status  "
						+ "from tpaket join tpaketdata on tpaketfk = tpaketpk join mbranch on mbranchfk  = mbranchpk "
						+ "join mcouriervendor on mcouriervendorfk = mcouriervendorpk where " + filter + ") as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tdelivery> listByFilter(String filter, String orderby) throws Exception {
		List<Tdelivery> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tdelivery where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tdelivery> listFilter(String filter, String orderby) throws Exception {
		List<Tdelivery> obj = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		obj = session.createSQLQuery(
				"select * from Tdelivery join mcouriervendor on mcouriervendorfk = mcouriervendorfk left join tdeliverycourier on tdeliverycourierfk = tdeliverycourierpk "
						+ "where " + filter + " order by " + orderby)
				.addEntity(Tdelivery.class).list();
		session.close();
		return obj;
	}

	public int sumData(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session.createSQLQuery("select coalesce(sum(tdelivery.totaldata),0) from Tdelivery "
						+ "join tdeliverycourier on tdeliverycourierfk = tdeliverycourierpk join mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk "
						+ "where " + filter).uniqueResult().toString());
		session.close();
		return count;
	}

	public Tdelivery findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdelivery oForm = (Tdelivery) session.createQuery("from Tdelivery where tdeliverypk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Tdelivery findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tdelivery oForm = (Tdelivery) session.createQuery("from Tdelivery where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("unchecked")
	public List<Tdelivery> trackingByFilter(String filter, String orderby) throws Exception {
		List<Tdelivery> obj = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		obj = session.createSQLQuery("select * from Tdelivery join mbranch on mbranchfk = mbranchpk where "
				+ filter + " order by " + orderby).addEntity(Tdelivery.class).list();
		session.close();
		return obj;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tdelivery order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tdelivery oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tdelivery oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	@SuppressWarnings("unchecked")
	public List<Vcouriervendorsumdata> getCouriervendorsumdata(String filter) throws Exception {
		List<Vcouriervendorsumdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select vendorcode,vendorname,productgroup,count(*) as totalmanifest, sum(totaldata) as totaldata from Tdelivery right join Mcouriervendor on mcouriervendorfk = mcouriervendorpk "
						+ "where " + filter
						+ " group by vendorcode,vendorname,productgroup order by productgroup,vendorname")
				.addEntity(Vcouriervendorsumdata.class).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdlv> reportdlvByFilter(String filter) throws Exception {
		List<Vreportdlv> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tdeliverydatapk, dlvid, tdelivery.productgroup, branchid, branchname, nopaket, mproduct.productcode, "
						+ "mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tdeliverydata.quantity from Tdeliverydata join Tdelivery on tdeliveryfk = tdeliverypk "
						+ "join Mbranch on mbranchfk = mbranchpk join Mregion on mregionfk = mregionpk join Mproduct on mproductfk = mproductpk "
						+ "join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk join Tpaketdata on tpaketdatafk = tpaketdatapk "
						+ "where " + filter)
				.addEntity(Vreportdlv.class).list();
		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdlvpinmailer> ReportdlvPmByFilter(String filter) throws Exception {
		List<Vreportdlvpinmailer> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select tpinmailerproductpk, tdeliverydatapk, dlvid, tdelivery.productgroup, mbranch.branchid, mbranch.branchname, nopaket, tpinmailerproduct.productcode, "
						+ "mproduct.productname, regionname, vendorcode, tdeliverydata.orderdate, isurgent, tdelivery.processtime, penerima, tglterima, tdelivery.totaldata, tpinmailerproduct.totaldata as quantity from Tdeliverydata join Tdelivery on tdeliveryfk = tdeliverypk "
						+ "join Mbranch on Tdelivery.mbranchfk = mbranchpk join Mregion on mregionfk = mregionpk "
						+ "join Mcouriervendor on tdelivery.mcouriervendorfk = mcouriervendorpk join Tpaketdata on tpaketdatafk = tpaketdatapk "
						+ "join tpinmailerbranch on Tpaketdata.tpinmailerbranchfk = tpinmailerbranchpk join tpinmailerproduct on tpinmailerproduct.tpinmailerbranchfk = tpinmailerbranchpk "
						+ "join Mproduct on tpinmailerproduct.mproductfk = mproductpk " + "where " + filter
						+ " order by tdeliverydatapk")
				.addEntity(Vreportdlvpinmailer.class).list();

		session.close();
		return oList;
	}

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Tembossdata;
import com.sdd.caption.domain.Vproductgroupsumdata;
import com.sdd.caption.domain.Vreportdaily;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TembossdataDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tembossdata> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tembossdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tembossdata left join Tderivatifdata on tembossdatafk = tembossdatapk "
						+ "join Tembossproduct on tembossproductfk = tembossproductpk left join Mbranch on mbranchfk = mbranchpk "
						+ "join mproduct on Tembossdata.mproductfk = mproductpk "
						+ "join mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter + " order by "
						+ orderby + " limit " + second + " offset " + first)
				.addEntity(Tembossdata.class).list();

		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tembossdata> inqList(String filter, String orderby) throws Exception {
		List<Tembossdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tembossdata left join Tderivatifdata on tembossdatafk = tembossdatapk "
						+ "join Tembossproduct on tembossproductfk = tembossproductpk left join Mbranch on mbranchfk = mbranchpk "
						+ "join mproduct on Tembossdata.mproductfk = mproductpk "
						+ "join mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter + " order by "
						+ orderby)
				.addEntity(Tembossdata.class).list();

		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tembossdata> dataStockList(String filter, String orderby) throws Exception {
		List<Tembossdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery("select * from Tembossdata join Tembossbranch on tembossbranchfk = tembossbranchpk where " + filter + " order by "
						+ orderby)
				.addEntity(Tembossdata.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tembossdata> listDataEmerald(String filter, String orderby) throws Exception {
		List<Tembossdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select * from Tembossdata join Tembossbranch on tembossdata.tembossbranchfk = tembossbranchpk "
						+ "join tpaketdata on tpaketdata.tembossbranchfk = tembossbranchpk "
						+ "join Tdeliverydata on tpaketdatafk = tpaketdatapk where " + filter + " order by " + orderby)
				.addEntity(Tembossdata.class).list();

		session.close();
		return oList;
	}

	public int pagePinmailerCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select orderdate,count(*) as total, pinmailertype, orderid, filename, torderpk, tembossdata.status, "
						+ "decisionmemo from Tembossdata join Torder on torderfk = torderpk where " + filter
						+ " group by orderdate, pinmailertype, orderid, filename, torderpk, tembossdata.status, decisionmemo) as foo")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vproductgroupsumdata> getProductgroupsumpaket(String filter) throws Exception {
		List<Vproductgroupsumdata> oList = null;
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select productgroup, count(*) as total from Tpaket where " + filter
				+ " group by productgroup order by productgroup").addEntity(Vproductgroupsumdata.class).list();
		session.close();
		return oList;
	}

	public int pageSladetailCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery("select count(*) from (select orderdate " + "from Tembossdata where " + filter
								+ " and prodfinishtime is not null group by orderdate) as foo")
						.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from Tembossdata left join Tderivatifdata on tembossdatafk = tembossdatapk "
						+ "join Tembossproduct on tembossproductfk = tembossproductpk left join Mbranch on mbranchfk = mbranchpk "
						+ "join mproduct on Tembossdata.mproductfk = mproductpk "
						+ "join mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdaily> getReportOrderPaging(int first, int second, String filter) throws Exception {
		List<Vreportdaily> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select a.mproductfk as id, a.productorg, a.producttype as producttype, a.productcode as productcode, a.productname as productname, "
						+ "coalesce(total,0) as total,coalesce(perso,0) as perso,coalesce(pending,0) as pending,coalesce(delivery,0) as delivery from ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as total "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as a left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as pending "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossbranch.status = 'P01' and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as b on a.mproductfk = b.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as perso "
						+ "from tembossdata left join tderivatifdata on tembossdatafk = tembossdatapk join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join tperso on tembossdata.tembossproductfk = tperso.tembossproductfk "
						+ "join mproducttype on mproducttypefk = mproducttypepk where " + filter
						+ " and persofinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as c on a.mproductfk = c.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as delivery "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk  join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and dlvfinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as d on a.mproductfk = d.mproductfk "
						+ "order by a.productorg, a.producttype, a.productcode, a.productname limit " + second
						+ " offset " + first)
				.addEntity(Vreportdaily.class).list();
		session.close();
		return oList;
	}

	public int getReportOrderCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select a.mproductfk as id, a.productorg, a.producttype as producttype, a.productcode as productcode, a.productname as productname, "
						+ "coalesce(total,0) as total,coalesce(perso,0) as perso,coalesce(pending,0) as pending,coalesce(delivery,0) as delivery from ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as total "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as a left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as pending "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossbranch.status = 'P01' and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as b on a.mproductfk = b.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as perso "
						+ "from tembossdata left join tderivatifdata on tembossdatafk = tembossdatapk join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join tperso on tembossdata.tembossproductfk = tperso.tembossproductfk "
						+ "join mproducttype on mproducttypefk = mproducttypepk where " + filter
						+ " and persofinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as c on a.mproductfk = c.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as delivery "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and dlvfinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as d on a.mproductfk = d.mproductfk "
						+ "order by a.productorg, a.producttype, a.productcode, a.productname) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Vreportdaily> listReportDaily(String filter) throws Exception {
		List<Vreportdaily> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select a.mproductfk as id, a.productorg, a.producttype as producttype, a.productcode as productcode, a.productname as productname, "
						+ "coalesce(total,0) as total,coalesce(perso,0) as perso,coalesce(pending,0) as pending,coalesce(delivery,0) as delivery from ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as total "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as a left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as pending "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and tembossbranch.status = 'P01' and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as b on a.mproductfk = b.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as perso "
						+ "from tembossdata left join tderivatifdata on tembossdatafk = tembossdatapk join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join tperso on tembossdata.tembossproductfk = tperso.tembossproductfk "
						+ "join mproducttype on mproducttypefk = mproducttypepk where " + filter
						+ " and persofinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as c on a.mproductfk = c.mproductfk left join ( "
						+ "select tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname, count(tembossdatapk) as delivery "
						+ "from tembossdata join mproduct on tembossdata.mproductfk = mproductpk join mbranch on tembossdata.mbranchfk = mbranchpk "
						+ "join tembossbranch on tembossbranchfk = tembossbranchpk join mproducttype on mproducttypefk = mproducttypepk "
						+ "where " + filter + " and dlvfinishtime is not null and tembossdata.mproductfk is not null "
						+ "group by tembossdata.mproductfk, productorg, producttype, tembossdata.productcode, productname) as d on a.mproductfk = d.mproductfk "
						+ "order by a.productorg, a.producttype, a.productcode, a.productname")
				.addEntity(Vreportdaily.class).list();
		session.close();
		return oList;
	}

	public int sumOrderData(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(itemqty),0) from Tembossdata join Torder on torderfk = torderpk left join Mproduct on tembossdata.productcode = mproduct.productcode and mproduct.isinstant = tembossdata.isinstant left join Mproducttype on mproduct.mproducttypefk = mproducttypepk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> listUnmapped(Integer pk) throws Exception {
		List<String> oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery(
				"select productcode from Tembossdata where mproduct.mproductpk is null and torder.torderpk = " + pk
						+ " group by productcode order by productcode")
				.list();
		session.close();
		return oList;
	}

	public int pageCountProductunreg(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session
				.createSQLQuery(
						"select count(*) from (select productcode, isinstant, sum(itemqty) as total from Tembossdata "
								+ "where " + filter + " group by productcode,isinstant) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountOrderPaket(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select mproductpk, mproducttypepk, mproducttype.producttype, mproduct.productgroup, mproduct.productcode, mproduct.productname, orderdate, coalesce(sum(itemqty),0) as total "
						+ "from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on Mproduct.mproducttypefk = mproducttypepk "
						+ "where " + filter
						+ " group by mproductpk, mproducttypepk, mproducttype.producttype, mproduct.productgroup, mproduct.productcode, mproduct.productname, orderdate) as a")
				.uniqueResult().toString());

		session.close();
		return count;
	}

	public int sumOrderProduct(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(itemqty),0) from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountOrderBranchInq(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select mbranchpk, orderdate, mbranch.branchid, mbranch.branchcode, mbranch.branchname, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Tembossdata.nopaket, sum(itemqty) as total from Tembossdata "
						+ "join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk "
						+ "join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
						+ " group by mbranchpk, orderdate, mbranch.branchid, mbranch.branchcode, mbranch.branchname, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Tembossdata.nopaket) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountOrderProductInq(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select tembossdata.orderdate, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Tembossdata.status, sum(itemqty) as total from Tembossdata "
						+ "join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk "
						+ "join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
						+ " group by tembossdata.orderdate, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Tembossdata.status "
						+ "order by processtype desc, orderdate, mproducttype.productorg, mproduct.productname) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int sumOrderBranch(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(itemqty),0) from Tembossdata join Mproduct on mproductfk = mproductpk "
						+ "join Mproducttype on mproduct.mproducttypefk = mproducttypepk join Mbranch on mbranchfk = mbranchpk "
						+ "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageBranchProductCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from ("
				+ "select  '' as processtype, Tembossdata.orderdate, Tembossdata.branchcode, Mbranch.branchname, Tembossdata.productcode, Mproduct.productname, Mproducttype.producttype, laststock, sum(itemqty) as total from Tembossdata "
				+ "left join Mbranch on mbranchfk = mbranchpk left join Mproduct on mproductfk = mproductpk "
				+ "left join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
				+ " group by Tembossdata.orderdate, Tembossdata.branchcode, Mbranch.branchname, Tembossdata.productcode, Mproduct.productname, Mproducttype.producttype, laststock "
				+ "order by orderdate, Mbranch.branchname, Mproducttype.producttype, Mproduct.productname) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageOutstandingCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session
				.createSQLQuery("select count(*) from (select orderdate, count(*) as total from Tembossdata where "
						+ filter + " group by orderdate) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tembossdata> listByFilter(String filter, String orderby) throws Exception {
		List<Tembossdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tembossdata where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Tembossdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossdata oForm = (Tembossdata) session.createQuery("from Tembossdata where tembossdatapk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Tembossdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossdata oForm = (Tembossdata) session.createQuery("from Tembossdata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	public Tembossdata getLimitByFilter(String filter, String orderby, int limit) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tembossdata oForm = (Tembossdata) session
				.createSQLQuery(
						"select * from Tembossdata where " + filter + " order by " + orderby + " limit " + limit)
				.addEntity(Tembossdata.class).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tembossdata order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tembossdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tembossdata oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void deleteByFilter(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("delete from Tembossdata where " + filter).executeUpdate();
	}

	public void updateProductMappingSql(Session session, Integer mproductfk, Integer mproducttypefk, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set mproductfk = " + mproductfk + ", mproducttypefk = "
				+ mproducttypefk + " where " + filter).executeUpdate();
	}

	public void updateStatusSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set status = '" + status + "' where " + filter).executeUpdate();
	}

	public void updateRollbackSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set status = '" + status
				+ "', tdeliveryfk = null, dlvfinishtime = null where " + filter).executeUpdate();
	}

	public void updateSuppliesSql(Session session, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set status = '" + status + "', prodfinishtime = '" + date + "' where " + filter)
				.executeUpdate();
	}

	public void updatePaketFinishSql(Session session, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set status = '" + status + "', paketfinishtime = '" + date + "' where " + filter)
				.executeUpdate();
	}

	public void updateStatusPersoSql(Session session, Integer fk, String status, String processtype, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tpersofk = " + fk + ", status = '" + status
				+ "', processtype = '" + processtype + "' where " + filter).executeUpdate();
	}

	/*
	 * public void updatePersoFinishSql(Session session, String date, Integer sla,
	 * String filter) throws HibernateException, Exception {
	 * session.createSQLQuery("update Tembossdata set status = '" +
	 * AppUtils.STATUS_PAKET_READY + "', prodsla = '" + sla + "', slatotal = '" +
	 * sla + "', prodfinishtime = '" + date + "', dlvstarttime = '" + date +
	 * "' where " + filter).executeUpdate(); }
	 */

	public void updateStatusPersoApproveSql(Session session, Integer fk, String status)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set status = '" + status + "' where tpersofk = " + fk)
				.executeUpdate();
	}

	public void updateStatusPendingSql(Session session, String Pendingreason, String Pendingby, String Pendingdate,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set pendingreason = '" + Pendingreason + "', pendingby = '"
				+ Pendingby + "', pendingtime = '" + Pendingdate + "', ispending = 'Y' where " + filter)
				.executeUpdate();
	}

	public void updateStatusPersoPendingSql(Session session, Integer fk, Integer tpendingfk, String Pendingreason,
			String Pendingby, String Pendingdate, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tpendingfk = " + tpendingfk + ", pendingreason = '"
				+ Pendingreason + "', pendingby = '" + Pendingby + "', pendingtime = '" + Pendingdate
				+ "', ispending = 'Y' where tpersofk = " + fk + " and " + filter).executeUpdate();
	}

	public void updateStatusPersoPendingResumeSql(Session session, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set ispending = 'N', pendingreason = '', pendingendtime = '" + date
				+ "'  where " + filter).executeUpdate();
	}

	public void updateStatusPersoUrgentSql(Session session, Integer fk, String status, String isUrgent, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tpersofk = " + fk + ", status = '" + status + "', isurgent = '"
				+ isUrgent + "' where " + filter).executeUpdate();
	}

	public void updateStatusPersoDoneSql(Session session, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set status = '" + status + "', persodate = '" + date + "' where " + filter)
				.executeUpdate();
	}

	public void updateStatusPaketSql(Session session, Integer fk, String status, String date, String nopaket,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tpaketfk = " + fk + ", status = '" + status + "', pakettime = '"
				+ date + "', nopaket = '" + nopaket + "' where " + filter).executeUpdate();
	}

	public void updateStatusPinMailerSql(Session session, Integer fk, String status, String date, String nopaket,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tpaketfk = " + fk + ", status = '" + status + "', pakettime = '"
				+ date + "', nopaket = '" + nopaket + "', processtype = '" + AppUtils.PROCESSTYPE_REGULAR + "',"
				+ " prodfinishtime = now(), prodstarttime = now(), paketfinishtime = now() where " + filter)
				.executeUpdate();
	}

	public void updateStatusDeliverySql(Session session, Integer fk, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set tdeliveryfk = " + fk + ", status = '" + status
				+ "', deliverydate = '" + date + "' where " + filter).executeUpdate();
	}

	public void updateStatusDeliveredSql(Session session, Integer fk, String status, String date, String penerima,
			String awb) throws HibernateException, Exception {
		session.createSQLQuery("update Tembossdata set status = '" + status + "', tglterima = '" + date
				+ "', penerima = '" + penerima + "', awb = '" + awb + "' where tdeliveryfk = " + fk).executeUpdate();
	}

	/*
	 * public int getReportOrderCount(String filter) throws Exception { int count =
	 * 0; if (filter == null || "".equals(filter)) filter = "0 = 0"; session =
	 * StoreHibernateUtil.openSession(); count = Integer.parseInt((String)
	 * session.createSQLQuery(
	 * "select count(*) from (select a.mproductfk as id, coalesce(total,0) as total,coalesce(perso,0) as perso,coalesce(pending,0) as pending,coalesce(delivery,0) as delivery from ("
	 * +
	 * "select mproductfk, sum(itemqty) as total from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "
	 * + filter +
	 * " and mproductfk is not null group by mproductfk) as a left join (" +
	 * "select mproductfk, sum(itemqty) as perso from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "
	 * + filter +
	 * " and prodfinishtime is not null and mproductfk is not null group by mproductfk) as b on a.mproductfk = b.mproductfk left join ("
	 * +
	 * "select mproductfk, sum(itemqty) as pending from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "
	 * + filter +
	 * " and ispending = 'Y' and mproductfk is not null group by mproductfk) as c on a.mproductfk = c.mproductfk left join ("
	 * +
	 * "select mproductfk, sum(itemqty) as delivery from Tembossdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "
	 * + filter +
	 * " and dlvfinishtime is not null and mproductfk is not null group by mproductfk) as d on a.mproductfk = d.mproductfk) as a"
	 * ) .uniqueResult().toString()); session.close(); return count; }
	 */

	public int getSumqtyprod(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt(
				(String) session.createSQLQuery("select coalesce(sum(itemqty),0) from Tembossdata " + "where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageReportProduksiCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from ("
				+ "select mproductpk, mproduct.productcode, mproduct.productname, orderdate, mregion.regioncode, mregion.regionname, mbranch.branchid, mbranch.branchname, klncode, sum(itemqty) as total "
				+ "from tembossdata join mproduct on mproductfk = mproductpk join mregion on tembossdata.mregionfk = mregionpk join mbranch on mbranchfk = mbranchpk join mproducttype on mproducttypepk = mproduct.mproducttypefk "
				+ "where " + filter
				+ " group by mproductpk, mproduct.productcode, mproduct.productname, orderdate, mregion.regioncode, mregion.regionname, mbranch.branchid, mbranch.branchname, klncode) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageReportPinMailerCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from ("
				+ "select dlvid, binno, orderdate, mbranch.branchid, mbranch.branchname, tembossdata.isinstant, sum(itemqty) as total "
				+ "from tembossdata join mbranch on mbranchfk = mbranchpk join tdelivery on tdeliveryfk = tdeliverypk "
				+ "where " + filter
				+ " group by dlvid, binno, orderdate, mbranch.branchid, mbranch.branchname, tembossdata.isinstant) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountBranchstockproduct(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (SELECT MPRODUCTPK,PRODUCTORG,MPRODUCTTYPE.PRODUCTTYPE,MPRODUCT.PRODUCTCODE,MPRODUCT.PRODUCTNAME,KLNCODE,SUM(ITEMQTY) AS TOTAL FROM TORDERDATA JOIN MPRODUCT ON MPRODUCTFK = MPRODUCTPK "
						+ "JOIN MPRODUCTTYPE ON MPRODUCT.MPRODUCTTYPEFK = MPRODUCTTYPEPK " + "WHERE " + filter
						+ " AND ISACTIVATED = 'N' AND DLVSTARTTIME IS NOT NULL GROUP BY MPRODUCTPK,PRODUCTORG,MPRODUCTTYPE.PRODUCTTYPE,MPRODUCT.PRODUCTCODE,MPRODUCT.PRODUCTNAME,KLNCODE ORDER BY KLNCODE,PRODUCTORG,PRODUCTNAME "
						+ ") as a ")
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
				"select coalesce(sum(itemqty),0) from Tembossdata join Mproduct on mproductfk = mproductpk " + "where "
						+ filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int getSumOutstanding() throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from tembossdata join mproducttype on mproducttypefk = mproducttypepk where status = 'E' and entrytype = 'B' and "
						+ "isneeddoc = 'Y' and productgroup = '01'")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountOutstanding() throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select productcode, count(*) as jumlah from tembossdata join mproducttype "
						+ "on mproducttypefk = mproducttypepk where status = 'E' and entrytype = 'B' and isneeddoc = 'Y' and productgroup = '01' group by productcode) as fv ")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	/* #REVITALISASI */

	public void updateMproductByEmbossProduct(Session session, Mproduct mproduct) throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set mproductfk = " + mproduct.getMproductpk() + " where productcode = '"
						+ mproduct.getProductcode() + "' and isinstant = '" + mproduct.getIsinstant() + "'")
				.executeUpdate();
	}

	public void updateMbranchByEmbossBranch(Session session, Integer mbranchfk, String branchid)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Tembossdata set mbranchfk = " + mbranchfk + " where branchid = '" + branchid + "'")
				.executeUpdate();
	}

}

package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Torderdata;
import com.sdd.caption.domain.Vbranchdelivery;
import com.sdd.caption.domain.Vproductgroupsumdata;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TorderdataDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Torderdata> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Torderdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Torderdata join mbranch on mbranchfk = mbranchpk join mproduct on mproductfk = mproductpk "
				+ "join mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter + " order by " + orderby
				+ " limit " + second + " offset " + first).addEntity(Torderdata.class).list();

		session.close();
		return oList;
	}
	
	public int pagePinmailerCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery("select count(*) from (select orderdate,count(*) as total, pinmailertype, orderid, filename, torderpk, torderdata.status, "
								+ "decisionmemo from Torderdata join Torder on torderfk = torderpk where " + filter 
								+ " group by orderdate, pinmailertype, orderid, filename, torderpk, torderdata.status, decisionmemo) as foo")
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
						.createSQLQuery("select count(*) from (select orderdate " + "from Torderdata where " + filter
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Torderdata join mbranch on mbranchfk = mbranchpk join mproduct on mproductfk = mproductpk "
				+ "join mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	/*@SuppressWarnings("unchecked")
	public List<Vorderproduct> listOrderProduksiPaging(int first, int second, String filter, String orderby)
			throws Exception {
		List<Vorderproduct> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"select mproductpk, torderdata.isinstant, mproducttypepk, mproducttype.producttype, mproducttype.productorg, "
						+ "mproduct.productgroup, torderdata.productcode, mproduct.productname, laststock, stockreserved, (laststock-stockreserved) as stock, "
						+ "isblockpagu, orderdate, sum(itemqty) as total, 0 as totalpending, filename, torderfk from Torderdata join Torder on torderfk = torderpk "
						+ "left join Mproduct on mproduct.productcode = torderdata.productcode and mproduct.isinstant = torderdata.isinstant "
						+ "left join Mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter
						+ " group by "
						+ "mproductpk, mproducttypepk, torderdata.isinstant, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, "
						+ "torderdata.productcode, mproduct.productname, orderdate, laststock, stockreserved, (laststock-stockreserved), isblockpagu, filename, torderfk "
						+ "order by orderdate, mproducttype.productorg, mproduct.productname limit " + second
						+ " offset " + first)
				.addEntity(Vorderproduct.class).list();

		session.close();
		return oList;
	}

	public int pageCountOrderProduksi(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select mproductpk, torderdata.isinstant, mproducttypepk, mproducttype.producttype, mproducttype.productorg, "
						+ "mproduct.productgroup, torderdata.productcode, mproduct.productname, laststock, stockreserved, (laststock-stockreserved) as stock, "
						+ "orderdate, sum(itemqty) as total, 0 as totalpending, filename from Torderdata join Torder on torderfk = torderpk "
						+ "left join Mproduct on mproduct.productcode = torderdata.productcode and mproduct.isinstant = torderdata.isinstant "
						+ "left join Mproducttype on torderdata.mproducttypefk = mproducttypepk where " + filter
						+ " group by "
						+ "mproductpk, mproducttypepk, torderdata.isinstant, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, "
						+ "torderdata.productcode, mproduct.productname, orderdate, laststock, stockreserved, (laststock-stockreserved), filename "
						+ "order by orderdate, mproducttype.productorg, mproduct.productname) as a")
				"select count(*) from (select distinct torderdata.isinstant, torderdata.productcode, orderdate, filename " +  
						"from Torderdata join Torder on torderfk = torderpk left join Mproduct on mproduct.productcode = torderdata.productcode and mproduct.isinstant = torderdata.isinstant " +  
						"left join Mproducttype on mproduct.mproducttypefk = mproducttypepk where " + filter + ") as a")
				.uniqueResult().toString());

		session.close();
		return count;
	}*/

	public int sumOrderData(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select coalesce(sum(itemqty),0) from Torderdata join Torder on torderfk = torderpk left join Mproduct on torderdata.productcode = mproduct.productcode and mproduct.isinstant = torderdata.isinstant left join Mproducttype on mproduct.mproducttypefk = mproducttypepk "
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
				"select productcode from Torderdata where mproduct.mproductpk is null and torder.torderpk = " + pk
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
						"select count(*) from (select productcode, isinstant, sum(itemqty) as total from Torderdata "
								+ "where " + filter + " group by productcode,isinstant) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int pageCountOrderPaket(String filter) throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select mproductpk, mproducttypepk, mproducttype.producttype, mproduct.productgroup, mproduct.productcode, mproduct.productname, orderdate, coalesce(sum(itemqty),0) as total " + 
				"from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on Mproduct.mproducttypefk = mproducttypepk " + 
				"where " + filter + " group by mproductpk, mproducttypepk, mproducttype.producttype, mproduct.productgroup, mproduct.productcode, mproduct.productname, orderdate) as a")
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
				"select coalesce(sum(itemqty),0) from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk "
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
				"select count(*) from (select mbranchpk, orderdate, mbranch.branchid, mbranch.branchcode, mbranch.branchname, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Torderdata.nopaket, sum(itemqty) as total from Torderdata "
						+ "join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk "
						+ "join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
						+ " group by mbranchpk, orderdate, mbranch.branchid, mbranch.branchcode, mbranch.branchname, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Torderdata.nopaket) as a")
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
				"select count(*) from (select torderdata.orderdate, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Torderdata.status, sum(itemqty) as total from Torderdata "
						+ "join Mbranch on mbranchfk = mbranchpk join Mproduct on mproductfk = mproductpk "
						+ "join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
						+ " group by torderdata.orderdate, mproductpk, mproducttype.producttype, mproducttype.productorg, mproduct.productgroup, mproduct.productcode, mproduct.productname, processtype, Torderdata.status "
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
				"select coalesce(sum(itemqty),0) from Torderdata join Mproduct on mproductfk = mproductpk "
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
				+ "select  '' as processtype, Torderdata.orderdate, Torderdata.branchcode, Mbranch.branchname, Torderdata.productcode, Mproduct.productname, Mproducttype.producttype, laststock, sum(itemqty) as total from Torderdata "
				+ "left join Mbranch on mbranchfk = mbranchpk left join Mproduct on mproductfk = mproductpk "
				+ "left join Mproducttype on Mproduct.mproducttypefk = mproducttypepk where " + filter
				+ " group by Torderdata.orderdate, Torderdata.branchcode, Mbranch.branchname, Torderdata.productcode, Mproduct.productname, Mproducttype.producttype, laststock "
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
				.createSQLQuery("select count(*) from (select orderdate, count(*) as total from Torderdata where "
						+ filter + " group by orderdate) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Torderdata> listByFilter(String filter, String orderby) throws Exception {
		List<Torderdata> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Torderdata where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Torderdata findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderdata oForm = (Torderdata) session.createQuery("from Torderdata where torderdatapk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Torderdata findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderdata oForm = (Torderdata) session.createQuery("from Torderdata where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Torderdata getLimitByFilter(String filter, String orderby, int limit) throws Exception {
		session = StoreHibernateUtil.openSession();
		Torderdata oForm = (Torderdata) session.createSQLQuery("select * from Torderdata where " + filter + " order by " + orderby + " limit " + limit).addEntity(Torderdata.class).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Torderdata order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Torderdata oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Torderdata oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}
	
	public void deleteByFilter(Session session, String filter) throws HibernateException, Exception {
		session.createSQLQuery("delete from Torderdata where " + filter).executeUpdate();
	}

	public void updateProductMappingSql(Session session, Integer mproductfk, Integer mproducttypefk, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set mproductfk = " + mproductfk + ", mproducttypefk = "
				+ mproducttypefk + " where " + filter).executeUpdate();
	}

	public void updateStatusSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + status + "' where " + filter).executeUpdate();
	}
	
	public void updateRollbackSql(Session session, String status, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tderivatif set status = '" + status + "', tdeliveryfk = null, dlvfinishtime = null where " + filter).executeUpdate();
	}
	
	public void updateSuppliesSql(Session session, String status, String date, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + status + "', prodfinishtime = '" + date + "' where " + filter).executeUpdate();
	}
	
	public void updatePaketFinishSql(Session session, String status, String date, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + status + "', paketfinishtime = '" + date + "' where " + filter).executeUpdate();
	}

	public void updateStatusPersoSql(Session session, Integer fk, String status, String processtype, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tpersofk = " + fk + ", status = '" + status + "', processtype = '"
				+ processtype + "' where " + filter).executeUpdate();
	}

	/*public void updatePersoFinishSql(Session session, String date, Integer sla, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + AppUtils.STATUS_PAKET_READY + "', prodsla = '" + sla
				+ "', slatotal = '" + sla + "', prodfinishtime = '" + date + "', dlvstarttime = '" + date + "' where "
				+ filter).executeUpdate();
	}*/

	public void updateStatusPersoApproveSql(Session session, Integer fk, String status)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + status + "' where tpersofk = " + fk)
				.executeUpdate();
	}

	public void updateStatusPendingSql(Session session, String Pendingreason, String Pendingby, String Pendingdate,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set pendingreason = '" + Pendingreason + "', pendingby = '"
				+ Pendingby + "', pendingtime = '" + Pendingdate + "', ispending = 'Y' where " + filter)
				.executeUpdate();
	}

	public void updateStatusPersoPendingSql(Session session, Integer fk, Integer tpendingfk, String Pendingreason,
			String Pendingby, String Pendingdate, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tpendingfk = " + tpendingfk + ", pendingreason = '"
				+ Pendingreason + "', pendingby = '" + Pendingby + "', pendingtime = '" + Pendingdate
				+ "', ispending = 'Y' where tpersofk = " + fk + " and " + filter).executeUpdate();
	}

	public void updateStatusPersoPendingResumeSql(Session session, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set ispending = 'N', pendingreason = '', pendingendtime = '" + date
				+ "'  where " + filter).executeUpdate();
	}

	public void updateStatusPersoUrgentSql(Session session, Integer fk, String status, String isUrgent, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tpersofk = " + fk + ", status = '" + status + "', isurgent = '"
				+ isUrgent + "' where " + filter).executeUpdate();
	}

	public void updateStatusPersoDoneSql(Session session, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Torderdata set status = '" + status + "', persodate = '" + date + "' where " + filter)
				.executeUpdate();
	}

	public void updateStatusPaketSql(Session session, Integer fk, String status, String date, String nopaket,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tpaketfk = " + fk + ", status = '" + status + "', pakettime = '"
				+ date + "', nopaket = '" + nopaket + "' where " + filter).executeUpdate();
	}

	public void updateStatusPinMailerSql(Session session, Integer fk, String status, String date, String nopaket,
			String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tpaketfk = " + fk + ", status = '" + status + "', pakettime = '"
				+ date + "', nopaket = '" + nopaket + "', processtype = '" + AppUtils.PROCESSTYPE_REGULAR + "',"
				+ " prodfinishtime = now(), prodstarttime = now(), paketfinishtime = now() where " + filter).executeUpdate();
	}
	
	public void updateStatusDeliverySql(Session session, Integer fk, String status, String date, String filter)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set tdeliveryfk = " + fk + ", status = '" + status
				+ "', deliverydate = '" + date + "' where " + filter).executeUpdate();
	}


	public void updateStatusDeliveredSql(Session session, Integer fk, String status, String date, String penerima, String awb)
			throws HibernateException, Exception {
		session.createSQLQuery("update Torderdata set status = '" + status + "', tglterima = '" + date
				+ "', penerima = '" + penerima + "', awb = '" + awb +"' where tdeliveryfk = " + fk).executeUpdate();
	}
	
	public int getReportOrderCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from (select a.mproductfk as id, coalesce(total,0) as total,coalesce(perso,0) as perso,coalesce(pending,0) as pending,coalesce(delivery,0) as delivery from (" 
						+ "select mproductfk, sum(itemqty) as total from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where " 
						+ filter + " and mproductfk is not null group by mproductfk) as a left join (" 
						+ "select mproductfk, sum(itemqty) as perso from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "  
						+ filter + " and prodfinishtime is not null and mproductfk is not null group by mproductfk) as b on a.mproductfk = b.mproductfk left join (" 
						+ "select mproductfk, sum(itemqty) as pending from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where " 
						+ filter + " and ispending = 'Y' and mproductfk is not null group by mproductfk) as c on a.mproductfk = c.mproductfk left join (" 
						+ "select mproductfk, sum(itemqty) as delivery from Torderdata join Mproduct on mproductfk = mproductpk join Mproducttype on mproduct.mproducttypefk = mproducttypepk where "  
						+ filter + " and dlvfinishtime is not null and mproductfk is not null group by mproductfk) as d on a.mproductfk = d.mproductfk) as a")
				.uniqueResult().toString());
		session.close();
		return count;
	}

	public int getSumqtyprod(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 != 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt(
				(String) session.createSQLQuery("select coalesce(sum(itemqty),0) from Torderdata " + "where " + filter)
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
				+ "from torderdata join mproduct on mproductfk = mproductpk join mregion on torderdata.mregionfk = mregionpk join mbranch on mbranchfk = mbranchpk join mproducttype on mproducttypepk = mproduct.mproducttypefk "
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
				+ "select dlvid, binno, orderdate, mbranch.branchid, mbranch.branchname, torderdata.isinstant, sum(itemqty) as total "
				+ "from torderdata join mbranch on mbranchfk = mbranchpk join tdelivery on tdeliveryfk = tdeliverypk "
				+ "where " + filter
				+ " group by dlvid, binno, orderdate, mbranch.branchid, mbranch.branchname, torderdata.isinstant) as a")
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
		count = Integer.parseInt((String) session.createSQLQuery("select coalesce(sum(itemqty),0) from Torderdata join Mproduct on mproductfk = mproductpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int getSumOutstanding() throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from torderdata join mproducttype on mproducttypefk = mproducttypepk where status = 'E' and entrytype = 'B' and "
				+ "isneeddoc = 'Y' and productgroup = '01'").uniqueResult().toString());
		session.close();
        return count;
    }
	
	public int pageCountOutstanding() throws Exception {
		int count = 0;
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from (select productcode, count(*) as jumlah from torderdata join mproducttype " 
				+ "on mproducttypefk = mproducttypepk where status = 'E' and entrytype = 'B' and isneeddoc = 'Y' and productgroup = '01' group by productcode) as fv ")
				.uniqueResult().toString());
		session.close();
		return count;
	}
	
	/* #REVITALISASI */
	
	public void updateMproductByEmbossProduct(Session session, Integer mproductfk, Integer tembossproductfk)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Torderdata set mproductfk = " + mproductfk + " where tembossproductfk = " + tembossproductfk)
				.executeUpdate();
	}
	
	public void updateMbranchByEmbossBranch(Session session, Integer mbranchfk, Integer tembossbranchfk)
			throws HibernateException, Exception {
		session.createSQLQuery(
				"update Torderdata set mbranchfk = " + mbranchfk + " where tembossbranchfk = " + tembossbranchfk)
				.executeUpdate();
	}
	
}

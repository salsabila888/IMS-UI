package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Vbranchstock;
import com.sdd.utils.db.StoreHibernateUtil;

public class TbranchstockDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Vbranchstock> listBranchstock(String filter, String filterBranch, String orderby) throws Exception {
		List<Vbranchstock> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";

		if (filterBranch == null || "".equals(filterBranch))
			filterBranch = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery(
				"SELECT F.MBRANCHPK, F.BRANCHID, F.BRANCHNAME, TOTALINSTANT, TOTALNOTINSTANT FROM MBRANCH AS F LEFT JOIN ("
						+ "SELECT MBRANCHPK,MBRANCH.BRANCHID,MBRANCH.BRANCHNAME,PRODUCTGROUP,SUM(STOCKCABANG) AS TOTALINSTANT "
						+ "FROM TBRANCHSTOCK JOIN MBRANCH ON MBRANCHFK = MBRANCHPK JOIN MREGION ON MREGIONFK = MREGIONPK JOIN "
						+ "MPRODUCT ON MPRODUCTFK = MPRODUCTPK WHERE ISINSTANT = 'Y' AND PRODUCTGROUP = '01' AND "
						+ filter
						+ " GROUP BY MBRANCHPK,MBRANCH.BRANCHID,MBRANCH.BRANCHNAME,PRODUCTGROUP ORDER BY MBRANCH.BRANCHID,PRODUCTGROUP) AS A ON F.MBRANCHPK = A.MBRANCHPK LEFT JOIN ("
						+ "SELECT MBRANCHPK,MBRANCH.BRANCHID,MBRANCH.BRANCHNAME,PRODUCTGROUP,SUM(STOCKCABANG) AS TOTALNOTINSTANT "
						+ "FROM TBRANCHSTOCK JOIN MBRANCH ON MBRANCHFK = MBRANCHPK JOIN MREGION ON MREGIONFK = MREGIONPK JOIN "
						+ "MPRODUCT ON MPRODUCTFK = MPRODUCTPK WHERE ISINSTANT = 'N' AND PRODUCTGROUP = '01' AND "
						+ filter
						+ " GROUP BY MBRANCHPK,MBRANCH.BRANCHID,MBRANCH.BRANCHNAME,PRODUCTGROUP ORDER BY MBRANCH.BRANCHID,PRODUCTGROUP) "
						+ "AS B ON F.MBRANCHPK = B.MBRANCHPK WHERE " + filterBranch + " ORDER BY " + orderby)
				.addEntity(Vbranchstock.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstock> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tbranchstock> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchstock join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk where " + filter + " order by " + orderby + " limit "
				+ second + " offset " + first).addEntity(Tbranchstock.class).list();

		session.close();
		return oList;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstock> listBranchStockByProduct(String filter, String orderby) throws Exception {
		List<Tbranchstock> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchstock join Mbranch on mbranchfk = mbranchpk "
				+ "join Mproduct on mproductfk = mproductpk join Mregion on mregionfk = mregionpk where " + filter
				+ " order by " + orderby).addEntity(Tbranchstock.class).list();

		session.close();
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer
				.parseInt((String) session
						.createSQLQuery("select count(*) from Tbranchstock join Mbranch on mbranchfk = mbranchpk "
								+ "join Mproduct on mproductfk = mproductpk where " + filter)
						.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tbranchstock> listByFilter(String filter, String orderby) throws Exception {
		List<Tbranchstock> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tbranchstock where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tbranchstock> listNativeListByFilter(String filter, String orderby) throws Exception {
		List<Tbranchstock> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tbranchstock join Mbranch on mbranchfk = mbranchpk "
				+ "where " + filter + " order by " + orderby).addEntity(Tbranchstock.class).list();

		session.close();
		return oList;
	}

	public Tbranchstock findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstock oForm = (Tbranchstock) session.createQuery("from Tbranchstock where tbranchstockpk = " + pk)
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchstock findById(String id) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstock oForm = (Tbranchstock) session.createQuery("from Tbranchstock where branchstockid = '" + id + "'")
				.uniqueResult();
		session.close();
		return oForm;
	}

	public Tbranchstock findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tbranchstock oForm = (Tbranchstock) session.createQuery("from Tbranchstock  where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tbranchstock order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tbranchstock oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tbranchstock oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	public void updateStockSql(Session session, Tbranchstock obj, String filter) throws HibernateException, Exception {
		session.createSQLQuery("update Tbranchstock set stockcabang = " + obj.getStockcabang() + ", stockdelivered = "
				+ obj.getStockdelivered() + " where " + filter).executeUpdate();
	}

}

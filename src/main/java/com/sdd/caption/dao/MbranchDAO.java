package com.sdd.caption.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.caption.domain.Mbranch;
import com.sdd.utils.db.StoreHibernateUtil;

public class MbranchDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Mbranch> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<Mbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from mbranch left join Mcouriervendor on mcouriervendorfk = mcouriervendorpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Mbranch.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Mbranch left join Mcouriervendor on mcouriervendorfk = mcouriervendorpk "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Mbranch> listByFilter(String filter, String orderby) throws Exception {		
    	List<Mbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Mbranch where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Mbranch findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mbranch oForm = (Mbranch) session.createQuery("from Mbranch where mbranchpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Mbranch findByCode(String code) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mbranch oForm = (Mbranch) session.createQuery("from Mbranch where branchcode = '" + code + "'").uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Mbranch order by " + fieldname).list();   
        session.close();
        return oList;
	}
	
	public Mbranch findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Mbranch oForm = (Mbranch) session.createQuery("from Mbranch where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
		
	public void save(Session session, Mbranch oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Mbranch oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

	@SuppressWarnings("unchecked")
	public List<Mbranch> listBranchByDoc(String filter, String orderby) throws Exception {		
    	List<Mbranch> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select distinct mbranch.* from Tproductdoc join Mbranch on mbranchfk = mbranchpk where " + filter + " order by " + orderby).addEntity(Mbranch.class).list();
		session.close();
        return oList;
    }
}


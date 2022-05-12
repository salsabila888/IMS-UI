package com.sdd.caption;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sdd.caption.dao.MbranchDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.utils.db.StoreHibernateUtil;

public class Testing {

	public static void main(String[] args) {
		try {
			Session session = StoreHibernateUtil.openSession();
			Transaction transaction = session.beginTransaction();
			List<Mbranch> objList = new MbranchDAO().listByFilter("0=0", "branchid");
			for(Mbranch data : objList) {
				String s = data.getBranchid();
				Integer nilai = Integer.parseInt(s);
				if(nilai < 600) {
					data.setBranchlevel(3);
				} else if(nilai >= 600 && nilai < 700) {
					data.setBranchlevel(2);
				} else if(nilai >= 700) {
					data.setBranchlevel(1);
				}
				new MbranchDAO().save(session, data);
			}
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

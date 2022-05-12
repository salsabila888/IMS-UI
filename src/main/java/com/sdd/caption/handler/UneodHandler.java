package com.sdd.caption.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sdd.caption.dao.TstockcardDAO;
import com.sdd.caption.dao.TuneodDAO;
import com.sdd.caption.domain.Tuneod;
import com.sdd.caption.domain.Vuneod;
import com.sdd.utils.db.StoreHibernateUtil;

public class UneodHandler implements Job {
	
	private Session session;
	private Transaction transaction;
	
    private TstockcardDAO oDao = new TstockcardDAO();
    private TuneodDAO tuneodDao = new TuneodDAO();
    
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();			
		String realpath = (String) datamap.get("realpath");
		
		try {
			Date trxdate = new Date();
			List listDate = new ArrayList<>(); 
			Map<String, Date> map = new HashMap<>();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			List<Vuneod> oList = oDao.listUneod("trxdate between '" + dateFormatter.format(cal.getTime()) + "' and date(now())", "trxdate");
			for (Vuneod date: oList) {
				map.put(dateFormatter.format(date.getTrxdate()), date.getTrxdate());
			}
			
			Calendar calFinish = Calendar.getInstance();
			calFinish.setTime(new Date());
			calFinish.add(Calendar.DATE, -1);
			while (calFinish.getTime().compareTo(cal.getTime()) >= 0) {
				if (map.get(dateFormatter.format(cal.getTime())) == null) {
					int counting = tuneodDao.pageCount("eoddate = '" + dateFormatter.format(cal.getTime()) + "'");
					if (counting == 0) {
						session = StoreHibernateUtil.openSession();
						transaction = session.beginTransaction();
						try {
							Tuneod obj = new Tuneod();
							obj.setEoddate(cal.getTime());
							tuneodDao.save(session, obj);

							transaction.commit();
						} catch (Exception e) {
							transaction.rollback();
							e.printStackTrace();
						} finally {
							session.close();
						}
					}
				}
				cal.add(Calendar.DATE, 1);
			}
			
			/*int count = oDao.pageCount("trxdate between '" + dateFormatter.format(cal.getTime()) + "' and date(now)");
			if (count == 0) {
				int counting = tuneodDao.pageCount("eoddate = '" + dateFormatter.format(cal.getTime()) + "'");
				if (counting == 0) {
					session = StoreHibernateUtil.openSession();
					transaction = session.beginTransaction();
					try {
						Tuneod obj = new Tuneod();
						obj.setEoddate(cal.getTime());
						tuneodDao.save(session, obj);
						
						transaction.commit();
					} catch (Exception e) {
						transaction.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}
				}
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

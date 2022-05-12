package com.sdd.caption.handler;

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

import com.sdd.caption.dao.MholidayDAO;
import com.sdd.caption.domain.Mholiday;
import com.sdd.utils.db.StoreHibernateUtil;

public class SlaHandler implements Job {
	
	private Session session;
	private Transaction transaction;
	
	@SuppressWarnings("unused")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();			
		String realpath = (String) datamap.get("realpath");
		Map<Date, Mholiday> mapHoliday = new HashMap<>();
		try {
			System.out.println("SERVICE SLA AKTIF");
			List<Mholiday> listHoli = new MholidayDAO().listByFilter("0=0", "holiday");
			for (Mholiday objHoli: listHoli) {
				mapHoliday.put(objHoli.getHoliday(), objHoli);
			}
			
			Calendar cal = Calendar.getInstance();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY && mapHoliday.get(cal.getTime()) == null) {
				session = StoreHibernateUtil.openSession();
				try {
					transaction = session.beginTransaction();
					session.createSQLQuery("update Tembossbranch set prodsla = prodsla+1, slatotal=slatotal+1 where dlvstarttime is null").executeUpdate();
					session.createSQLQuery("update Tembossbranch set dlvsla = dlvsla+1, slatotal=slatotal+1 where dlvstarttime is not null and dlvfinishtime is null").executeUpdate();
					session.createSQLQuery("update Tderivatif set prodsla = prodsla+1, slatotal=slatotal+1 where totaldata > 0 and dlvstarttime is null").executeUpdate();
					session.createSQLQuery("update Tderivatif set dlvsla = dlvsla+1, slatotal=slatotal+1 where dlvstarttime is not null and dlvfinishtime is null").executeUpdate();
					session.createSQLQuery("update Torder set prodsla = prodsla+1, slatotal=slatotal+1 where dlvstarttime is null").executeUpdate();
					session.createSQLQuery("update Torder set dlvsla = dlvsla+1, slatotal=slatotal+1 where dlvstarttime is not null and dlvfinishtime is null").executeUpdate();
					transaction.commit();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

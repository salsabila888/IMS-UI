package com.sdd.caption.handler;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.sdd.caption.dao.TschedulerDAO;
import com.sdd.caption.domain.Tscheduler;
import com.sdd.caption.utils.AppUtils;

public class ScheduleManager {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initializer(String realpath) {
		System.out.println("SERVICES");
		String schedule = "";
		try {				
			List<Tscheduler> listTscheduler = new TschedulerDAO().list();
			for (Tscheduler tscheduler: listTscheduler) {
				Class cls = Class.forName(tscheduler.getJobclass());
								
				SchedulerFactory factory = new StdSchedulerFactory();
				Scheduler scheduler = factory.getScheduler();
				scheduler.start();
				
				JobDetail job = JobBuilder.newJob(cls).withIdentity(tscheduler.getSchedulername(), tscheduler.getSchedulergroup()).build();
				job.getJobDataMap().put("realpath", realpath);
				
				if (tscheduler.getSchedulerrepeattype().equals(AppUtils.SCHEDULER_REPEAT_PERMINUTE)) {
					schedule = "0 0/" + tscheduler.getRepeatinterval() + " * * * ?";
				} else if (tscheduler.getSchedulerrepeattype().equals(AppUtils.SCHEDULER_REPEAT_ATHOUR)) {
					schedule = "0 0 " + tscheduler.getRepeatinterval() + " * * ?";
				}
				
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tscheduler.getSchedulername(), tscheduler.getSchedulergroup())
						.withSchedule(CronScheduleBuilder.cronSchedule(schedule)).forJob(job).build();																					
				
				scheduler.scheduleJob(job, trigger);				
				
				if (tscheduler.getSchedulerstatus().equals(AppUtils.SCHEDULER_DISABLE_VALUE))
					scheduler.pauseTrigger(new TriggerKey(tscheduler.getSchedulername(), tscheduler.getSchedulergroup()));
				//else scheduler.resumeTrigger(new TriggerKey(tscheduler.getSchedulername(), tscheduler.getSchedulergroup()));
			}			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}

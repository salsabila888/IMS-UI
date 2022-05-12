package com.sdd.caption.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TstockcardDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tstockcard;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class StockEstimationHandler implements Job {
	
	private Session session;
	private Transaction transaction;
	
	private Mproducttype mproducttype;
	private MproducttypeDAO prodDao = new MproducttypeDAO(); 
    private TstockcardDAO oDao = new TstockcardDAO();
    private MsysparamDAO paramDao = new MsysparamDAO();
    
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat jdateFormmater = new SimpleDateFormat("yyyyD");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();			
		String realpath = (String) datamap.get("realpath");
		
		System.out.println("estimasi...");
		List<Tstockcard> oStocklist;
		SimpleRegression simpleRegression;
		int jmlStock;
		Double velocity = new Double(0);
		Double lamahabis = new Double(0);
		Double predict = new Double(0);
		
		Msysparam param = null;
		try {
			param = paramDao.findById(AppUtils.PARAM_ESTSTOCK);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (param == null) {
			param = new Msysparam();
			param.setParamvalue("120");
		}		
		
		Date date120before = DateUtils.addDays(new Date(), -(Integer.parseInt(param.getParamvalue())));	//--120 before now
		System.out.println(dateFormatter.format(date120before));
		
		try {
			List<Mproducttype> oList = prodDao.listByFilter("productgroupcode = '" + AppUtils.PRODUCTGROUP_CARD + "' and isestcount = 'Y'", "mproducttypepk");			
			//List<Mproducttype> oList = prodDao.listByFilter("mproducttypepk = 112", "mproducttypepk");
			for (Mproducttype data: oList) {
				oStocklist = oDao.listByFilter("mproducttypefk=" + data.getMproducttypepk() + 
						" and outgoingtotal > 0 and trxdate > '" + dateFormatter.format(date120before) + "' and productgroup = '" + AppUtils.PRODUCTGROUP_CARD + "'",  "trxdate");	//--hanya menghitung data outgoing					
				if (oStocklist!=null && oStocklist.size()>1) {		//--minimal perlu 2 data
			        simpleRegression = new SimpleRegression(true);
			        jmlStock=0;
					for (Tstockcard datastock: oStocklist) {
				        simpleRegression.addData(new double[][] {
				        	{Double.valueOf(jdateFormmater.format(datastock.getTrxdate())), datastock.getOutgoingtotal()}
				        });
				        //jmlStock=datastock.getStock();				        
				        System.out.println(datastock.getTrxdate() + " " + jdateFormmater.format(datastock.getTrxdate()) + " " + datastock.getOutgoingtotal());
					}
					jmlStock = data.getLaststock();
					
					//velocity = Math.abs(simpleRegression.getSlopeConfidenceInterval());	//--bikin jadi absolut
					velocity = Math.abs(simpleRegression.getSlope());
					predict = simpleRegression.predict(Double.parseDouble(jdateFormmater.format(new Date())));					
					if (predict < 0) {
						predict = new Double(0);
					} else lamahabis = jmlStock/predict;
					
			        System.out.println("product = " + data.getProductgroupname() + " - " + data.getProducttype());			        
			        System.out.println("predict = " + predict);
			        System.out.println("Stock = " + jmlStock);
			        System.out.println("lama Habis (hari) = " + lamahabis);
			        System.out.println("------------------");
			        System.out.println("");
			        
			        session = StoreHibernateUtil.openSession();
			        transaction = session.beginTransaction();
			        try {
			        	Calendar cal = Calendar.getInstance();
			        	cal.setTime(new Date());
			        	cal.add(Calendar.DATE, lamahabis.intValue());
			        	data.setVelocity(predict.intValue());
			        	data.setEstdays(lamahabis.intValue());
			        	data.setEstdate(cal.getTime());
			        	prodDao.save(session, data);
			        	transaction.commit();
			        } catch (Exception e) {
			        	transaction.rollback();
			        	e.printStackTrace();
					} finally {
						session.close();
					}
					
				} else {
			        System.out.println("product: " + data.getProductgroupname() + " - " + data.getProducttype());
			        System.out.println("Data tidak mencukupi untuk melakukan perhitungan estimasi...");
			        System.out.println("------------------");					
				}				
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

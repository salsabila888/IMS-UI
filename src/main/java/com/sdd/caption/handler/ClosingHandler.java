package com.sdd.caption.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.zkoss.zul.Messagebox;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.TincomingDAO;
import com.sdd.caption.dao.ToutgoingDAO;
import com.sdd.caption.dao.TpersodataDAO;
import com.sdd.caption.dao.TstockcardDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Toutgoing;
import com.sdd.caption.domain.Tstockcard;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class ClosingHandler implements Job {
	
	private Session session;
	private Transaction transaction;
	
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO(); 
	private TincomingDAO tincomingDao = new TincomingDAO();
	private ToutgoingDAO toutgoingDao = new ToutgoingDAO();
	private TpersodataDAO tpersodataDAO = new TpersodataDAO();
    private TstockcardDAO oDao = new TstockcardDAO();
    
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();			
		String realpath = (String) datamap.get("realpath");
		
		try {
			Date trxdate = new Date();
			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			
			oDao.deleteBySQL(session, "trxdate = '" + dateFormatter.format(trxdate) + "'");
				
			Map<String, Integer> map = new HashMap<String, Integer>();
			String filter = "trxdate in "
					+ "(select max(trxdate) from Tstockcard where trxdate < '" + dateFormatter.format(trxdate) + "')";
			List<Tstockcard> listStock = oDao.listNative(filter, "tstockcardpk");
			for (Tstockcard obj: listStock) {
				map.put(String.valueOf(obj.getMproducttype().getMproducttypepk()), obj.getStock());
			}
								
			List<Mproducttype> listProducttype = mproducttypeDao.listByFilter("0=0", "mproducttypepk");
			for (Mproducttype data: listProducttype) {
				//System.out.println("PROD : " + custprod.getMproduct().getProductname());
				Integer balance = map.get(String.valueOf(data.getMproducttypepk()));
				if (balance == null)
					balance = 0;
				//filter = "date(entrytime) = '" + dateFormatter.format(trxdate) + "' and mproducttypefk = " + data.getMproducttypepk();
				filter = "date(decisiontime) = '" + dateFormatter.format(trxdate) + "' and mproducttypefk = " + data.getMproducttypepk();
				Integer in = tincomingDao.getSumqtyprod(filter + " and status = '" + AppUtils.STATUS_APPROVED + "'");
				Integer out = toutgoingDao.getSumqtyprod(filter + " and status = '" + AppUtils.STATUS_APPROVED + "'");				
				/*List<Toutgoing> listOrder = toutgoingDao.listByFilter("date(entrytime) = '" + dateFormatter.format(trxdate) + "' and status = '" + AppUtils.STATUS_APPROVED + "'", "toutgoingpk");
				for (Toutgoing order: listOrder) {
					if (order.getTperso() != null) {
						out += tpersodataDAO.getSumqtyprod("tpersofk = " + order.getTperso().getTpersopk() + " and mproducttypefk = " + data.getMproducttypepk());
					} else if (order.getTorder() != null) {
						out += torderdataDao.getSumqtyprod("torderfk = " + order.getTorder().getTorderpk() + " and mproducttypefk = " + data.getMproducttypepk());
					} else {
						out += toutgoingDao.getSumqtyprod("mproducttypefk = " + data.getMproducttypepk());
					}
				}*/
				
				Tstockcard objForm = new Tstockcard();
				objForm.setMproducttype(data);
				objForm.setProducttype(data.getProducttype());
				objForm.setProductorg(data.getProductorg());
				objForm.setProductgroup(data.getProductgroupcode());
				objForm.setTrxdate(trxdate);
				objForm.setIncomingtotal(in);
				objForm.setOutgoingtotal(out);
				objForm.setStock(balance + in - out);
				objForm.setLastupdated(new Date());
				objForm.setUpdatedby("SYSTEM");
				oDao.save(session, objForm);
			}
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

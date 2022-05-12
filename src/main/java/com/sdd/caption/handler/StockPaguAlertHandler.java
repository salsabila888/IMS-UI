package com.sdd.caption.handler;

import java.text.NumberFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sdd.caption.dao.MpicproductDAO;
import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TnotifDAO;
import com.sdd.caption.domain.Mpicproduct;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tnotif;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class StockPaguAlertHandler implements Job {

	private TnotifDAO tnotifDao = new TnotifDAO();
	private MproducttypeDAO mproducttypeDao = new MproducttypeDAO();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();
		String realpath = (String) datamap.get("realpath");

		try {
			int notifcounter = 0;
			String[] recipients = null;
			/*
			 * Session session = null; Transaction transaction = null;
			 */
			List<Mpicproduct> listPic = new MpicproductDAO().listByFilter("0=0", "picname");
			if (listPic.size() > 0) {
				recipients = new String[listPic.size()];
				int idx = 0;
				for (Mpicproduct mpicproduct : listPic) {
					recipients[idx++] = mpicproduct.getPicemail();
				}
			}

			/*
			 * List<Mproducttype> objList = mproducttypeDao.
			 * listByFilter("laststock < stockmin and isalertstockpagu = 'N' and productgroupcode = '"
			 * + AppUtils.PRODUCTGROUP_CARD + "'", "producttype"); if (objList.size() > 0) {
			 * session = StoreHibernateUtil.openSession(); try { transaction =
			 * session.beginTransaction(); mproducttypeDao.updateBlockPagu(session);
			 * transaction.commit(); notifcounter++; } catch (Exception e) {
			 * e.printStackTrace(); } finally { session.close(); }
			 */

			if (recipients != null) {
				try {
					List<Mproducttype> objList = mproducttypeDao.listByFilter("laststock < stockmin", "producttype");
					String smtpname = "";
					String smtpport = "";
					String mailid = "";
					String mailpass = "";
					List<Msysparam> objParam = new MsysparamDAO()
							.listByFilter("paramgroup = '" + AppUtils.PARAM_GROUP_MAIL + "'", "orderno");
					for (Msysparam obj : objParam) {
						if (obj.getParamcode().equals(AppUtils.PARAM_SMTPNAME))
							smtpname = obj.getParamvalue();
						else if (obj.getParamcode().equals(AppUtils.PARAM_SMTPPORT))
							smtpport = obj.getParamvalue();
						else if (obj.getParamcode().equals(AppUtils.PARAM_MAILID))
							mailid = obj.getParamvalue();
						else if (obj.getParamcode().equals(AppUtils.PARAM_MAILPASSWORD))
							mailpass = obj.getParamvalue();
					}

					MailUtil mailUtils = new MailUtil();
					mailUtils.setSmtpname(smtpname);
					mailUtils.setSmtpport(Integer.parseInt(smtpport));
					mailUtils.setMailid(mailid);
					mailUtils.setMailpassword(mailpass);
					mailUtils.setFrom("Caption <" + mailid + ">");
					mailUtils.setSubject("Caption - Alert Stok Pagu");
					mailUtils.setRecipients(recipients);

					StringBuffer bufferContent = new StringBuffer();
					bufferContent.append("<p>Yth PIC Aplikasi Caption,</p>");
					bufferContent.append("<br/>");
					bufferContent.append("<p>Berikut daftar produk yang telah menyentuh pagu stocknya : </p>");
					bufferContent.append("<br/>");
					bufferContent.append("<table border=\"1\">");
					bufferContent.append("<tr>");
					bufferContent.append("<th align=\"right\">No</th>");
					bufferContent.append("<th>Tipe Produk</th>");
					bufferContent.append("<th align=\"right\">Stock</th>");
					bufferContent.append("<th align=\"right\">Pagu Stock</th>");
					bufferContent.append("</tr>");
					int no = 1;
					for (Mproducttype obj : objList) {
						bufferContent.append("<tr>");
						bufferContent.append("<td align=\"right\">" + (no++) + "</td>");
						bufferContent.append("<td>" + obj.getProducttype() + "</td>");
						bufferContent.append("<td align=\"right\">"
								+ NumberFormat.getInstance().format(obj.getLaststock()) + "</td>");
						bufferContent.append("<td align=\"right\">"
								+ NumberFormat.getInstance().format(obj.getStockmin()) + "</td>");
						bufferContent.append("</tr>");
					}
					bufferContent.append("</table>");
					bufferContent.append("<br/>");
					bufferContent.append("<p>Terima Kasih.</p>");
					bufferContent.append("<br/>");
					bufferContent.append("<p>Salam,</p>");
					bufferContent.append("<p>Caption</p>");
					mailUtils.setBodymsg(bufferContent.toString());
					MailSender.sendSSLMessage(mailUtils);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

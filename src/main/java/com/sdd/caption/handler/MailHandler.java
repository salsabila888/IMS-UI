package com.sdd.caption.handler;

import java.util.List;

import javax.mail.MessagingException;

import com.sdd.caption.dao.MpicproductDAO;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.domain.Mpicproduct;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Msysparam;

public class MailHandler extends Thread {
	
	private Mproducttype obj;
	
	public MailHandler(Mproducttype obj) {
		this.obj = obj;
	}
	
	public void run() {		
		try {				
			String smtpname = "";
			String smtpport = "";
			String mailid = "";
			String mailpassword = "";
			List<Msysparam> objParam = new MsysparamDAO().listByFilter("paramgroup = 'MAIL'", "orderno");
			for (Msysparam obj: objParam) {
				if (obj.getParamcode().equals("SMTPNAME"))
					smtpname = obj.getParamvalue();
				else if (obj.getParamcode().equals("SMTPPORT"))
					smtpport = obj.getParamvalue();
				else if (obj.getParamcode().equals("MAILID"))
					mailid = obj.getParamvalue();
				else if (obj.getParamcode().equals("MAILPASSWORD"))
					mailpassword = obj.getParamvalue();
				
			}
			List<Mpicproduct> listPic = new MpicproductDAO().listByFilter("productgroup = '" + obj.getProductgroupcode() + "'", "picname");
			for (Mpicproduct pic: listPic) {
				MailUtil mailUtils = new MailUtil();
				mailUtils.setSmtpname(smtpname);
				mailUtils.setSmtpport(Integer.parseInt(smtpport));
				mailUtils.setMailid(mailid);
				mailUtils.setMailpassword(mailpassword);
				mailUtils.setFrom("Caption <Teon.Yudasto@bni.co.id>");
				mailUtils.setSubject("Alert Stok Pagu Tipe Produk " + obj.getProducttype());
				mailUtils.setRecipient(pic.getPicemail());
				
				StringBuffer bufferContent = new StringBuffer();
				bufferContent.append("<p>Dear " + pic.getPicname() + "</p>");
				bufferContent.append("<br/>");
				bufferContent.append("<p>Stok untuk tipe produk " + obj.getProducttype() + " sudah menyentuh angka pagu. Saat ini jumlah stok yang tersisa adalah " + obj.getLaststock() + ". Mohon segera ditindak lanjuti.</p>");
				bufferContent.append("<br/>");				
				bufferContent.append("<br/>");
				bufferContent.append("<p>Terima Kasih.</p>");
				bufferContent.append("<br/>");
				bufferContent.append("<p>Salam,</p>");
				bufferContent.append("<p>Caption</p>");
				mailUtils.setBodymsg(bufferContent.toString());
				MailSender.sendSSLMessage(mailUtils);
			}														
		} catch (MessagingException e) {
			e.printStackTrace();			
		} catch (Exception e) {
			e.printStackTrace();			
		}				
	}
}
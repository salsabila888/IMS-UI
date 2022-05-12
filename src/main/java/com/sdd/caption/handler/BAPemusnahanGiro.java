package com.sdd.caption.handler;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TembossdataDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tembossdata;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.utils.AppUtils;

public class BAPemusnahanGiro {
	
	public static void doBAPemusnahanGiro(Document document, Font font, Font fonttable, Font fontbold, Treturn obj, Treturnitem objItem, Muser oUser)
			throws Exception {
		try {
			
			String[] hari = { "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu" };
			String[] months = new DateFormatSymbols().getMonths();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
			String companyname = "";
			String divisiname = "";
			String groupname = "";
			String address1 = "";
			String address2 = "";
			String address3 = "";
			String address4 = "";
			String city = "";
			String pemimpin = "";
			String ttd = "";
			String assistenpnh = "";
			String penyeliapnc = "";
			String pemimpinkcp = "";
			String pemimpinpmsr = "";
			
			List<Msysparam> objParam = new MsysparamDAO()
					.listByFilter("paramgroup = '" + AppUtils.PARAM_GROUP_COMPANYDATA + "'", "orderno");
			for (Msysparam obj2 : objParam) {
				if (obj2.getParamcode().equals(AppUtils.PARAM_COMPANYNAME))
					companyname = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_DIVISINAME))
					divisiname = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_GROUPNAME))
					groupname = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_ADDRESS1))
					address1 = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_ADDRESS2))
					address2 = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_ADDRESS3))
					address3 = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_ADDRESS4))
					address4 = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_CITY))
					city = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_PEMIMPIN))
					pemimpin = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_TTD))
					ttd = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_ASSISTENPNH))
					assistenpnh = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_PEMIMPINKCP))
					pemimpinkcp = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_PENYELIAPNC))
					penyeliapnc = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_PEMIMPINPMSR))
					pemimpinpmsr = obj2.getParamvalue();
				
			}

			PdfPTable table = null;
			PdfPCell cell = null;
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("BERITA ACARA PEMUSNAHAN " + obj.getMproduct().getProductname(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingTop(50);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Pada hari " + hari[cal.get(Calendar.DAY_OF_WEEK) - 1] + " Tanggal " +  cal.get(Calendar.DATE) + 
					" Bulan " + months[(cal.get(Calendar.MONTH))] + " Tahun " + cal.get(Calendar.YEAR) + " bertempat di cabang " + obj.getMbranch().getBranchname() + 
					" telah dilakukan pemusnahan " + obj.getMproduct().getProductname(), font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Yang dilakukan oleh ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Nama lengkap/NPP ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + oUser.getUsername() + " / " + oUser.getUserid(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Jabatan ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + oUser.getMusergroup().getUsergroupname(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Yang dimusnahkan ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Nomor Seri Giro ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": *Daftar nomor seri terlampir", fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 35, 65 });
			cell = new PdfPCell(new Paragraph("Jumlah ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getItemqty(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Pemusnahan tersebut karena cek bilyet giro " + obj.getMreturnreason().getReturnreason(), font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Demikian Berita Acara Pemusnahan Cek Bilyet Giro ini dibuat untuk dapat dipergunakan sebagaimana mestinya.", font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(
					new Paragraph(city + ", " + new SimpleDateFormat("dd MMMMM yyyy").format(obj.getInserttime()), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 45, 55 });
			cell = new PdfPCell(new Paragraph("Yang Melaksanakan", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Yang Menyaksikan", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 30, 35, 35});
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell(new Paragraph(assistenpnh, fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(penyeliapnc, fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(pemimpinkcp, fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
		    cell.setPaddingRight(50);
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 30, 35, 35});
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell(new Paragraph("Assisten PNH", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Penyelia PNC", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("PGS Pemimpin KCP UIS", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Mengetahui : ", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Kantor Cabang " + obj.getMbranch().getBranchname(), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("\n", font));
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("\n", font));
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph(pemimpinpmsr, fontbold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
		    cell.setPaddingTop(20);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Pemimpin Bidang Pemasaran", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setPaddingLeft(50);
		    cell.setPaddingRight(50);
		    cell.setPaddingBottom(5);
			table.addCell(cell);
			document.add(table);
			

			document.newPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

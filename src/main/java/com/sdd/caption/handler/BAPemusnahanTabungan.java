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
import com.sdd.caption.dao.TderivatifDAO;
import com.sdd.caption.dao.TderivatifdataDAO;
import com.sdd.caption.dao.TreturnDAO;
import com.sdd.caption.dao.TreturnitemDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Treturn;
import com.sdd.caption.domain.Treturnitem;
import com.sdd.caption.utils.AppUtils;

public class BAPemusnahanTabungan {
	
	public static void doBAPemusnahanTabungan(Document document, Font font, Font fonttable, Font fontbold, Treturn obj, Treturnitem objItem, Muser oUser)
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

//			table = new PdfPTable(2);
//			table.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table.setWidthPercentage(100);
//			table.setWidths(new int[] { 60, 40 });
//			cell = new PdfPCell(new Paragraph("PEDOMAN PERUSAHAAN", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
//			cell.setRowspan(7);
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//
//			String logo_path = Executions.getCurrent().getDesktop().getWebApp()
//					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/corp_logo.png");
//			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
//			// img.scaleAbsolute(135f, 40f);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell = new PdfPCell(img);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setRowspan(3);
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Paragraph("Indeks", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("BAB", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("SUB BAB", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("SUB SUB BAB", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			document.add(table);
//			
//			table = new PdfPTable(2);
//			table.setHorizontalAlignment(Element.ALIGN_LEFT);
//			table.setWidthPercentage(100);
//			table.setWidths(new int[] { 60, 40 });
//			cell = new PdfPCell(new Paragraph("NAMA BAB ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("Halaman ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			document.add(table);
//			table = new PdfPTable(2);
//			table.setHorizontalAlignment(Element.ALIGN_LEFT);
//			table.setWidthPercentage(100);
//			table.setWidths(new int[] { 60, 40 });
//			cell = new PdfPCell(new Paragraph("NAMA SUB BAB ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("No. Instruksi ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			document.add(table);
//			table = new PdfPTable(2);
//			table.setHorizontalAlignment(Element.ALIGN_LEFT);
//			table.setWidthPercentage(100);
//			table.setWidths(new int[] { 60, 40 });
//			cell = new PdfPCell(new Paragraph("NAMA SUB SUB BAB ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("Tgl Berlaku ", font));
//			cell.setBorder(PdfPCell.BOX);
//			table.addCell(cell);
//			document.add(table);
//			table = new PdfPTable(1);
//			table.setHorizontalAlignment(Element.ALIGN_LEFT);
//			table.setWidthPercentage(100);
//			cell = new PdfPCell(new Paragraph("\n", font));
//			cell.setBorder(PdfPCell.NO_BORDER);
//			table.addCell(cell);
//			document.add(table);
			
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
			cell = new PdfPCell(new Paragraph("Nomor Seri Buku Tabungan ", font));
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
					"Pemusnahan tersebut karena buku tabungan " + obj.getMreturnreason().getReturnreason(), font));
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
					"Demikian Berita Acara Pemusnahan Buku Tabungan ini dibuat untuk dapat dipergunakan sebagaimana mestinya.", font));
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
		    cell.setPaddingTop(20);
		    cell.setPaddingBottom(5);
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
			
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 10, 90 });
			cell = new PdfPCell(new Paragraph("No", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("No Seri", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);			
			document.add(table);
			int no = 1;
			for (Treturnitem data: new TreturnitemDAO().listDataLampiran("treturnfk = " + obj.getTreturnpk(),
					"treturnitempk")) {
				table = new PdfPTable(2);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 10, 90});
				cell = new PdfPCell(new Paragraph(String.valueOf(no++), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);				
				cell = new PdfPCell(new Paragraph(data.getItemno(), fonttable));
				table.addCell(cell);			
				document.add(table);
			}

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			document.newPage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

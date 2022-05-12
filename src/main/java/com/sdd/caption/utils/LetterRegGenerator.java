package com.sdd.caption.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TdeliverydataDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tdeliverydata;
import com.sdd.utils.StringUtils;

public class LetterRegGenerator {
	
	public LetterRegGenerator() {
		
	}
	
	public static void doLetterRegGenerator(Document document, Font font, Font fonttable, Tdelivery obj) throws Exception {
		try {
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
			}

			PdfPTable table = null;
			PdfPCell cell = null;

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);

			String logo_path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/corp_logo.png");
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
			// img.scaleAbsolute(135f, 40f);
			cell = new PdfPCell(img);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//cell.setRowspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(
					new Paragraph(city + ", " + new SimpleDateFormat("dd MMMMM yyyy").format(obj.getProcesstime()), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
			table.setWidths(new int[] { 10, 90 });
			cell = new PdfPCell(new Paragraph("Nomor", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getDlvid(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 10, 90 });
			cell = new PdfPCell(new Paragraph("Lamp", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getTotaldata() + " (" + StringUtils.angkaToTerbilang(Long.valueOf(obj.getTotaldata())) + ") Jenis Kartu", font));
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
			table.setWidths(new int[] { 10, 90 });
			cell = new PdfPCell(new Paragraph("Kepada", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(":", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("PT. Bank Negara Indonesia (Persero) Tbk", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kantor Cabang " + obj.getMbranch().getBranchname() + " (" + obj.getMbranch().getBranchid() + ")", font));
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
			cell = new PdfPCell(new Paragraph("Up : Pemimpin Bidang Layanan (PBN)", font));
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
			table.setWidths(new int[] { 10, 90 });
			cell = new PdfPCell(new Paragraph("Perihal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Pengiriman Kartu Debit", font));
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
			cell = new PdfPCell(new Paragraph("Terlampir kami sampaikan paket yang terdiri dari :", font));
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

			table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 6, 43, 18, 18, 15 });
			cell = new PdfPCell(new Paragraph("No", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jenis Kartu", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("No Paket", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Tanggal Data", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jumlah Data", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.CYAN);
			table.addCell(cell);
			document.add(table);
			int no = 1;
			List<Tdeliverydata> objList = new TdeliverydataDAO().listByFilter("tdeliveryfk = " + obj.getTdeliverypk(),
					"orderdate");
			for (Tdeliverydata data : objList) {
				table = new PdfPTable(5);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 6, 43, 18, 18, 15 });
				cell = new PdfPCell(new Paragraph(String.valueOf(no++), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(data.getMproduct().getProductname(), fonttable));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(data.getTpaketdata().getNopaket(), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(
						new Paragraph(new SimpleDateFormat("dd MMM yyyy").format(data.getOrderdate()), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(String.valueOf(data.getQuantity()), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				document.add(table);
			}

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Selanjutnya kami harapkan bantuan Saudara untuk mengembalikan copy surat ini kepada kami (setelah ditandatangani) sebagai tanda terima melalui email ke pkd.opr@bni.co.id",
					font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Demikianlah kami sampaikan, atas perhatian dan kerjasamanya diucapkan terima kasih.",
					font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			PdfPTable tablefoot = new PdfPTable(2);
			tablefoot.setWidthPercentage(100);
			tablefoot.setWidths(new int[] { 70, 30 });

			PdfPCell cellfoot1 = new PdfPCell();
			cellfoot1.setBorder(PdfPCell.NO_BORDER);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			if (ttd != null && ttd.trim().length() > 0) {
				logo_path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/" + ttd);
				if (new File(logo_path).exists()) {
					img = com.itextpdf.text.Image.getInstance(logo_path);
					img.scaleAbsolute(150f, 70f);
					cell = new PdfPCell(img);
				}
			} else
				cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setRowspan(4);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(pemimpin, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Pemimpin Kelompok ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cellfoot1.addElement(table);

			PdfPCell cellfoot2 = new PdfPCell();
			cellfoot2.setBorder(PdfPCell.NO_BORDER);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Tanda Terima", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(7);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n\n\n\n\n\n................................................\n\nTanda Tangan, Nama & NPP", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(7);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Tanggal :", font));
			cell.setPadding(7);
			table.addCell(cell);
			cellfoot2.addElement(table);

			tablefoot.addCell(cellfoot1);
			tablefoot.addCell(cellfoot2);
			document.add(tablefoot);

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 73, 27 });
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("PT. Bank Negara Indonesia (Persero) Tbk", new Font(Font.FontFamily.HELVETICA, 7)));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 73, 27 });
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Kantor Pusat", new Font(Font.FontFamily.HELVETICA, 7)));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 73, 27 });
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jl. Jenderal Sudirman Kav. 1", new Font(Font.FontFamily.HELVETICA, 7)));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 72, 27 });
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jakarta 10220, Indonesia ", new Font(Font.FontFamily.HELVETICA, 7)));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 72, 27 });
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("www.bni.co.id", new Font(Font.FontFamily.HELVETICA, 7)));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			document.newPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

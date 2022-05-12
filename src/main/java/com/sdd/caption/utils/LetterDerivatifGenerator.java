package com.sdd.caption.utils;

import java.io.File;
import java.text.SimpleDateFormat;
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
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Tderivatifdata;

public class LetterDerivatifGenerator {
	
	public LetterDerivatifGenerator() {
		
	}
	
	public static void doGenerate(Document document, Font font, Font fonttable, Tdelivery obj) throws Exception {
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

			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 60, 40 });
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);

			String logo_path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/corp_logo.png");
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
			// img.scaleAbsolute(135f, 40f);
			cell = new PdfPCell(img);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setRowspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(address1, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(address2, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(address3, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(address4, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(
					new Paragraph(city + "," + new SimpleDateFormat("dd MMMMM yyyy").format(obj.getProcesstime()), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
			table.setWidths(new int[] { 15, 85 });
			cell = new PdfPCell(new Paragraph("No", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getDlvid(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15, 85 });
			cell = new PdfPCell(new Paragraph("Lampiran", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getTotaldata(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kepada", font));
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
			cell = new PdfPCell(new Paragraph("Kantor Cabang " + obj.getMbranch().getBranchid(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(obj.getMbranch().getBranchname(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Up. Pemimpin Bidang Layanan (PBN)", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15, 85 });
			cell = new PdfPCell(new Paragraph("Hal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Pengiriman", font));
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
			cell = new PdfPCell(new Paragraph("Terlampir kami sampaikan paket yang terdiri :", font));
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
			
			for (Tderivatif objLetter: new TderivatifDAO().listLampiran("tdeliveryfk = " + obj.getTdeliverypk(), "orderdate")) {
				System.out.println("NO SURAT : " + objLetter.getOrderno());
				table = new PdfPTable(1);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.setWidthPercentage(100);
				cell = new PdfPCell(new Paragraph("Surat Saudara No " + objLetter.getOrderno() + ", tanggal " + new SimpleDateFormat("dd MMMMM yyyy").format(objLetter.getOrderdate()), font));
				cell.setBorder(PdfPCell.NO_BORDER);
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
					"Untuk selanjutnya, harap Saudara mengembalikan copy surat ini kepada kami (setelah Saudara tanda tangani) sebagai tanda terima kami, melalui email ke pkd.opr@bni.co.id dan opr.produksi@gmail.com",
					font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15, 85 });
			cell = new PdfPCell(new Paragraph("Subject", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Tanda Terima_Nama Cabang_Surat OPR No./PKD_Tanggal Surat", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15, 85 });
			cell = new PdfPCell(new Paragraph("Isi email", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Tanggal diterima Kiriman, Nama & NPP Penerima", font));
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
			cell = new PdfPCell(new Paragraph("Demikianlah agar Saudara dapat menerima dengan baik.", font));
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
			tablefoot.setWidths(new int[] { 60, 40 });

			PdfPCell cellfoot1 = new PdfPCell();
			cellfoot1.setBorder(PdfPCell.NO_BORDER);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(groupname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
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
			cell.setRowspan(5);
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
			cell = new PdfPCell(new Paragraph("Diterima Tanggal :", font));
			cell.setPadding(7);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Diterima Oleh : \n\n\n\nTanda Tangan, Nama Jelas & Cap", font));
			cell.setPadding(7);
			table.addCell(cell);
			cellfoot2.addElement(table);

			tablefoot.addCell(cellfoot1);
			tablefoot.addCell(cellfoot2);
			document.add(tablefoot);

			document.newPage();
			
			for (Tderivatif objLetter: new TderivatifDAO().listLampiran("tdeliveryfk = " + obj.getTdeliverypk(), "orderdate")) {
				table = new PdfPTable(4);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 6, 44, 30, 20 });
				cell = new PdfPCell(new Paragraph("No", font));
				cell.setUseBorderPadding(true);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Jenis Kartu", font));
				cell.setUseBorderPadding(true);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Nama", font));
				cell.setUseBorderPadding(true);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("No. Kartu", font));
				cell.setUseBorderPadding(true);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);			
				document.add(table);
				int no = 1;
				for (Tderivatifdata data: new TderivatifdataDAO().listDataLampiran("tderivatifdata.tderivatiffk = " + objLetter.getTderivatifpk(),
						"mproduct.productcode, tderivatifproduct.orderdate")) {
					table = new PdfPTable(4);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidthPercentage(100);
					table.setWidths(new int[] { 6, 44, 30, 20 });
					cell = new PdfPCell(new Paragraph(String.valueOf(no++), fonttable));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);				
					cell = new PdfPCell(new Paragraph(data.getTderivatifproduct().getMproduct().getProductname(), fonttable));
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(data.getTembossdata().getNameonid(), fonttable));
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(data.getCardno(), fonttable));
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
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

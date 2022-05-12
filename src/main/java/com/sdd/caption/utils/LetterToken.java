package com.sdd.caption.utils;

import java.io.File;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TtokenitemDAO;
import com.sdd.caption.dao.TtokenorderdataDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.domain.Ttokenorderdata;

public class LetterToken {

	public static void doLetterTokenGenerator(Document document, Font font, Font fonttable, Font fontbold, Tdelivery obj)
			throws Exception {
		try {

			//SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
			String companyname = "";
			String divisiname = "";
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
				else if (obj2.getParamcode().equals(AppUtils.PARAM_CITY))
					city = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_PEMIMPIN))
					pemimpin = obj2.getParamvalue();
				else if (obj2.getParamcode().equals(AppUtils.PARAM_TTD))
					ttd = obj2.getParamvalue();
			}

			List<Torderitem> serialno = new TtokenitemDAO().listSerialnoLetter("tdeliverypk = " + obj.getTdeliverypk() + " and Torderitem.status = '"
					+ AppUtils.STATUS_SERIALNO_OUTPRODUKSI + "'");

			int lastSerial = serialno.size();
			PdfPTable table = null;
			PdfPCell cell = null;

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(city + ",", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 7, 93 });
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
			table.setWidths(new int[] { 7, 93 });
			cell = new PdfPCell(new Paragraph("Lamp", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + serialno.size() + " Unit", font));
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
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kepada", font));
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
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kantor Cabang " + obj.getMbranch().getBranchname(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Up. Pemp. Bid. Pelayanan Nasabah (PBN)", font));
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
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Perihal : Pengiriman Token Instan ", font));
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
					"Menunjuk surat tersebut diatas dan perihal pada pokok surat, dengan ini kami sampaikan  BNI e-Secure "
							+ "Instan  sebanyak " + serialno.size()
							+ " unit  dalam rangka memenuhi kebutuhan cabang tahun 2020 dengan rincian sbb:",
					font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("1.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Menerima dan mendata paket BNI e-Secure.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 6, 3, 91 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("a.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("BNI e-Secure dengan serial Number " + serialno.get(0).getTtokenitem().getItemno()
					+ " sampai " + serialno.get(lastSerial - 1).getTtokenitem().getItemno(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 6, 3, 91 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("b.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Buku Petunjuk Penggunaan BNI e-Secure", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("2.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Report List BNI e-Secure Delivery", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("3.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("lembar Tanda terima BNI e-Secure", font));
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
			cell = new PdfPCell(new Paragraph("Selanjutnya harap Saudara : ", font));
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
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("1.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Menerima dan mendata paket BNI e-Secure. ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("2.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Mengembalikan BNI e-Secure ke Divisi OPR apabila BNI e-Secure rusak atau tidak dapat digunakan.",
					font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 3, 97 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("3.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Mengembalikan copy surat ini kepada kami (setelah Saudara tanda tangani) sebagai bukti tanda "
							+ "terima dari kami melalui email ke pkd.opr@bni.co.id dan opr.produksi@gmail.com.",
					font));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
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
			cell = new PdfPCell(new Paragraph(
					"Demikian kami sampaikan, atas perhatian dan kerjasamanya kami ucapkan terima kasih. ", font));
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
			cell = new PdfPCell(new Paragraph(companyname, fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 67, 33 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(" Diterima Tanggal :", font));
			cell.setPaddingBottom(6);
			cell.setBorderWidthTop(2);
			cell.setBorderWidthBottom(2);
			cell.setBorderWidthRight(2);
			cell.setBorderWidthLeft(2);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 67, 33 });

			if (ttd != null && ttd.trim().length() > 0) {
				String logo_path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/" + ttd);
				if (new File(logo_path).exists()) {
					com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
					img.scaleAbsolute(150f, 70f);
					cell = new PdfPCell(img);
				}
			} else {
				cell = new PdfPCell();
			}
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setRowspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(" Diterima Oleh : ", font));
			cell.setRowspan(5);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthRight(2);
			cell.setBorderWidthLeft(2);
			cell.setBorderWidthBottom(0);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 67, 33 });
			cell = new PdfPCell(new Paragraph(pemimpin, fontbold));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(" Tanda Tangan, Nama Jelas & Cap", font));
			cell.setPaddingBottom(6);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(2);
			cell.setBorderWidthRight(2);
			cell.setBorderWidthLeft(2);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Pemimpin Kelompok ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

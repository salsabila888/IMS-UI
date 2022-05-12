package com.sdd.caption.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TdeliverydataDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.dao.TpinpadorderdataDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tdeliverydata;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.domain.Tpinpadorderdata;
import com.sdd.utils.StringUtils;

public class LetterPinpad {

	public static void doLetterPinpadGenerator(Document document, Font font, Font fonttable, Font fontheadertable,
			Tdelivery obj) throws Exception {
		try {
			String companyname = "";
			String divisiname = "";
			String city = "";
			String pemimpin = "";
			String ttd = "";
			String groupname = "";
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
				else if (obj2.getParamcode().equals(AppUtils.PARAM_GROUPNAME))
					groupname = obj2.getParamvalue();
			}

			List<Torderitem> serialno = new TorderitemDAO()
					.listSerialnoLetter("tdeliverypk = " + obj.getTdeliverypk() + " and tpinpaditem.status = '"
							+ AppUtils.STATUS_SERIALNO_OUTPRODUKSI + "'");

			//int lastSerial = serialno.size();
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
			cell = new PdfPCell(new Paragraph("Hal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Pengiriman Pinpad ", font));
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
			cell = new PdfPCell(new Paragraph(": " + obj.getTotaldata() + " ("
					+ StringUtils.angkaToTerbilang(Long.parseLong(String.valueOf(obj.getTotaldata()))) + ") " + "dan "
					+ serialno.size() + " Unit pinpad", font));
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
			cell = new PdfPCell(new Paragraph("Kepada", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kantor CABANG " + obj.getMbranch().getBranchname(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Up. Bagian Umum", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			Tdeliverydata letterno = new TdeliverydataDAO().findByFilter("tdeliveryfk = " + obj.getTdeliverypk());
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Surat Saudara No : " + letterno.getTpaketdata().getTpaket().getTorder().getOrderid() + " " + new SimpleDateFormat("ddMMyyyy").format(letterno.getTpaketdata().getTpaket().getTorder().getInserttime()), font));
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
			cell = new PdfPCell(
					new Paragraph(
							"Menunjuk Surat tersebut diatas perihal pada pokok surat, dengan ini kami kirimkan "
									+ serialno.size() + " Unit pinpad " + "Ingenico dan Verifone dengan rincian sbb :",
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

			table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 5, 20, 15, 20, 34, 3 });
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("NO", fontheadertable));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("S/N", fontheadertable));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("TID", fontheadertable));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jenis", fontheadertable));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Keterangan", fontheadertable));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			int no = 1;
			for (Torderitem data : serialno) {
				table = new PdfPTable(7);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 3, 5, 20, 15, 20, 34, 3 });
				cell = new PdfPCell(new Paragraph("", font));
				cell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(String.valueOf(no++), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(data.getTpinpaditem().getItemno(), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(data.getTid(), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(AppData.getPinpadtypeLabel(data.getPinpadtype()), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(data.getPinpadmemo(), fonttable));
				cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("", font));
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
					"Kami harapkan bantuan Saudara untuk mengembalikan copy surat ini kepada kami (setelah Saudara "
							+ "tanda tangani) melalui email pkd.opr@bni.co.id . Info lebih lanjut dapat menghubungi Acep "
							+ "Zulqornaini / Isnaeni Gunawan no telp (021) 80826840 – ext. 8576/8569.",
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
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(
					"Demikian kami sampaikan, atas perhatian dan kerjasamanya kami ucapkan terima kasih", font));
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
			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(groupname, font));
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
			cell = new PdfPCell(new Paragraph(pemimpin, font));
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

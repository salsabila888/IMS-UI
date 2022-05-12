package com.sdd.caption.utils;

import java.io.File;
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
import com.sdd.caption.dao.TdeliverydataDAO;
import com.sdd.caption.dao.TpinmailerproductDAO;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tdeliverydata;
import com.sdd.caption.domain.Tpinmailerproduct;
import com.sdd.utils.StringUtils;

public class LetterPinMailer {

	public static void doLetterPinMailerGenerator(Document document, Font font, Font fonttable, Tdelivery obj) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(obj.getProcesstime());
			cal.add(Calendar.DATE, -1);
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

			PdfPTable table = null;
			PdfPCell cell = null;

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);

			cell = new PdfPCell(
					new Paragraph(city + ", " + new SimpleDateFormat("dd MMMMM yyyy").format(obj.getProcesstime()), font));
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
			table.setWidths(new int[] { 7, 93 });
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
			table.setWidths(new int[] { 7, 93 });
			cell = new PdfPCell(new Paragraph("Lamp ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": " + obj.getTotaldata() + " ("
					+ StringUtils.angkaToTerbilang(Long.parseLong(String.valueOf(obj.getTotaldata()))) + ") amplop PIN",
					font));
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
			cell = new PdfPCell(new Paragraph(
					"KCU " + obj.getMbranch().getBranchname() + " (" + obj.getMbranch().getBranchid() + ")", font));
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
			table.setWidths(new int[] { 7, 93 });
			cell = new PdfPCell(new Paragraph("Hal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(": Pengiriman PIN (Personal Identification Number)", font));
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
			cell = new PdfPCell(new Paragraph("Card Production Control Report Tanggal "
					+ new SimpleDateFormat("dd MMMMM yyyy").format(obj.getProcesstime()), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(
					new Paragraph("(Periode Input Tanggal " + dateFormat.format(cal.getTime()) + ")", font));
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
					"Menunjuk laporan Card Production tersebut di atas terlampir kami sampaikan PIN (Personal Identification Number) "
							+ " BNI CARD / ADMIN / EMERALD / Kartu Utama / Kartu Pegawai / Kartu Mahasiswa atas nama nasabah Saudara, dalam amplop tertutup.",
					font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
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

			table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 6, 43, 18, 15 });
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
			cell = new PdfPCell(new Paragraph("Tanggal Data", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Jumlah", font));
			cell.setUseBorderPadding(true);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			document.add(table);
			
			int no = 1;
			List<Tdeliverydata> objList = new TdeliverydataDAO().listByFilter("tdeliveryfk = " + obj.getTdeliverypk(),
					"tdeliverydatapk");
			for (Tdeliverydata data : objList) {
				List<Tpinmailerproduct> productList = new TpinmailerproductDAO().listByFilter(
						"tpinmailerbranchfk = " + data.getTpaketdata().getTpinmailerbranch().getTpinmailerbranchpk(),
						"productcode");
				for (Tpinmailerproduct product : productList) {
					table = new PdfPTable(4);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidthPercentage(100);
					table.setWidths(new int[] { 6, 43, 18, 15 });
					cell = new PdfPCell(new Paragraph(String.valueOf(no++), fonttable));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(product.getMproduct().getProductname(), fonttable));
					table.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(new SimpleDateFormat("dd MMM yyyy").format(product.getOrderdate()), fonttable));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(String.valueOf(product.getTotaldata()), fonttable));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					document.add(table);
				}
			}

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Selanjutnya kami harapkan kerjasama Saudara sebagai berikut :", font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 97 });
			cell = new PdfPCell(new Paragraph("1.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Meneruskan PIN tersebut kepada nasabah pemegang Kartu bersangkutan berikut perangkat Kartu "
							+ "yang diterima dari Divisi Operasional cfm. ketentuan yang berlaku.",
					font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 97 });
			cell = new PdfPCell(new Paragraph("2.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Mengaktifkan Kartu tersebut setelah pemilik Kartu menandatangani dan menyerahkan formulir tanda terima.",
					font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 97 });
			cell = new PdfPCell(new Paragraph("3.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Menginformasikan kepada nasabah untuk segera mengganti PIN tersebut dengan PIN pribadi.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 97 });
			cell = new PdfPCell(new Paragraph("4. ", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(
					"Segera mengembalikan copy surat ini kepada kami 1 (satu) minggu setelah Saudara tanda tangani "
							+ "dan dibubuhi cap/stempel serta tanggal penerimaan surat tersebut.",
					font));
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
					"Demikian untuk diterima dengan baik, atas perhatian Saudara kami ucapkan terima kasih.", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
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
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			if (ttd != null && ttd.trim().length() > 0) {
				String logo_path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.IMAGE_PATH + "/" + ttd);
				if (new File(logo_path).exists()) {
					com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
					img.scaleAbsolute(150f, 70f);
					cell = new PdfPCell(img);
				}
			} else cell = new PdfPCell();
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

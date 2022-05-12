package com.sdd.caption.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sdd.caption.dao.TderivatifdataDAO;
import com.sdd.caption.dao.TembossdataDAO;
import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Tembossdata;
import com.sdd.caption.domain.Tpaket;
import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;

public class PaketManifestHandler {
	
	public static void doLabelPrint(List<Tpaketdata> objList) throws Exception {
		try {
			Sessions.getCurrent().setAttribute("objList", objList);
			Sessions.getCurrent().setAttribute("reportPath", Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(SysUtils.JASPER_PATH + "/paketlabel.jasper"));
			Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void doOutputPrint(List<Tpaketdata> listPaket) throws Exception {
		SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			String filename = "OUTPUT" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".pdf";
			Font font = new Font(Font.FontFamily.TIMES_ROMAN, 8);
			String output = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH + filename);
			Document document = new Document(new Rectangle(PageSize.A4));
			PdfWriter.getInstance(document, new FileOutputStream(output));
			document.open();

			for (Tpaketdata obj : listPaket) {
				document.newPage();

				int no = 1;
				String branchid = "";
				List<Tembossdata> objList = new TembossdataDAO().listByFilter("tembossbranch.tembossbranchpk = " + obj.getTembossbranch().getTembossbranchpk(), "tembossdatapk desc");
				for (Tembossdata data : objList) {
					if (!data.getMbranch().getBranchid().equals(branchid)) {
						if (branchid.length() > 0)
							document.newPage();

						PdfPTable table = new PdfPTable(2);
						table.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.setWidthPercentage(100);
						table.setWidths(new int[] { 30, 70 });
						PdfPCell cell1 = new PdfPCell(new Paragraph("WAKTU MANIFEST", font));
						cell1.setBorder(PdfPCell.NO_BORDER);
						PdfPCell cell2 = new PdfPCell(
								new Paragraph(": " + dateLocalFormatter.format(obj.getTpaket().getProcesstime()), font));
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);
						table = new PdfPTable(2);
						table.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.setWidthPercentage(100);
						table.setWidths(new int[] { 30, 70 });
						cell1 = new PdfPCell(new Paragraph("CABANG", font));
						cell1.setBorder(PdfPCell.NO_BORDER);
						cell2 = new PdfPCell(new Paragraph(
								": " + data.getBranchid() + "-" + data.getBranchname(),
								font));
						cell2.setBorder(PdfPCell.NO_BORDER);
						table.addCell(cell1);
						table.addCell(cell2);
						document.add(table);

						document.add(new Paragraph(" "));

						table = new PdfPTable(9);
						table.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.setWidthPercentage(100);
						table.setWidths(new int[] { 4, 10, 14, 4, 18, 12, 16, 10, 12 });
						cell1 = new PdfPCell(new Paragraph("NO", font));
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell2 = new PdfPCell(new Paragraph("No Paket", font));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						PdfPCell cell3 = new PdfPCell(new Paragraph("ID PRODUK", font));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						PdfPCell cell4 = new PdfPCell(new Paragraph("KLN", font));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						PdfPCell cell5 = new PdfPCell(new Paragraph("NAMA", font));
						PdfPCell cell6 = new PdfPCell(new Paragraph("TGL DATA", font));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						PdfPCell cell7 = new PdfPCell(new Paragraph("PRODUK", font));
						PdfPCell cell8 = new PdfPCell(new Paragraph("KODE PRODUK", font));
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						PdfPCell cell9 = new PdfPCell(new Paragraph("SEQ NUM", font));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell1);
						table.addCell(cell2);
						table.addCell(cell3);
						table.addCell(cell4);
						table.addCell(cell5);
						table.addCell(cell6);
						table.addCell(cell7);
						table.addCell(cell8);
						table.addCell(cell9);
						document.add(table);
						no = 1;
					}
					branchid = data.getMbranch().getBranchid();
					PdfPTable table = new PdfPTable(9);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.setWidthPercentage(100);
					table.setWidths(new int[] { 4, 10, 14, 4, 18, 12, 16, 10, 12 });
					PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(no++), font));
					cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					PdfPCell cell2 = new PdfPCell(new Paragraph(obj.getNopaket(), font));
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell3 = new PdfPCell(new Paragraph(data.getCardno(), font));
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell4 = new PdfPCell(new Paragraph(data.getKlncode(), font));
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell5 = new PdfPCell(new Paragraph(data.getNameonid(), font));
					PdfPCell cell6 = new PdfPCell(new Paragraph(dateLocalFormatter.format(data.getOrderdate()), font));
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell7 = new PdfPCell(new Paragraph(data.getMproduct().getProductname(), font));
					PdfPCell cell8 = new PdfPCell(new Paragraph(data.getProductcode(), font));
					cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell9 = new PdfPCell(new Paragraph(data.getSeqno(), font));
					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell1);
					table.addCell(cell2);
					table.addCell(cell3);
					table.addCell(cell4);
					table.addCell(cell5);
					table.addCell(cell6);
					table.addCell(cell7);
					table.addCell(cell8);
					table.addCell(cell9);
					document.add(table);
				}
			}
			document.close();

			Filedownload.save(
					new File(Executions.getCurrent().getDesktop().getWebApp()
							.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH + filename)),
					"application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void doDataPrint(List<Tpaket> objList) throws Exception {
		SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();

			int rownum = 0;
			int cellnum = 0;
			Integer no = 0;
			Integer total = 0;
			org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("Laporan Paket");
			rownum++;
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue("Tanggal");
			cell = row.createCell(1);
			cell.setCellValue(dateLocalFormatter.format(new Date()));

			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			rownum++;
			Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
			datamap.put(1, new Object[] { "No", "No Paket", "Grup Produk", "Kode Produk", "Jenis Produk",
					"Tanggal Data", "Tanggal Produksi", "Total", "Status" });
			no = 2;
			for (Tpaket data : objList) {
				datamap.put(no,
						new Object[] { no - 1, data.getPaketid(), data.getProductgroup(), data.getMproduct().getProductcode(),
								data.getMproduct().getProductname(), dateLocalFormatter.format(data.getOrderdate()),
								dateLocalFormatter.format(data.getProcesstime()), data.getTotaldata(),
								data.getStatus() });
				no++;
				total += data.getTotaldata();
			}
			datamap.put(no, new Object[] { "", "", "TOTAL", "", "", "", "", total });
			Set<Integer> keyset = datamap.keySet();
			for (Integer key : keyset) {
				row = sheet.createRow(rownum++);
				Object[] objArr = datamap.get(key);
				cellnum = 0;
				for (Object obj : objArr) {
					cell = row.createCell(cellnum++);
					if (obj instanceof String)
						cell.setCellValue((String) obj);
					else if (obj instanceof Integer)
						cell.setCellValue((Integer) obj);
					else if (obj instanceof Double)
						cell.setCellValue((Double) obj);
				}
			}

			String path = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
			String filename = "CAPTION_DAFTAR_PAKET_" + new SimpleDateFormat("yyMMddHHmm").format(new Date())
					+ ".xlsx";
			FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
			workbook.write(out);
			out.close();

			Filedownload.save(new File(path + "/" + filename),
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}

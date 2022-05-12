package com.sdd.caption.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tderivatifdata;

public class LampiranDerivatif {

	public static void doGenerate(List<Tderivatifdata> objList, Tdelivery obj) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);

		int rownum = 0;
		int cellnum = 0;
		Integer no = 0;
		org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(0);
		cell.setCellValue("Cabang : " + obj.getMbranch().getBranchname());
		rownum++;
		Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
		datamap.put(1, new Object[] { "No", "No Kartu", "Nama", "Tgl Data", "Kode Produk", "Nama Produk" });
		no = 2;
		for (Tderivatifdata data : objList) {
			datamap.put(no,
					new Object[] { no - 1, data.getCardno(), data.getTembossdata().getNameonid(),
							new SimpleDateFormat("dd-MM-yyyy").format(data.getTembossdata().getOrderdate()),
									data.getTderivatifproduct().getMproduct().getProductcode(),
									data.getTderivatifproduct().getMproduct().getProductname() });
			no++;
		}
		Set<Integer> keyset = datamap.keySet();
		for (Integer key : keyset) {
			row = sheet.createRow(rownum++);
			Object[] objArr = datamap.get(key);
			cellnum = 0;
			if (rownum == 3) {
				XSSFCellStyle styleHeader = workbook.createCellStyle();
				styleHeader.setBorderTop(BorderStyle.MEDIUM);
				styleHeader.setBorderBottom(BorderStyle.MEDIUM);
				styleHeader.setBorderLeft(BorderStyle.MEDIUM);
				styleHeader.setBorderRight(BorderStyle.MEDIUM);
				styleHeader.setFillForegroundColor(IndexedColors.AQUA.getIndex());
				styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
				for (Object object : objArr) {
					cell = row.createCell(cellnum++);
					if (object instanceof String) {
						cell.setCellValue((String) object);
						cell.setCellStyle(styleHeader);
					} else if (object instanceof Integer) {
						cell.setCellValue((Integer) object);
						cell.setCellStyle(styleHeader);
					} else if (object instanceof Double) {
						cell.setCellValue((Double) object);
						cell.setCellStyle(styleHeader);
					}
				}
			} else {
				for (Object object : objArr) {
					cell = row.createCell(cellnum++);
					if (object instanceof String) {
						cell.setCellValue((String) object);
						cell.setCellStyle(style);
					} else if (object instanceof Integer) {
						cell.setCellValue((Integer) object);
						cell.setCellStyle(style);
					} else if (object instanceof Double) {
						cell.setCellValue((Double) object);
						cell.setCellStyle(style);
					}
				}
			}
		}

		String path = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
		String filename = "LAMPIRAN_DERIVATIF" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
		FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
		workbook.write(out);
		out.close();

		Filedownload.save(new File(path + "/" + filename),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

}

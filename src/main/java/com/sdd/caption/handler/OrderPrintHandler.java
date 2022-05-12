package com.sdd.caption.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.zkoss.zul.Messagebox;

import com.sdd.caption.domain.Morg;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Tperso;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.StringUtils;
import com.sdd.utils.SysUtils;

public class OrderPrintHandler {
	
	public static void doBonPrint(List<Torder> objList, String outputfile, String productgroup) throws Exception {
		SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			if (outputfile.equals("pdf")) {
				Map<String, String> parameters = new HashMap<>();
				parameters.put("PRODUCTGROUP", AppData.getProductgroupLabel(productgroup));
				Sessions.getCurrent().setAttribute("objList", objList);
				Sessions.getCurrent().setAttribute("parameters", parameters);
				Sessions.getCurrent().setAttribute("reportPath", Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(SysUtils.JASPER_PATH + "/bonproduk.jasper"));
				Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");
			} else if (outputfile.equals("xls")) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();

				int rownum = 0;
				int cellnum = 0;
				Integer no = 0;
				Integer total = 0;
				org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue("PT. BANK NEGARA INDONESIA (PERSERO) Tbk.");
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("DIVISI OPERASIONAL");
				row = sheet.createRow(rownum++);
				row = sheet.createRow(rownum++);
				cell = row.createCell(3);
				cell.setCellValue("BON " + AppData.getProductgroupLabel(productgroup));
				row = sheet.createRow(rownum++);
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Tanggal : ");
				cell = row.createCell(1);
				cell.setCellValue(dateTimeFormatter.format(new Date()));
				row = sheet.createRow(rownum++);

				Map<String, String> mapOrg = new HashMap<>();
				for (Morg morg : AppData.getMorg()) {
					mapOrg.put(morg.getOrg(), morg.getDescription());
				}

				Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
				datamap.put(1, new Object[] { "No", "Order ID", "Tgl Order", "Cabang",
						"Jenis Produk", "Jumlah", "Petugas", "Paraf" });
				no = 2;
				for (Torder data : objList) {
					datamap.put(no,
							new Object[] { no - 1, data.getOrderid(), dateLocalFormatter.format(data.getInserttime()), data.getMbranch() != null ? data.getMbranch().getBranchname() : "OPR",
									data.getMproduct().getProductname(), data.getItemqty(),
									data.getInsertedby(), "" });
					no++;
					total += data.getItemqty();
				}
				datamap.put(no, new Object[] { "", "", "", "TOTAL", total, "", "", "" });
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

				row = sheet.createRow(rownum++);
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Mengetahui");
				cell = row.createCell(3);
				cell.setCellValue("Yang Menyerahkan Barang");
				cell = row.createCell(6);
				cell.setCellValue("Yang Meminta Barang");
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Pemimpin Kelompok / Pengelola");
				cell = row.createCell(3);
				cell.setCellValue("Unit Inventory");
				cell = row.createCell(6);
				cell.setCellValue("Unit Produksi");
				row = sheet.createRow(rownum++);

				String path = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
				String filename = "CAPTION_BON_"
						+ new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
				FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
				workbook.write(out);
				out.close();

				Filedownload.save(new File(path + "/" + filename),
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void doManifestPrint(List<Tperso> objList, String outputfile, String operators) throws Exception {
		SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat datelocFormatter = new SimpleDateFormat("dd-MMMM-yyyy");
		try {			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("OPERATORS", operators);

			if (outputfile.equals("pdf")) {
				Sessions.getCurrent().setAttribute("objList", objList);
				Sessions.getCurrent().setAttribute("parameters", parameters);
				Sessions.getCurrent().setAttribute("reportPath",
						Executions.getCurrent().getDesktop().getWebApp()
								.getRealPath(SysUtils.JASPER_PATH + "/persomanifestorder.jasper"));
				Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");
			} else if (outputfile.equals("xls")) {
				if (objList != null && objList.size() > 0) {
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet sheet = workbook.createSheet();

					int rownum = 0;
					int cellnum = 0;
					Integer no = 0;
					Integer total = 0;
					org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
					Cell cell = row.createCell(0);
					cell.setCellValue("PT. BANK NEGARA INDONESIA (PERSERO) Tbk.");
					row = sheet.createRow(rownum++);
					cell = row.createCell(0);
					cell.setCellValue("DIVISI OPERASIONAL");
					row = sheet.createRow(rownum++);
					row = sheet.createRow(rownum++);
					cell = row.createCell(3);
					cell.setCellValue("FORM PERINTAH CETAK KARTU");
					row = sheet.createRow(rownum++);
					row = sheet.createRow(rownum++);
					cell = row.createCell(0);
					cell.setCellValue("Tanggal : ");
					cell = row.createCell(1);
					cell.setCellValue(dateLocalFormatter.format(new Date()));
					row = sheet.createRow(rownum++);
					cell = row.createCell(0);
					cell.setCellValue("Operator : ");
					cell = row.createCell(1);
					cell.setCellValue(operators);
					cell = row.createCell(3);
					cell.setCellValue("Paraf : ");
					row = sheet.createRow(rownum++);

					Map<String, String> mapOrg = new HashMap<>();
					for (Morg morg : AppData.getMorg()) {
						mapOrg.put(morg.getOrg(), morg.getDescription());
					}

					Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
					datamap.put(1,
							new Object[] { "No", "No Manifest", "Tgl Data", "Org", "Tipe Kartu",
									"Kode Kartu", "Jenis Kartu", "Jumlah", "Pemroses", "Paraf" });
					no = 2;
					for (Tperso data : objList) {
						datamap.put(no, new Object[] { no - 1, data.getPersoid(),
								dateLocalFormatter.format(data.getOrderdate()),
								mapOrg.get(data.getTembossproduct().getMproduct().getMproducttype().getProductorg()),
								data.getTembossproduct().getMproduct().getMproducttype().getProducttype(),
								data.getTembossproduct().getMproduct().getProductcode(),
								data.getTembossproduct().getMproduct().getProductname(), data.getTotaldata(),
								data.getPersostartby(), "" });
						no++;
						total += data.getTotaldata();
					}
					datamap.put(no, new Object[] { "", "", "", "", "", "", "TOTAL", total });
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

					row = sheet.createRow(rownum++);
					row = sheet.createRow(rownum++);
					cell = row.createCell(5);
					cell.setCellValue("Tangerang Selatan, " + datelocFormatter.format(new Date()));
					row = sheet.createRow(rownum++);
					cell = row.createCell(5);
					cell.setCellValue("Pemimpin Kelompok / Pengelola");
					row = sheet.createRow(rownum++);

					String path = Executions.getCurrent().getDesktop().getWebApp()
							.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.REPORT_PATH);
					String filename = "CAPTION_PERINTAH_CETAK_"
							+ new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
					FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
					workbook.write(out);
					out.close();

					Filedownload.save(new File(path + "/" + filename),
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				} else {
					Messagebox.show("Data tidak tersedia", "Info", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void doListPrint(List<Tperso> objList, Integer month, Integer year, String status) throws Exception {
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
			cell.setCellValue("Daftar Perso");
			rownum++;
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue("Periode");
			cell = row.createCell(1);
			cell.setCellValue(StringUtils.getMonthLabel(month) + " " + year);
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue("Status");
			cell = row.createCell(1);
			cell.setCellValue(status != null && status.length() > 0 ? AppData.getStatusLabel(status) : "ALL");

			rownum++;

			Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
			datamap.put(1, new Object[] { "No", "No Manifest", "Kode Produk", "Tipe Produk", "Jenis Produk",
					"Tanggal Order", "Dibuat Oleh", "Total Data", "Status" });
			no = 2;
			for (Tperso data : objList) {
				datamap.put(no,
						new Object[] { no - 1, data.getPersoid(), data.getTembossproduct().getProductcode(),
								data.getTembossproduct().getMproduct().getMproducttype().getProducttype(),
								data.getTembossproduct().getMproduct().getProductname(),
								dateLocalFormatter.format(data.getOrderdate()), data.getPersostartby(),
								NumberFormat.getInstance().format(data.getTotaldata()),
								AppData.getStatusLabel(data.getStatus()) });
				no++;
				total += data.getTotaldata();
			}
			datamap.put(no, new Object[] { "", "TOTAL", "", "", "", "", "", total, "", "" });
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
			String filename = "CAPTION_DAFTAR_ORDER_" + new SimpleDateFormat("yyMMddHHmm").format(new Date())
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

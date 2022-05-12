package com.sdd.caption.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;

public class ReportBranchStockByProductVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	List<Tbranchstock> objList = new ArrayList<>();
	private TbranchstockDAO oDao = new TbranchstockDAO();
	private String filter;

	private BigDecimal total;
	private String productcode;
	private String datereport;
	private Mbranch mbranch;
	private Mproducttype mproducttype;

	@Wire
	private Combobox cbBranch, cbProducttype;
	@Wire
	private Grid grid;
	@Wire
	private Row rowBranch;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		if (oUser != null) {
			if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
				rowBranch.setVisible(true);
			} else {
				rowBranch.setVisible(false);
			}
		}

		datereport = new SimpleDateFormat("dd MMM yyyy").format(new Date());
		doReset();

		grid.setRowRenderer(new RowRenderer<Tbranchstock>() {
			@Override
			public void render(Row row, final Tbranchstock data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				row.getChildren().add(new Label(data.getMbranch().getBranchid()));
				row.getChildren().add(new Label(data.getMbranch().getBranchname()));
				row.getChildren().add(new Label(data.getMproduct().getProductcode()));
				row.getChildren().add(new Label(data.getMproduct().getProductname()));
				row.getChildren().add(new Label(data.getMproduct().getMproducttype().getProducttype()));
				row.getChildren().add(new Label(NumberFormat.getInstance().format(data.getStockcabang())));
//				A aInstant = new A(
//						data.getStockcabang() != null ? NumberFormat.getInstance().format(data.getStockcabang()) : "0");
//				aInstant.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//
//					@Override
//					public void onEvent(Event event) throws Exception {
//						Map<String, Object> map = new HashMap<>();
//						map.put("obj", data);
//
//						Window win = new Window();
//						Morg org = new MorgDAO().findById(data.getMproduct().getMproducttype().getProductorg());
//						if (org.getIsneeddoc().equals("N")) {
//							System.out.println("REGULAR");
//							win = (Window) Executions.createComponents("/view/report/reportbranchstockdatadetail.zul",
//									null, map);
//						} else {
//							System.out.println("DERIVATIF");
//							win = (Window) Executions
//									.createComponents("/view/report/reportbranchstockdrvdatadetail.zul", null, map);
//						}
//						win.setWidth("90%");
//						win.setClosable(true);
//						win.doModal();
//					}
//				});
//				row.getChildren().add(data.getStockcabang() != null ? aInstant : new Label("0"));
			}
		});
	}

	@Command
	public void doExport() {
		try {
			if (objList != null && objList.size() > 0) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();

				int rownum = 0;
				int cellnum = 0;
				Integer no = 0;
				org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue("Laporan Stock Cabang By Produk");
				rownum++;
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Tanggal");
				cell = row.createCell(1);
				cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

				row = sheet.createRow(rownum++);

				Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
				datamap.put(1, new Object[] { "No", "Kode Cabang", "Cabang", "Kode Produk", "Nama Product",
						"Tipe Produk", "Stock Cabang" });
				no = 2;
				for (Tbranchstock data : objList) {
					datamap.put(no,
							new Object[] { no - 1, data.getMbranch().getBranchid(), data.getMbranch().getBranchname(),
									data.getMproduct().getProductcode(), data.getMproduct().getProductname(),
									data.getMproduct().getMproducttype().getProducttype(),
									data.getStockcabang() != null
											? NumberFormat.getInstance().format(data.getStockcabang())
											: "0" });
					no++;
				}
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
				String filename = "CIMS_BRANCHSTOCK_" + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + ".xlsx";
				FileOutputStream out = new FileOutputStream(new File(path + "/" + filename));
				workbook.write(out);
				out.close();

				Filedownload.save(new File(path + "/" + filename),
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			} else {
				Messagebox.show("Data tidak tersedia", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("Error : " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@NotifyChange("*")
	public void refreshModel() {
		try {
			total = new BigDecimal(0);
			objList = oDao.listBranchStockByProduct(filter, "branchid");
			grid.setModel(new ListModelList<>(objList));

			for (Tbranchstock data : objList) {
				total = total.add(new BigDecimal(data.getStockcabang()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("total")
	public void doSearch() {
		if (oUser != null) {
			if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 700) {
				filter = "";
			}else if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) < 700
					&& Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
				filter = "MREGIONPK = " + oUser.getMbranch().getMregion().getMregionpk();
			}else {
				filter = "MBRANCHFK = " + oUser.getMbranch().getMbranchpk();
			}
			
			if (mbranch != null) {
				if (filter.trim().length() > 0)
					filter += " AND ";
				filter += "MBRANCHFK = " + mbranch.getMbranchpk();
			}

			if (productcode != null && productcode.trim().length() > 0) {
				if (filter.trim().length() > 0)
					filter += " AND ";
				filter = "PRODUCTCODE LIKE '%" + productcode.trim().toUpperCase() + "%'";
			}

			if (mproducttype != null) {
				if (filter.trim().length() > 0)
					filter += " AND ";
				filter = "MPRODUCTTYPEFK = " + mproducttype.getMproducttypepk();
			}

			refreshModel();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		total = new BigDecimal(0);
		productcode = "";
		mproducttype = null;
		mbranch = null;
		cbBranch.setValue(null);
		cbProducttype.setValue(null);
		if (grid.getRows() != null)
			grid.getRows().getChildren().clear();
		doSearch();
	}
	
	public ListModelList<Mbranch> getMbranchmodel() {
		ListModelList<Mbranch> lm = null;
		try {
			if (oUser != null) {
				if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600
						&& Integer.parseInt(oUser.getMbranch().getBranchid().trim()) < 700) {
					lm = new ListModelList<Mbranch>(
							AppData.getMbranch("mregionfk = " + oUser.getMbranch().getMregion().getMregionpk()));
				} else {
					lm = new ListModelList<Mbranch>(AppData.getMbranch());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Mproducttype> getMproducttypemodel() {
		ListModelList<Mproducttype> lm = null;
		try {
			lm = new ListModelList<Mproducttype>(AppData.getMproducttype());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getDatereport() {
		return datereport;
	}

	public void setDatereport(String datereport) {
		this.datereport = datereport;
	}

	public Mproducttype getMproducttype() {
		return mproducttype;
	}

	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}

	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}
}

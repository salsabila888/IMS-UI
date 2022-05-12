package com.sdd.caption.viewmodel;

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
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Vbranchstock;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;

public class ReportBranchStockVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	List<Vbranchstock> objList;
	private TbranchstockDAO oDao = new TbranchstockDAO();
	private String filter;
	private String filterBranch;

	private Integer total;
	private Integer totalbernama;
	private String datereport;
	private Mbranch mbranch;
	private Mproducttype mproducttype;

	@Wire
	private Combobox cbBranch;
	@Wire
	private Grid grid;
	@Wire
	private Row rowBranch;
	@Wire
	private Button btnSearch, btnReset;

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
				btnSearch.setVisible(false);
				btnReset.setVisible(false);
			}
		}
		datereport = new SimpleDateFormat("dd MMM yyyy").format(new Date());
		doReset();

		grid.setRowRenderer(new RowRenderer<Vbranchstock>() {
			@Override
			public void render(Row row, final Vbranchstock data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				row.getChildren().add(new Label(data.getBranchid()));
				row.getChildren().add(new Label(data.getBranchname()));
				row.getChildren().add(new Label("KARTU"));
				A aInstant = new A(
						data.getTotalinstant() != null ? NumberFormat.getInstance().format(data.getTotalinstant())
								: "0");
				aInstant.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						map.put("obj", data);
						map.put("isinstant", "Y");

						Window win = (Window) Executions.createComponents("/view/report/reportbranchstockdata.zul",
								null, map);
						win.setWidth("90%");
						win.setClosable(true);
						win.doModal();
					}
				});

				row.getChildren().add(data.getTotalinstant() != null ? aInstant : new Label("0"));

				A aNotInstant = new A(
						data.getTotalnotinstant() != null ? NumberFormat.getInstance().format(data.getTotalnotinstant())
								: "0");
				aNotInstant.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						map.put("obj", data);
						map.put("isinstant", "N");

						Window win = (Window) Executions.createComponents("/view/report/reportbranchstockdata.zul",
								null, map);
						win.setWidth("90%");
						win.setClosable(true);
						win.doModal();
					}
				});

				row.getChildren().add(data.getTotalnotinstant() != null ? aNotInstant : new Label("0"));
			}
		});
	}

	@NotifyChange("*")
	public void refreshModel() {
		try {
			total = 0;
			totalbernama = 0;
			objList = oDao.listBranchstock(filter, filterBranch, "branchid");
			grid.setModel(new ListModelList<>(objList));

			for (Vbranchstock obj : objList) {
				if (obj.getTotalinstant() != null) {
					total = total + obj.getTotalinstant();
				}
				if (obj.getTotalnotinstant() != null) {
					totalbernama = totalbernama + obj.getTotalnotinstant();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "total", "totalbernama" })
	public void doSearch() {
		if (oUser != null) {
			if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 700) {
				filter = "";
			}else if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) < 700
					&& Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
				filter = "MREGIONPK = " + oUser.getMbranch().getMregion().getMregionpk();
				filterBranch = "F.MREGIONFK = " + oUser.getMbranch().getMregion().getMregionpk();
			}else {
				filter = "MBRANCHPK = " + oUser.getMbranch().getMbranchpk();
				filterBranch = "F.MBRANCHPK = " + oUser.getMbranch().getMbranchpk();
			}
			
			if (mbranch != null) {
				filter += "MBRANCHPK = " + mbranch.getMbranchpk();
				filterBranch = "F.MBRANCHPK = " + mbranch.getMbranchpk();
			}

			if (mproducttype != null) {
				filter += "MPRODUCTTYPEFK = " + mproducttype.getMproducttypepk();
			}

			refreshModel();
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		total = 0;
		totalbernama = 0;
		mbranch = null;
		cbBranch.setValue(null);
		if (grid.getRows() != null)
			grid.getRows().getChildren().clear();
		doSearch();
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
				cell.setCellValue("Laporan Stock Cabang");
				rownum++;
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Cabang");
				cell = row.createCell(1);
				cell.setCellValue(mbranch != null ? mbranch.getBranchname() : "ALL");
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue("Tanggal");
				cell = row.createCell(1);
				cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

				row = sheet.createRow(rownum++);

				Map<Integer, Object[]> datamap = new TreeMap<Integer, Object[]>();
				datamap.put(1, new Object[] { "No", "Kode Cabang", "Nama Cabang", "Grup Produk", "Stock Instant",
						"Stock Bernama" });
				no = 2;
				for (Vbranchstock data : objList) {
					datamap.put(no, new Object[] { no - 1, data.getBranchid(), data.getBranchname(), "KARTU",
							data.getTotalinstant() != null ? NumberFormat.getInstance().format(data.getTotalinstant())
									: "0",
							data.getTotalnotinstant() != null
									? NumberFormat.getInstance().format(data.getTotalnotinstant())
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

	public ListModelList<Mproduct> getMproductmodel() {
		ListModelList<Mproduct> lm = null;
		try {
			lm = new ListModelList<Mproduct>(AppData.getMproduct());
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalbernama() {
		return totalbernama;
	}

	public void setTotalbernama(Integer totalbernama) {
		this.totalbernama = totalbernama;
	}

	public String getDatereport() {
		return datereport;
	}

	public void setDatereport(String datereport) {
		this.datereport = datereport;
	}

	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public Mproducttype getMproducttype() {
		return mproducttype;
	}

	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}


}

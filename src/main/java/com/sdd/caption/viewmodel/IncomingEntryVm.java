package com.sdd.caption.viewmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TincomingDAO;
import com.sdd.caption.dao.TsecuritiesitemDAO;
import com.sdd.caption.dao.TtokenitemDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Msupplier;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tincoming;
import com.sdd.caption.domain.Tsecuritiesitem;
import com.sdd.caption.domain.Ttokenitem;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class IncomingEntryVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Session session;
	private Transaction transaction;

	private Tincoming objForm;
	private TincomingDAO oDao = new TincomingDAO();

	private String filename;
	private String productgroup;
	private String endno;
	private Integer startno;
	private String unit;
	private Integer totaldata;
	private String prefix;

	private Media media;
	private String arg;

	@Wire
	private Row rowPinpad, rowHarga, rowStartno, rowEndno;
	@Wire
	private Combobox cbTypeProduct, cbVendor, cbCabang;
	@Wire
	private Intbox quantity;
	@Wire
	private Button btnSave;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		this.arg = arg;

		if (arg.equals(AppUtils.PRODUCTGROUP_PINPAD)) {
			rowPinpad.setVisible(true);
			rowStartno.setVisible(false);
			rowEndno.setVisible(false);
			quantity.setReadonly(true);
		}

		if (arg.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
			rowHarga.setVisible(true);
		}

		// getProductAutocomplete();
		doReset();
	}

	@SuppressWarnings("unused")
	@Command
	@NotifyChange({ "filename", "totaldata" })
	public void doBrowse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		try {
			UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
			media = event.getMedia();
			filename = media.getName();
			if (media != null) {
				Tincoming file = oDao.findByFilter("filename = '" + media.getName() + "'");
				if (file == null) {
					if (media.getFormat().contains("xls")) {
						btnSave.setDisabled(false);

						totaldata = 0;
						String serialno = "";

						Workbook wb = null;
						if (media.getName().toLowerCase().endsWith("xlsx")) {
							wb = new XSSFWorkbook(media.getStreamData());
						} else if (media.getName().toLowerCase().endsWith("xls")) {
							wb = new HSSFWorkbook(media.getStreamData());
						}
						Sheet sheet = wb.getSheetAt(0);
						for (org.apache.poi.ss.usermodel.Row row : sheet) {
							try {
								if (row.getRowNum() < 1) {
									continue;
								}

								for (int count = 0; count <= row.getLastCellNum(); count++) {
									Cell cell = row.getCell(count,
											org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
									if (cell == null) {
										continue;
									}

									switch (count) {
									case 1:
										if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
											cell.setCellType(Cell.CELL_TYPE_STRING);
											serialno = cell.getStringCellValue();
										} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
											serialno = cell.getStringCellValue();
										}
										break;
									}
								}

								totaldata++;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else {
						btnSave.setDisabled(true);
						Messagebox.show("Format data harus berupa xls/xlsx", "Exclamation", Messagebox.OK,
								Messagebox.EXCLAMATION);
					}
				} else {
					btnSave.setDisabled(true);
					Messagebox.show("File sudah pernah diupload", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			boolean isValid = true;
			if (arg.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
				List<Tsecuritiesitem> dataList = new TsecuritiesitemDAO().listNativeByFilter(
						"mproducttypefk = " + objForm.getMproducttype().getMproducttypepk()
						+ " and prefix = '" + prefix.trim().toUpperCase() + "' and numerator between " + startno
						+ " and " + endno.trim(),
						"tsecuritiesitempk");
				if (dataList.size() > 0)
					isValid = false;
			} else if (arg.equals(AppUtils.PRODUCTGROUP_TOKEN)) {
				List<Ttokenitem> dataList = new TtokenitemDAO().listNativeByFilter(
						"mproducttypefk = " + objForm.getMproducttype().getMproducttypepk()
								+ " and numerator between " + startno + " and " + endno + "",
						"ttokenitempk");
				if (dataList.size() > 0)
					isValid = false;
			}

			if (isValid) {
				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();
				objForm.setIncomingid(new TcounterengineDAO().generateYearMonthCounter(AppUtils.CE_INVENTORY_INCOMING));
				objForm.setStatus(AppUtils.STATUS_INVENTORY_INCOMINGWAITAPPROVAL);
				objForm.setEntryby(oUser.getUsername());
				objForm.setProductgroup(arg);
				objForm.setItemqty(totaldata);
				objForm.setItemstartno(startno);
				objForm.setPrefix(prefix);

				if (media != null) {
					String path = Executions.getCurrent().getDesktop().getWebApp()
							.getRealPath(AppUtils.FILES_ROOT_PATH + AppUtils.PATH_PINPAD);
					if (media.isBinary()) {
						Files.copy(new File(path + "/" + media.getName()), media.getStreamData());
					} else {
						BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + media.getName()));
						Files.copy(writer, media.getReaderData());
						writer.close();
					}

					objForm.setFilename(filename);
				}

				oDao.save(session, objForm);
				transaction.commit();
				session.close();

				Clients.showNotification("Entri data incoming berhasil", "info", null, "middle_center", 5000);
				doReset();
			} else {
				Messagebox.show("Nomer seri sudah pernah didaftarkan", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("endno")
	public void doGetEndno() {
		if (startno != null && startno > 0) {
			if (totaldata != null && totaldata > 0) {
				if (prefix != null && !"".equals(prefix)) {
					endno = String.valueOf(startno + totaldata - 1);
				}
			}
		}
	}

	@NotifyChange("*")
	public void doReset() {
		objForm = new Tincoming();
		objForm.setEntrytime(new Date());

		filename = "";
		endno = "";
		prefix = "";
		totaldata = null;
		media = null;
		productgroup = AppData.getProductgroupLabel(arg);

		if (arg.equals("04"))
			unit = arg;
		else
			unit = "02";

		cbCabang.setValue(null);
		cbTypeProduct.setValue(null);
		cbVendor.setValue(null);

		if (oUser.getMbranch().getBranchlevel() == 3) {
			objForm.setMbranch(oUser.getMbranch());
			cbCabang.setValue(oUser.getMbranch().getBranchname());
			cbCabang.setReadonly(true);
		}

	}

	public ListModelList<Mbranch> getMbranchmodel() {
		ListModelList<Mbranch> lm = null;
		try {
			if (oUser != null) {
				if (oUser.getMbranch().getBranchlevel() == 2) {
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

	public ListModelList<Mproducttype> getMproducttypes() {
		ListModelList<Mproducttype> lm = null;
		try {
			lm = new ListModelList<Mproducttype>(AppData.getMproducttype("productgroupcode = '" + arg + "'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public ListModelList<Msupplier> getMsupplier() {
		ListModelList<Msupplier> lm = null;
		try {
			lm = new ListModelList<Msupplier>(AppData.getMsupplier());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				try {
					Mproducttype mproduct = (Mproducttype) ctx.getProperties("mproducttype")[0].getValue();
					if (mproduct == null)
						this.addInvalidMessage(ctx, "mproducttype", Labels.getLabel("common.validator.empty"));

					Mbranch mbranch = (Mbranch) ctx.getProperties("mbranch")[0].getValue();
					if (mbranch == null)
						this.addInvalidMessage(ctx, "mbranch", Labels.getLabel("common.validator.empty"));

					Msupplier msupplier = (Msupplier) ctx.getProperties("msupplier")[0].getValue();
					if (msupplier == null)
						this.addInvalidMessage(ctx, "msupplier", Labels.getLabel("common.validator.empty"));

					BigDecimal harga = (BigDecimal) ctx.getProperties("harga")[0].getValue();
					if (harga == null)
						this.addInvalidMessage(ctx, "harga", Labels.getLabel("common.validator.empty"));

					String spkno = (String) ctx.getProperties("spkno")[0].getValue();
					if (spkno == null || "".equals(spkno.trim()))
						this.addInvalidMessage(ctx, "spkno", Labels.getLabel("common.validator.empty"));

					Date spkdate = (Date) ctx.getProperties("spkdate")[0].getValue();
					if (spkdate == null)
						this.addInvalidMessage(ctx, "spkdate", Labels.getLabel("common.validator.empty"));

					if (arg.equals(AppUtils.PRODUCTGROUP_PINPAD)) {
						if (filename == null || "".equals(filename.trim()))
							this.addInvalidMessage(ctx, "filename", Labels.getLabel("common.validator.empty"));
					} else {
						if (arg.equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
							if (prefix == null || "".equals(prefix))
								this.addInvalidMessage(ctx, "startno", Labels.getLabel("common.validator.empty"));
						}

						if (startno == null || startno == 0)
							this.addInvalidMessage(ctx, "startno", Labels.getLabel("common.validator.empty"));

						if (totaldata == null || totaldata == 0)
							this.addInvalidMessage(ctx, "itemqty", Labels.getLabel("common.validator.empty"));

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
	}

	public Tincoming getObjForm() {
		return objForm;
	}

	public void setObjForm(Tincoming objForm) {
		this.objForm = objForm;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getEndno() {
		return endno;
	}

	public void setEndno(String endno) {
		this.endno = endno;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getStartno() {
		return startno;
	}

	public void setStartno(Integer startno) {
		this.startno = startno;
	}

}

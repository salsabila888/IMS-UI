/**
 * 
 */
package com.sdd.caption.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.ImmutableFields;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.MmenuDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.dao.TpaketDAO;
import com.sdd.caption.dao.TpaketdataDAO;
import com.sdd.caption.dao.TpinpaditemDAO;
import com.sdd.caption.domain.Mmenu;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.domain.Tpaket;
import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.domain.Tpinpaditem;
import com.sdd.caption.handler.FlowHandler;
import com.sdd.caption.handler.NotifHandler;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PinpadScanEntryVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private TorderDAO oDao = new TorderDAO();
	private TorderitemDAO toiDao = new TorderitemDAO();
	private TpinpaditemDAO tpiDao = new TpinpaditemDAO();

	private Session session;
	private Transaction transaction;

	private Torder obj;
	private Tpinpaditem objItem;
	private Torderitem objForm;
	private int outstanding;
	private String serialno;
	private String serialnoManual;
	private int totalfail;
	private int totaldata;
	private int inserted;
	private String filename;
	private Media media;

	private List<String> listData = new ArrayList<>();
	private List<Torderitem> snList = new ArrayList<>();
	private List<Torderitem> listFail = new ArrayList<>();

	@Wire
	private Window winSerial;
	@Wire
	private Textbox tbSerial;
	@Wire
	private Button btnRegisterManual, btnRegister;
	@Wire
	private Grid grid;

	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Torder obj)
			throws Exception {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");
		this.obj = obj;
		doReset();
		outstanding = obj.getTotalproses();
		grid.setRowRenderer(new RowRenderer<Torderitem>() {

			@Override
			public void render(final Row row, final Torderitem data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				row.getChildren().add(new Label(data.getTpinpaditem().getItemno()));
				row.getChildren().add(new Label(data.getTid()));
				row.getChildren().add(new Label(data.getMid()));
				row.getChildren().add(new Label(AppData.getPinpadtypeLabel(data.getPinpadtype())));
				row.getChildren().add(new Label(data.getPinpadmemo()));
				Button btn = new Button("Cancel");
				btn.setAutodisable("self");
				btn.setSclass("btn btn-danger btn-sm");
				btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
								Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {

									@Override
									public void onEvent(Event event) throws Exception {
										snList.remove(data);
										listData.remove(data.getTpinpaditem().getItemno().trim());
										refresh();
										BindUtils.postNotifyChange(null, null, PinpadScanEntryVm.this, "outstanding");
									}
								});
					}
				});

				Div div = new Div();
				div.appendChild(btn);
				row.getChildren().add(div);
			}
		});
	}

	@NotifyChange("*")
	public void refresh() {
		grid.setModel(new ListModelList<Torderitem>(snList));
		outstanding = obj.getTotalproses() - snList.size();
	}

	@NotifyChange("filename")
	@Command
	public void doBrowse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		media = event.getMedia();
		filename = media.getName();
		if (media != null)
			if (media.getFormat().contains("xls")) {
				btnRegister.setDisabled(false);
			} else {
				Messagebox.show("Format harus berupa xls/xlsx", "Exclamation", Messagebox.OK, Messagebox.EXCLAMATION);
				btnRegister.setDisabled(true);
			}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		filename = null;
		totalfail = 0;
		inserted = 0;
		totaldata = 0;
		serialno = "";
		serialnoManual = "";
		objItem = null;
		objForm = new Torderitem();
		tbSerial.setFocus(true);
		btnRegisterManual.setDisabled(true);
		btnRegister.setDisabled(true);
	}

	@NotifyChange("*")
	@Command
	public void doFind() {
		try {
			objItem = tpiDao.findById(serialnoManual.trim());
			if (objItem != null) {
				if (objItem.getStatus().equals(AppUtils.STATUS_SERIALNO_OUTINVENTORY)) {
					objForm.setPinpadtype(AppUtils.PINPADTYPE_CS);
					btnRegisterManual.setDisabled(false);
				} else if (objItem.getStatus().equals(AppUtils.STATUS_SERIALNO_ENTRY)) {
					objItem = null;
					btnRegisterManual.setDisabled(true);
					Messagebox.show("Status pinpad belum diverifikasi oleh inventori", "Info", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					objItem = null;
					btnRegisterManual.setDisabled(true);
					Messagebox.show("Status pinpad sudah diinject", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				btnRegisterManual.setDisabled(true);
				Messagebox.show("Data tidak ditemukan", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void doRegisterUpload() {
		try {
			if (media != null) {
				totalfail = 0;
				inserted = 0;
				totaldata = 0;

				Workbook wb = null;
				if (filename.trim().toLowerCase().endsWith("xlsx")) {
					wb = new XSSFWorkbook(media.getStreamData());
				} else if (filename.trim().toLowerCase().endsWith("xls")) {
					wb = new HSSFWorkbook(media.getStreamData());
				}
				Sheet sheet = wb.getSheetAt(0);
				for (org.apache.poi.ss.usermodel.Row row : sheet) {
					try {
						if (row.getRowNum() < 1) {
							continue;
						}

						for (int count = 0; count <= row.getLastCellNum(); count++) {
							Cell cell = row.getCell(count, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
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
							case 2:
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									cell.setCellType(Cell.CELL_TYPE_STRING);
									objForm.setTid(cell.getStringCellValue());
								} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
									objForm.setTid(cell.getStringCellValue());
								}
								break;
							case 3:
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									cell.setCellType(Cell.CELL_TYPE_STRING);
									objForm.setPinpadtype(cell.getStringCellValue());
								} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
									objForm.setPinpadtype(cell.getStringCellValue());
								}
								break;
							case 4:
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									cell.setCellType(Cell.CELL_TYPE_STRING);
									objForm.setPinpadmemo(cell.getStringCellValue());
								} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
									objForm.setPinpadmemo(cell.getStringCellValue());
								}
								break;
							case 5:
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									cell.setCellType(Cell.CELL_TYPE_STRING);
									objForm.setMid(cell.getStringCellValue());
								} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
									objForm.setMid(cell.getStringCellValue());
								}
								break;
							}
						}

						if (outstanding > 0) {
							if (!listData.contains(serialno.trim())) {
								objItem = tpiDao.findById(serialno.trim());
								if (objItem != null) {
									if (objItem.getStatus().equals(AppUtils.STATUS_SERIALNO_OUTINVENTORY)) {
										if (objForm.getPinpadtype()
												.equals(AppData.getPinpadtypeLabel(AppUtils.PINPADTYPE_CS))) {
											objForm.setPinpadtype(AppUtils.PINPADTYPE_CS);
										} else {
											objForm.setPinpadtype(AppUtils.PINPADTYPE_TELLER);
										}
										objForm.setTpinpaditem(objItem);
										snList.add(objForm);
										listData.add(serialno);
										serialno = "";
										objForm = new Torderitem();
										inserted++;
										outstanding = obj.getTotalproses() - snList.size();
									} else if (objItem.getStatus().equals(AppUtils.STATUS_SERIALNO_ENTRY)) {
										objForm.setTid(serialno.trim());
										objForm.setPinpadmemo("Status pinpad belum diverifikasi oleh inventori");
										listFail.add(objForm);
										objForm = new Torderitem();
									} else {
										objForm.setTid(serialno.trim());
										objForm.setPinpadmemo("Status pinpad sudah diinject");
										listFail.add(objForm);
										objForm = new Torderitem();
									}
								} else {
									objForm.setTid(serialno.trim());
									objForm.setPinpadmemo("Data Tidak ditemukan");
									listFail.add(objForm);
									objForm = new Torderitem();
								}
							} else {
								objForm.setTid(serialno.trim());
								objForm.setPinpadmemo("Duplicate Data");
								listFail.add(objForm);
								objForm = new Torderitem();
							}
						} else {
							objForm.setTid(serialno.trim());
							objForm.setPinpadmemo("Jumlah data sudah memenuhi jumlah order");
							listFail.add(objForm);
							objForm = new Torderitem();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				refresh();
				btnRegister.setDisabled(true);
				totalfail = listFail.size();
				totaldata = inserted + totalfail;
			} else {
				Messagebox.show("Silahkan upload file nomor serial pinpad", "Exclamation", Messagebox.OK,
						Messagebox.EXCLAMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	@Command
	public void doRegisterManual() {
		try {
			if (outstanding > 0) {
				if (!listData.contains(serialnoManual.trim())) {
					try {
						objForm.setTpinpaditem(objItem);
						snList.add(objForm);
						listData.add(objItem.getItemno());
						refresh();
						serialnoManual = "";
						objForm = new Torderitem();
						btnRegisterManual.setDisabled(true);
					} catch (Exception e) {
						Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
						e.printStackTrace();
					}
				} else {
					Messagebox.show("Duplicate Data", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("Jumlah data sudah memenuhi jumlah order", "Info", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doSave() {
		if (outstanding == 0) {
			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			try {
				obj.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				oDao.save(session, obj);

				for (Torderitem data : snList) {
					data.getTpinpaditem().setStatus(AppUtils.STATUS_SERIALNO_OUTPRODUKSI);
					new TpinpaditemDAO().save(session, data.getTpinpaditem());

					data.setTorder(obj);
					data.setProductgroup(obj.getProductgroup());
					toiDao.save(session, data);
				}

				Tpaket paket = new Tpaket();
				paket.setMproduct(obj.getMproduct());
				paket.setOrderdate(obj.getInserttime());
				paket.setPaketid(new TcounterengineDAO().generateYearMonthCounter(AppUtils.CE_PAKET));
				paket.setProcessedby(oUser.getUserid());
				paket.setProcesstime(new Date());
				paket.setProductgroup(obj.getMproduct().getProductgroup());
				paket.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				paket.setTotaldata(obj.getTotalproses());
				paket.setTotaldone(1);
				paket.setTorder(obj);
				new TpaketDAO().save(session, paket);

				Tpaketdata paketdata = new Tpaketdata();
				paketdata.setNopaket(new TcounterengineDAO().generateNopaket());
				paketdata.setIsdlv("N");
				paketdata.setMbranch(obj.getMbranch());
				paketdata.setOrderdate(paket.getOrderdate());
				paketdata.setPaketfinishby(oUser.getUserid());
				paketdata.setPaketfinishtime(new Date());
				paketdata.setPaketstartby(oUser.getUserid());
				paketdata.setPaketstarttime(new Date());
				paketdata.setProductgroup(paket.getProductgroup());
				paketdata.setQuantity(obj.getTotalproses());
				paketdata.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				paketdata.setTpaket(paket);
				new TpaketdataDAO().save(session, paketdata);

				Mmenu menu = new MmenuDAO().findByFilter("menupath = '/view/delivery/deliveryjob.zul'");
				if (menu != null) {
					NotifHandler.doNotif(session, menu, "Paket PINPAD yang harus diOrder Delivery");
				}

				// FlowHandler.doFlow(session, null, obj, AppUtils.PROSES_ORDER, obj.getMemo(),
				// oUser.getUserid());

				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			Clients.showNotification("Proses verified data order berhasil", "info", null, "middle_center", 3000);
			Event closeEvent = new Event("onClose", winSerial, new Boolean(true));
			Events.postEvent(closeEvent);
		} else {
			Messagebox.show("Masih ada data outstanding. Silahkan selesaikan proses verifikasinya", "Info",
					Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				if (serialnoManual == null || serialnoManual.trim().length() == 0)
					this.addInvalidMessage(ctx, "serialno", Labels.getLabel("common.validator.empty"));

				String tid = (String) ctx.getProperties("tid")[0].getValue();
				if (tid == null || "".equals(tid.trim()))
					this.addInvalidMessage(ctx, "tid", Labels.getLabel("common.validator.empty"));
				String mid = (String) ctx.getProperties("mid")[0].getValue();
				if (mid == null || "".equals(mid.trim()))
					this.addInvalidMessage(ctx, "mid", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	@Command
	@NotifyChange("*")
	public void doView() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", obj);
		map.put("listFail", listFail);
		map.put("isFail", "Y");
		Window win = (Window) Executions.createComponents("/view/pinpad/pinpadscanfail.zul", null, map);
		win.setWidth("80%");
		win.setClosable(true);
		win.doModal();
	}

	public Torder getObj() {
		return obj;
	}

	public void setObj(Torder obj) {
		this.obj = obj;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public int getTotalfail() {
		return totalfail;
	}

	public void setTotalfail(int totalfail) {
		this.totalfail = totalfail;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}

	public int getInserted() {
		return inserted;
	}

	public void setInserted(int inserted) {
		this.inserted = inserted;
	}

	public String getSerialnoManual() {
		return serialnoManual;
	}

	public void setSerialnoManual(String serialnoManual) {
		this.serialnoManual = serialnoManual;
	}

	@ImmutableFields
	public Torderitem getObjForm() {
		return objForm;
	}

	public void setObjForm(Torderitem objForm) {
		this.objForm = objForm;
	}
}
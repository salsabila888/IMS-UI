/**
 * 
 */
package com.sdd.caption.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TbranchstockitemDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.dao.TswitchDAO;
import com.sdd.caption.dao.TswitchitemDAO;
import com.sdd.caption.dao.TswitchmemoDAO;
import com.sdd.caption.dao.TpaketDAO;
import com.sdd.caption.dao.TpaketdataDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.domain.Tswitch;
import com.sdd.caption.domain.Tswitchitem;
import com.sdd.caption.domain.Tswitchmemo;
import com.sdd.caption.domain.Tpaket;
import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.domain.Tsecuritiesitem;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class DocumentSwitchingVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private TswitchDAO tswitchDao = new TswitchDAO();
	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();

	private Session session;
	private Transaction transaction;

	private Tswitch obj;
	private Tsecuritiesitem objForm;
	private int outstanding;
	private String itemno;
	private String itemnoend;
	private String memo;
	private String prefix;
	private Integer branchlevel;

	private List<Tbranchstockitem> branchstockitem = new ArrayList<>();
	private List<String> listData = new ArrayList<>();

	private Tr tr;

	@Wire
	private Window winItem;
	@Wire
	private Textbox tbItem;
	@Wire
	private Button btnRegister, btnAdd;
	@Wire
	private Button btnSave;
	@Wire
	private Grid grid;
	@Wire
	private Button btnRegisterBatch;
	@Wire
	private Checkbox chkbox;
	@Wire
	private Tr trmemo;
	@Wire
	private Table table;

	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tswitch obj)
			throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		branchlevel = oUser.getMbranch().getBranchlevel();
		this.obj = obj;

		grid.setRowRenderer(new RowRenderer<String>() {

			@Override
			public void render(final Row row, final String data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				row.getChildren().add(new Label(data));
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
										listData.remove(data);
										refresh();
										BindUtils.postNotifyChange(null, null, DocumentSwitchingVm.this, "outstanding");
									}
								});
					}
				});

				Div div = new Div();
				div.appendChild(btn);
				row.getChildren().add(div);
			}
		});

		doReset();
	}

	@Command
	@NotifyChange("*")
	public void doChecked() {
		if (chkbox.isChecked())
			trmemo.setVisible(true);
		else
			trmemo.setVisible(false);
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		itemno = "";
		prefix = "";
		objForm = null;
		tbItem.setFocus(true);
		branchstockitem = new ArrayList<Tbranchstockitem>();
		listData = new ArrayList<String>();
		refresh();
	}

	@NotifyChange("*")
	public void refresh() {
		grid.setModel(new ListModelList<String>(listData));
		outstanding = obj.getItemqty() - listData.size();
	}

	@NotifyChange("*")
	@Command
	public void doRegister() {
		try {
			outstanding = obj.getItemqty();
			if (outstanding > 0) {
				Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk()
						+ " and mproductfk = " + obj.getMproduct().getMproductpk() + " and outlet = '"
						+ obj.getOutletpool() + "'" + "");

				if (objStock != null) {
					branchstockitem = new TbranchstockitemDAO()
							.listNativeByFilter(
									"tbranchstockfk = " + objStock.getTbranchstockpk() + " and numerator between "
											+ itemno.trim() + " and " + itemnoend.trim() + " and status = '"
											+ AppUtils.STATUS_SERIALNO_ENTRY + "'",
									"numerator limit " + obj.getItemqty());
					if (branchstockitem.size() > 0) {
						for (Tbranchstockitem data : branchstockitem) {
							if (!listData.contains(data.getItemno().trim())) {
								if (data.getItemno()
										.equals(prefix.trim().toUpperCase() + data.getNumerator().toString())) {
									listData.add(data.getItemno().trim());
								}
							}
						}
					} else {
						Messagebox.show("Data tidak ditemukan.", "Info", Messagebox.OK,
								Messagebox.INFORMATION);
					}
				} else {
					Messagebox.show("Cabang ini belum memiliki stok.", "Info", Messagebox.OK, Messagebox.INFORMATION);
				}

				refresh();
			} else {
				Messagebox.show("Jumlah data sudah memenuhi jumlah order.", "Info", Messagebox.OK,
						Messagebox.INFORMATION);
			}
			tbItem.setFocus(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doAdd() {
		try {

			if (outstanding <= 0) {
				Messagebox.show(
						"Tidak bisa menambah data karna jumlah data yang dimasukan sudah setara atau melebihi outstanding.",
						"Info", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				btnAdd.setDisabled(true);

				tr = new Tr();
				Td td = new Td();
				td.setColspan(2);
				Textbox txtAwal = new Textbox();
				txtAwal.setPlaceholder("Entri Nomor Item Awal");
				Label label = new Label(" ");
				Textbox txtAkhir = new Textbox();
				txtAkhir.setPlaceholder("Entri Nomor Item Akhir");
				Label label2 = new Label(" ");
				Button btn = new Button("Check Data");
				btn.setAutodisable("self");
				btn.setSclass("btn btn-info btn-sm");
				btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (txtAwal == null || txtAwal.getValue().trim().length() == 0)
							Messagebox.show("Silahkan isi data nomor item awal dengan data numerik", "Info",
									Messagebox.OK, Messagebox.INFORMATION);
						else if (txtAkhir == null || txtAkhir.getValue().trim().length() == 0)
							Messagebox.show("Silahkan isi data nomor item akhir dengan data numerik", "Info",
									Messagebox.OK, Messagebox.INFORMATION);
						else {
							Integer inserted = 0;

							Tbranchstock objStock = tbranchstockDao
									.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk()
											+ " and mproductfk = " + obj.getMproduct().getMproductpk()
											+ " and outlet = '" + obj.getOutletpool() + "'" + "");

							if (objStock != null) {
								branchstockitem = new TbranchstockitemDAO().listNativeByFilter(
										"tbranchstockfk = " + objStock.getTbranchstockpk() + " and numerator between "
												+ txtAwal.getValue().trim() + " and " + txtAkhir.getValue().trim()
												+ " and status = '" + AppUtils.STATUS_SERIALNO_ENTRY + "'",
										"numerator limit " + obj.getItemqty());
								for (Tbranchstockitem data : branchstockitem) {
									if (!listData.contains(data.getItemno().trim())) {
										if (data.getItemno()
												.equals(prefix.trim().toUpperCase() + data.getNumerator().toString())) {
											listData.add(data.getItemno().trim());
											inserted++;
										}
									}
								}
							}

							if (inserted > 0) {
								txtAwal.setReadonly(true);
								txtAkhir.setReadonly(true);
								btn.setDisabled(true);
								btnAdd.setDisabled(false);

								refresh();
								BindUtils.postNotifyChange(null, null, DocumentSwitchingVm.this, "outstanding");
							} else {
								Messagebox.show("Data yang dicari tidak ditemukan atau sudah masuk kedalam daftar.",
										"Info", Messagebox.OK, Messagebox.INFORMATION);
							}
						}
					}
				});

				td.appendChild(txtAwal);
				td.appendChild(label);
				td.appendChild(txtAkhir);
				td.appendChild(label2);
				td.appendChild(btn);

				tr.appendChild(td);
				table.appendChild(tr);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void doSave() {
		if (outstanding == 0) {
			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			try {
				obj.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				tswitchDao.save(session, obj);

				obj.getTorder().setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				obj.getTorder().setTotalproses(listData.size());
				new TorderDAO().save(session, obj.getTorder());

				if (branchstockitem.size() > 0) {
					for (Tbranchstockitem data : branchstockitem) {
						data.setStatus(AppUtils.STATUS_SERIALNO_OUTINVENTORY);
						new TbranchstockitemDAO().save(session, data);

						Torderitem tori = new Torderitem();
						tori.setProductgroup(AppUtils.PRODUCTGROUP_DOCUMENT);
						tori.setItemno(data.getItemno());
						tori.setNumerator(data.getNumerator());
						tori.setTorder(obj.getTorder());
						new TorderitemDAO().save(session, tori);

						Tswitchitem objItem = new Tswitchitem();
						objItem.setItemno(data.getItemno());
						objItem.setTswitch(obj);
						new TswitchitemDAO().save(session, objItem);
					}
				}

				Tpaket paket = new Tpaket();
				paket.setMproduct(obj.getMproduct());
				paket.setOrderdate(obj.getTorder().getOrderdate());
				paket.setPaketid(new TcounterengineDAO().generateYearMonthCounter(AppUtils.CE_PAKET));
				paket.setProcessedby(oUser.getUserid());
				paket.setProcesstime(new Date());
				paket.setProductgroup(obj.getMproduct().getProductgroup());
				paket.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				paket.setTotaldata(obj.getTorder().getTotalproses());
				paket.setTotaldone(1);
				paket.setTorder(obj.getTorder());
				paket.setTswitch(obj);
				new TpaketDAO().save(session, paket);

				Tpaketdata paketdata = new Tpaketdata();
				paketdata.setNopaket(new TcounterengineDAO().generateNopaket());
				paketdata.setIsdlv("N");
				paketdata.setMbranch(obj.getTorder().getMbranch());
				paketdata.setOrderdate(paket.getOrderdate());
				paketdata.setPaketfinishby(oUser.getUserid());
				paketdata.setPaketfinishtime(new Date());
				paketdata.setPaketstartby(oUser.getUserid());
				paketdata.setPaketstarttime(new Date());
				paketdata.setProductgroup(paket.getProductgroup());
				paketdata.setQuantity(obj.getTorder().getTotalproses());
				paketdata.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
				paketdata.setTpaket(paket);
				new TpaketdataDAO().save(session, paketdata);

				if (branchlevel == 2) {
					Tbranchstock objStock = tbranchstockDao
							.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk() + " and mproductfk = "
									+ obj.getTorder().getMproduct().getMproductpk());
					if (objStock != null) {
						objStock.setStockactivated(objStock.getStockactivated() + obj.getItemqty());
						objStock.setStockcabang(objStock.getStockcabang() - obj.getItemqty());
//						objStock.setStockreserved(objStock.getStockreserved() - obj.getItemqty());
						tbranchstockDao.save(session, objStock);
					}
				} else if (branchlevel == 3) {
					Tbranchstock objStock = tbranchstockDao
							.findByFilter("mbranchfk = " + oUser.getMbranch().getMbranchpk() + " and mproductfk = "
									+ obj.getTorder().getMproduct().getMproductpk() + " and outlet = '00'");
					if (objStock != null) {
						objStock.setStockactivated(objStock.getStockactivated() + obj.getItemqty());
						objStock.setStockcabang(objStock.getStockcabang() - obj.getItemqty());
//						objStock.setStockreserved(objStock.getStockreserved() - obj.getItemqty());
						tbranchstockDao.save(session, objStock);
					}
				}

				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			Clients.showNotification("Proses verified data order berhasil", "info", null, "middle_center", 3000);
			Event closeEvent = new Event("onClose", winItem, new Boolean(true));
			Events.postEvent(closeEvent);
		} else {
			if (listData.size() > 0) {
				Messagebox.show(
						"Jumlah yang terpenuhi adalah " + listData.size()
								+ ", apakah anda yakin ingin melanjutkan proses?",
						"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onOK")) {
									if (memo != null && memo.trim().length() > 0) {
										session = StoreHibernateUtil.openSession();
										transaction = session.beginTransaction();
										try {
											obj.setItemqty(listData.size());
											obj.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
											tswitchDao.save(session, obj);

											obj.getTorder().setTotalproses(listData.size());
											obj.getTorder().setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
											new TorderDAO().save(session, obj.getTorder());

											if (branchstockitem.size() > 0) {
												for (Tbranchstockitem data : branchstockitem) {
													data.setStatus(AppUtils.STATUS_SERIALNO_OUTINVENTORY);
													new TbranchstockitemDAO().save(session, data);

													Torderitem tori = new Torderitem();
													tori.setProductgroup(AppUtils.PRODUCTGROUP_DOCUMENT);
													tori.setItemno(data.getItemno());
													tori.setNumerator(data.getNumerator());
													tori.setTorder(obj.getTorder());
													new TorderitemDAO().save(session, tori);

													Tswitchitem objItem = new Tswitchitem();
													objItem.setItemno(data.getItemno());
													objItem.setTswitch(obj);
													new TswitchitemDAO().save(session, objItem);
												}
											}

											Tswitchmemo objMemo = new Tswitchmemo();
											objMemo.setMemo(memo);
											objMemo.setMemoby(oUser.getUsername());
											objMemo.setMemotime(new Date());
											objMemo.setTswitch(obj);
											new TswitchmemoDAO().save(session, objMemo);

											Tpaket paket = new Tpaket();
											paket.setMproduct(obj.getMproduct());
											paket.setOrderdate(obj.getTorder().getOrderdate());
											paket.setPaketid(new TcounterengineDAO()
													.generateYearMonthCounter(AppUtils.CE_PAKET));
											paket.setProcessedby(oUser.getUserid());
											paket.setProcesstime(new Date());
											paket.setProductgroup(obj.getMproduct().getProductgroup());
											paket.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
											paket.setTotaldata(obj.getItemqty());
											paket.setTotaldone(obj.getItemqty());
											paket.setTorder(obj.getTorder());
											new TpaketDAO().save(session, paket);

											Tpaketdata paketdata = new Tpaketdata();
											paketdata.setNopaket(new TcounterengineDAO().generateNopaket());
											paketdata.setIsdlv("N");
											paketdata.setMbranch(obj.getTorder().getMbranch());
											paketdata.setOrderdate(paket.getOrderdate());
											paketdata.setPaketfinishby(oUser.getUserid());
											paketdata.setPaketfinishtime(new Date());
											paketdata.setPaketstartby(oUser.getUserid());
											paketdata.setPaketstarttime(new Date());
											paketdata.setProductgroup(paket.getProductgroup());
											paketdata.setQuantity(obj.getItemqty());
											paketdata.setStatus(AppUtils.STATUS_DELIVERY_PAKETDONE);
											paketdata.setTpaket(paket);
											new TpaketdataDAO().save(session, paketdata);

											if (branchlevel == 2) {
												Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = "
														+ oUser.getMbranch().getMbranchpk() + " and mproductfk = "
														+ obj.getTorder().getMproduct().getMproductpk());
												if (objStock != null) {
													objStock.setStockcabang(
															objStock.getStockcabang() - obj.getItemqty());
//													objStock.setStockreserved(
//															objStock.getStockreserved() - obj.getTorder().getItemqty());
													tbranchstockDao.save(session, objStock);
												}
											} else if (branchlevel == 3) {
												Tbranchstock objStock = tbranchstockDao.findByFilter("mbranchfk = "
														+ oUser.getMbranch().getMbranchpk() + " and mproductfk = "
														+ obj.getTorder().getMproduct().getMproductpk()
														+ " and outlet = '00'");
												if (objStock != null) {
													objStock.setStockcabang(
															objStock.getStockcabang() - obj.getItemqty());
//													objStock.setStockreserved(
//															objStock.getStockreserved() - obj.getTorder().getItemqty());
													tbranchstockDao.save(session, objStock);
												}
											}

											transaction.commit();
										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											session.close();
										}

										Clients.showNotification("Proses verified data order berhasil", "info", null,
												"middle_center", 3000);
										Event closeEvent = new Event("onClose", winItem, new Boolean(true));
										Events.postEvent(closeEvent);
									} else {
										Messagebox.show("Alasan pemenuhan harus diisi terlebih dahulu.", "Info",
												Messagebox.OK, Messagebox.INFORMATION);
									}
								}
							}
						});
			} else {
				Messagebox.show("Belum ada data.", "Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				if (itemno == null || itemno.trim().length() == 0)
					this.addInvalidMessage(ctx, "itemno", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	@ImmutableFields
	public Tsecuritiesitem getObjForm() {
		return objForm;
	}

	public void setObjForm(Tsecuritiesitem objForm) {
		this.objForm = objForm;
	}

	public Tswitch getObj() {
		return obj;
	}

	public void setObj(Tswitch obj) {
		this.obj = obj;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getItemnoend() {
		return itemnoend;
	}

	public void setItemnoend(String itemnoend) {
		this.itemnoend = itemnoend;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}

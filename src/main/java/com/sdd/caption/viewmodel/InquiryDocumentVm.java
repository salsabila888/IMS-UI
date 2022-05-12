package com.sdd.caption.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TbranchstockitemDAO;
import com.sdd.caption.dao.TsecuritiesitemDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.caption.domain.Tsecuritiesitem;
import com.sdd.caption.pojo.InquiryOrder;
import com.sdd.caption.utils.AppData;

public class InquiryDocumentVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private int pageTotalSize;
	private String itemno;
	private Integer branchlevel;

	List<InquiryOrder> objList = new ArrayList<InquiryOrder>();

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ParseException {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		if(oUser != null)
			branchlevel = oUser.getMbranch().getBranchlevel();

		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<InquiryOrder>() {

				@Override
				public void render(Row row, final InquiryOrder data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getItemno()));
					row.getChildren().add(new Label(data.getMbranch().getBranchname()));
					row.getChildren().add(new Label(data.getMproducttype().getProducttype()));
					row.getChildren().add(new Label(AppData.getStatusLabel(data.getStatus())));

					Button btn = new Button("Lihat Detail");
					btn.setAutodisable("self");
					btn.setSclass("btn btn-default btn-sm");
					btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<>();
							Window win = new Window();
							map.put("obj", data);
							win = (Window) Executions.createComponents("/view/inquiry/inquirydatadetail.zul", null, map);
							win.setWidth("50%");
							win.setClosable(true);
							win.doModal();
						}
					});

					Div div = new Div();
					div.appendChild(btn);
					row.getChildren().add(div);
				}
			});
		}
		doReset();
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		if (oUser != null) {
			if (itemno != null && itemno.trim().length() > 0) {
				try {
					objList = new ArrayList<InquiryOrder>();
					if (branchlevel == 1) {
						List<Tsecuritiesitem> itemList = new TsecuritiesitemDAO()
								.listByFilter("itemno = '" + itemno.trim() + "'", "tsecuritiesitempk");
						if (itemList.size() > 0) {
							for (Tsecuritiesitem data : itemList) {
								InquiryOrder obj = new InquiryOrder();
								obj.setItemno(data.getItemno());
								obj.setMbranch(data.getTincoming().getMbranch());
								obj.setMproducttype(data.getTincoming().getMproducttype());
								obj.setStatus(data.getStatus());

								objList.add(obj);
							}

							pageTotalSize = objList.size();
							grid.setModel(new ListModelList<InquiryOrder>(objList));
						} else {
							Messagebox.show("Data tidak ditemukan");
						}
					} else if (branchlevel == 2) {
						List<Tbranchstockitem> itemList = new TbranchstockitemDAO().listByFilter("itemno = '"
								+ itemno.trim() + "' and mregionfk = " + oUser.getMbranch().getMregion().getMregionpk(),
								"tbranchstockitempk");
						if (itemList.size() > 0) {
							for (Tbranchstockitem data : itemList) {
								InquiryOrder obj = new InquiryOrder();
								obj.setItemno(data.getItemno());
								obj.setMbranch(data.getTbranchstock().getMbranch());
								obj.setMproducttype(data.getTbranchstock().getMproduct().getMproducttype());
								obj.setStatus(data.getStatus());

								objList.add(obj);
							}

							pageTotalSize = objList.size();
							grid.setModel(new ListModelList<InquiryOrder>(objList));
						} else {
							Messagebox.show("Data tidak ditemukan");
						}
					} else {
						List<Tbranchstockitem> itemList = new TbranchstockitemDAO().listByFilter(
								"itemno = '" + itemno.trim() + "' and mbranchfk = " + oUser.getMbranch().getMbranchpk(),
								"tbranchstockitempk");
						if (itemList.size() > 0) {
							for (Tbranchstockitem data : itemList) {
								InquiryOrder obj = new InquiryOrder();
								obj.setItemno(data.getItemno());
								obj.setMbranch(data.getTbranchstock().getMbranch());
								obj.setMproducttype(data.getTbranchstock().getMproduct().getMproducttype());
								obj.setStatus(data.getStatus());

								objList.add(obj);
							}

							pageTotalSize = objList.size();
							grid.setModel(new ListModelList<InquiryOrder>(objList));
						} else {
							Messagebox.show("Data tidak ditemukan");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * if(oUser != null) { if(oUser.getMbranch() != null) { filter +=
		 * "and mbranchfk = " + oUser.getMbranch().getMbranchpk(); } }
		 */

	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		itemno = null;
		objList = new ArrayList<>();
		grid.setModel(new ListModelList<>(objList));
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
}

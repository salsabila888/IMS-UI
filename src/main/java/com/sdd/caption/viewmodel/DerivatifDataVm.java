package com.sdd.caption.viewmodel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.domain.Tderivatif;
import com.sdd.caption.domain.Tderivatifdata;
import com.sdd.caption.domain.Tderivatifproduct;
import com.sdd.caption.model.TderivatifdataListModel;
import com.sdd.utils.SysUtils;

public class DerivatifDataVm {

	private TderivatifdataListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;

	private Tderivatif obj;
	private Tderivatifproduct objproduct;
	private String cardno;

	private SimpleDateFormat datelocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	@Wire
	private Window winOrderdata;
	@Wire
	private Groupbox gbBatchInfo;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Object obj,
			@ExecutionArgParam("isDelivery") String isDelivery) throws ParseException {
		Selectors.wireComponents(view, this, false);
		if (obj instanceof Tderivatif) {
			this.obj = (Tderivatif) obj;
		} else if (obj instanceof Tderivatifproduct) {
			objproduct = (Tderivatifproduct) obj;
			this.obj = objproduct.getTderivatif();
		}

		doReset();
		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);

			}
		});

		grid.setRowRenderer(new RowRenderer<Tderivatifdata>() {

			@Override
			public void render(Row row, final Tderivatifdata data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));
				row.getChildren().add(new Label(data.getCardno()));
				row.getChildren().add(new Label(
						data.getTembossdata().getNameonid() == null ? "" : data.getTembossdata().getNameonid()));
				row.getChildren().add(
						new Label(data.getOrderdate() == null ? "" : datelocalFormatter.format(data.getOrderdate())));
				row.getChildren()
						.add(new Label(data.getTderivatifproduct() != null
								? data.getTderivatifproduct().getMproduct().getProductcode()
								: ""));
				row.getChildren()
						.add(new Label(data.getTderivatifproduct() != null
								? data.getTderivatifproduct().getMproduct().getProductname()
								: ""));
				Button btnDetail = new Button("Detail");
				btnDetail.setAutodisable("self");
				btnDetail.setClass("btn btn-default btn-sm");
				btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> map = new HashMap<>();
						map.put("drv", data);
						map.put("isDrvCard", "Y");

						Window win = (Window) Executions.createComponents("/view/opr/inquirydetail.zul", null, map);
						win.setWidth("70%");
						win.setClosable(true);
						win.doModal();
					}
				});
				row.getChildren().add(btnDetail);
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tderivatifdata.orderdate, tderivatifdata.status";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TderivatifdataListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		filter = "";
		if (objproduct != null) {
			filter = "tderivatifproductfk = " + objproduct.getTderivatifproductpk();
		} else {
			filter = "tderivatiffk = " + obj.getTderivatifpk();
		}
		if (cardno != null && cardno.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "tderivatifdata.cardno like '%" + cardno.trim().toUpperCase() + "%'";
		}

		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}

	@Command
	public void doClose() {
		Event closeEvent = new Event("onClose", winOrderdata, null);
		Events.postEvent(closeEvent);
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		cardno = "";
		doSearch();
	}

	public Tderivatif getObj() {
		return obj;
	}

	public void setObj(Tderivatif obj) {
		this.obj = obj;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

}

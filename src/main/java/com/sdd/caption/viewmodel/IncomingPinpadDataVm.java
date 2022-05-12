package com.sdd.caption.viewmodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.domain.Tincoming;
import com.sdd.caption.domain.Tpinpaditem;
import com.sdd.caption.domain.Ttokenitem;
import com.sdd.caption.model.TpinpaditemListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.utils.SysUtils;

public class IncomingPinpadDataVm {
	
	private Session zkSession = Sessions.getCurrent();

	private TpinpaditemListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String orderby;
	
	private Tincoming obj;
	private Ttokenitem objProduct;
	private String productname;
	private Integer itemqty;

	private Boolean isSaved;
	private String productgroup;
	private String arg;
	
	private SimpleDateFormat datelocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimelocalFormatter = new SimpleDateFormat("dd MMMMM yyyy HH:mm");

	@Wire
	private Window winIncomingTokenItem;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Label lbTitle;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tincoming tincoming)
			throws ParseException {
		
		Selectors.wireComponents(view, this, false);
		obj = tincoming;
		
		paging.addEventListener("onPaging", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});
		
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Tpinpaditem>() {

				@Override
				public void render(Row row, Tpinpaditem data, int index) throws Exception {
					row.getChildren().add(new Label(
							String.valueOf((SysUtils.PAGESIZE * pageStartNumber)
									+ index + 1)));
					row.getChildren().add(new Label(data.getItemno() != null ? data.getItemno() : "-"));
					row.getChildren().add(new Label(data.getStatus() != null ? AppData.getStatusLabel(data.getStatus()) : "-"));
				}
			});
		}
		
		doReset();
	}
	
	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		
		filter = "tincomingfk = " + obj.getTincomingpk();
			needsPageUpdate = true;
			paging.setActivePage(0);
			pageStartNumber = 0;
			refreshModel(pageStartNumber);
	}
	
	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tincomingpk";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TpinpaditemListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}
	
	@Command
	public void doClose() {
		Event closeEvent = new Event("onClose", winIncomingTokenItem, isSaved);
		Events.postEvent(closeEvent);
	}
	
	@Command
	@NotifyChange("*")
	public void doReset() throws ParseException { 
		productgroup = AppData.getProductgroupLabel(obj.getProductgroup());
		doSearch();
	}
	
	public int getPageStartNumber() {
		return pageStartNumber;
	}

	public void setPageStartNumber(int pageStartNumber) {
		this.pageStartNumber = pageStartNumber;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public Tincoming getObj() {
		return obj;
	}

	public void setObj(Tincoming obj) {
		this.obj = obj;
	}

	public Ttokenitem getObjProduct() {
		return objProduct;
	}

	public void setObjProduct(Ttokenitem objProduct) {
		this.objProduct = objProduct;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Integer getItemqty() {
		return itemqty;
	}

	public void setItemqty(Integer itemqty) {
		this.itemqty = itemqty;
	}

	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}

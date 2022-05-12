package com.sdd.caption.viewmodel;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.TincomingDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tincoming;
import com.sdd.caption.model.TincomingListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class IncomingListVm {
	
	Session zkSession = Sessions.getCurrent();
	
	private TincomingListModel model;
	private TincomingDAO oDao = new TincomingDAO();
	
	private Muser oUser;
	
	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String filter;	
	private Integer year;
	private Integer month;
	private String status;	
	private String productgroup;
	private String producttype;
	
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
	@Wire
	private Combobox cbMonth;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg)
			throws ParseException {
		Selectors.wireComponents(view, this, false);				
		productgroup = arg;
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);

			}
		});	
		
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Tincoming>() {

				@Override
				public void render(Row row, final Tincoming data, int index)
						throws Exception {
					row.getChildren()
					.add(new Label(
							String.valueOf((SysUtils.PAGESIZE * pageStartNumber)
									+ index + 1)));
			
			if (data.getProductgroup().equals(AppUtils.PRODUCTGROUP_TOKEN)) {
				A a = new A(data.getIncomingid());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("obj", data);	
						map.put("arg", arg);
						Window win = (Window) Executions.createComponents("/view/inventory/incomingtokendata.zul", null, map);
						win.setWidth("45%");
						win.setClosable(true);							
						win.doModal();
					}
				});
				row.getChildren().add(a);
				
			} else if (data.getProductgroup().equals(AppUtils.PRODUCTGROUP_PINPAD)) {
				A a = new A(data.getIncomingid());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("obj", data);	
						map.put("arg", arg);
						Window win = (Window) Executions.createComponents("/view/inventory/incomingpinpaddata.zul", null, map);
						win.setWidth("45%");
						win.setClosable(true);							
						win.doModal();
					}
				});
				row.getChildren().add(a);
				
			} else if (data.getProductgroup().equals(AppUtils.PRODUCTGROUP_DOCUMENT)) {
				A a = new A(data.getIncomingid());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("obj", data);	
						map.put("arg", arg);
						Window win = (Window) Executions.createComponents("/view/inventory/incomingsecuritiesdata.zul", null, map);
						win.setWidth("45%");
						win.setClosable(true);							
						win.doModal();
					}
				});
				row.getChildren().add(a);
			} else 
				row.getChildren().add(new Label(data.getIncomingid()));
			row.getChildren().add(new Label(data.getMproducttype().getProducttype()));
			row.getChildren().add(new Label(data.getItemqty() != null ? NumberFormat.getInstance().format(data.getItemqty()) : ""));
			row.getChildren().add(new Label(data.getMemo() != null ? data.getMemo() : "-"));
			Label lblStatus = new Label(AppData.getStatusLabel(data.getStatus()));
			if (data.getStatus().equals(AppUtils.STATUS_INVENTORY_INCOMINGDECLINE))
				lblStatus.setTooltiptext(data.getDecisionmemo());
			row.getChildren().add(lblStatus);
			row.getChildren().add(new Label(data.getEntryby() != null ? data.getEntryby() : "-"));
			row.getChildren().add(
					new Label(data.getEntrytime() != null ? datetimeLocalFormatter.format(data.getEntrytime()) : "-"));
			row.getChildren().add(new Label(data.getDecisionby() != null ? data.getDecisionby() : "-"));
			row.getChildren().add(new Label(data.getDecisiontime() != null ? datetimeLocalFormatter
					.format(data.getDecisiontime()) : "-"));
			row.getChildren().add(new Label(data.getMsupplier() != null ? data.getMsupplier().getSuppliername() : "-"));
			row.getChildren().add(new Label(data.getSpkno() != null ? data.getSpkno() : "-"));
			row.getChildren().add(new Label(data.getSpkdate() != null ? dateLocalFormatter.format(data.getSpkdate()) : "-"));
			row.getChildren().add(new Label(data.getManufacturedate() != null ? datetimeLocalFormatter
					.format(data.getManufacturedate()) : "-"));
			row.getChildren().add(new Label(data.getHarga() != null ? "Rp" + NumberFormat.getNumberInstance().format(data.getHarga()) : "0"));
			
					
				}

			});
		}
		
		String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length; i++) {
	      Comboitem item = new Comboitem();
	      item.setLabel(months[i]);
	      item.setValue(i+1);
	      cbMonth.appendChild(item);
	    }
	    
		doReset();
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		if (year != null && month != null) {
			filter = "extract(year from entrytime) = " + year + " and "
					+ "extract(month from entrytime) = " + month + " and productgroup = '" + productgroup + "'";
			
			if (oUser.getMbranch().getBranchlevel() == 2) {
				filter += " and mregionfk = " + oUser.getMbranch().getMregion().getMregionpk();
			} else if (oUser.getMbranch().getBranchlevel() == 3) {
				filter += " and mbranchfk = " + oUser.getMbranch().getMbranchpk();
			}
			if (status.length() > 0)
				filter += " and status = '" + status + "'";
			if (producttype != null && producttype.trim().length() > 0) {
				if (filter.length() > 0)
					filter += " and ";
				filter += "mproducttype.producttype like '%" + producttype.trim().toUpperCase() + "%'";
			}
			
			System.out.println("user : " + oUser.getUsername() + "and branchlevel" + oUser.getMbranch().getBranchlevel());
			
			needsPageUpdate = true;
			paging.setActivePage(0);
			pageStartNumber = 0;
			refreshModel(pageStartNumber);
		}		
	}
		
	@Command
	@NotifyChange("*")
	public void doReset() {
		year = Calendar.getInstance().get(Calendar.YEAR);
		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		status = "";
		doSearch();
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "tincomingpk desc";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TincomingListModel(activePage, SysUtils.PAGESIZE, filter,
				orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		grid.setModel(model);
	}	

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public Muser getoUser() {
		return oUser;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}	
		
}

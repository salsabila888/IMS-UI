package com.sdd.caption.viewmodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.model.TorderListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.utils.SysUtils;

public class InquiryOrderCabangVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	
	private TorderListModel model;

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String orderby;
	private String orderid;
	private String filter;
	private Date orderdate;
	private Mbranch mbranch;

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	@Wire
	private Combobox cbBranch;
	@Wire
	private Paging paging;
	@Wire
	private Grid grid;
	@Wire
	private Row rowBranch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ParseException {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");

		if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
			rowBranch.setVisible(true);
		} else {
			rowBranch.setVisible(false);
		}
		
		paging.addEventListener("onPaging", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);

			}
		});
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Torder>() {

				@Override
				public void render(Row row, final Torder data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1)));

					row.getChildren().add(new Label(data.getOrderid()));
					row.getChildren().add(new Label(dateLocalFormatter.format(data.getEntrytime())));
					row.getChildren().add(
							new Label(data != null ? NumberFormat.getInstance().format(data.getTotaldata()) : "0"));
					row.getChildren().add(new Label(data.getMemo()));
					row.getChildren().add(new Label(AppData.getStatusLabel(data.getStatus())));
				}
			});
		}
		doReset();
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "entrytime";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new TorderListModel(activePage, SysUtils.PAGESIZE, filter, orderby);
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
		try {
			if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
				if (mbranch != null) {
					filter = "mbranchfk = " + mbranch.getMbranchpk();
					if (orderdate != null) {
						if (filter.length() > 0)
							filter += " and ";
						filter += "DATE(entrytime) = '" + dateFormatter.format(orderdate) + "'";
					}
					if (orderid != null) {
						if (filter.length() > 0)
							filter += " and ";
						filter += "orderno = '" + orderid.toUpperCase() + "'";
					}

					if (filter.length() > 0) {
						needsPageUpdate = true;
						paging.setActivePage(0);
						pageStartNumber = 0;
						refreshModel(pageStartNumber);
					}
				}
			} else {
				filter = "mbranchfk = " + oUser.getMbranch().getMbranchpk();
				if (orderdate != null) {
					if (filter.length() > 0)
						filter += " and ";
					filter += "DATE(entrytime) = '" + dateFormatter.format(orderdate) + "'";
				}
				if (orderid != null) {
					if (filter.length() > 0)
						filter += " and ";
					filter += "orderno = '" + orderid.toUpperCase() + "'";
				}

				if (filter.length() > 0) {
					needsPageUpdate = true;
					paging.setActivePage(0);
					pageStartNumber = 0;
					refreshModel(pageStartNumber);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		mbranch = null;
		orderdate = null;
		cbBranch.setValue(null);
	}

	public ListModelList<Mbranch> getMbranchmodel() {
		ListModelList<Mbranch> lm = null;
		try {
			if (oUser != null) {
				if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 700) {
					lm = new ListModelList<Mbranch>(AppData.getMbranch());
				} else if (Integer.parseInt(oUser.getMbranch().getBranchid().trim()) < 700
						&& Integer.parseInt(oUser.getMbranch().getBranchid().trim()) >= 600) {
					lm = new ListModelList<Mbranch>(
							AppData.getMbranch("mregionfk = " + oUser.getMbranch().getMregion().getMregionpk()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}

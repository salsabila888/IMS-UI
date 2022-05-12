package com.sdd.caption.viewmodel;

import java.text.NumberFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.domain.Mproducttype;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.model.MproducttypeListModel;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.caption.utils.ProductgroupBean;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class MproducttypeNonCardVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Session session;
	private Transaction transaction;

	private MproducttypeListModel model;
	private MproducttypeDAO oDao = new MproducttypeDAO();

	private int pageStartNumber;
	private int pageTotalSize;
	private boolean needsPageUpdate;
	private String filter;
	private String filteralert;
	private String orderby;
	private boolean isInsert;

	private Mproducttype objForm;
	private ProductgroupBean productgroup;
	private String productgroupcode;	
	private String productgroupname;
	private String producttype;

	@Wire
	private Button btnSave;
	@Wire
	private Button btnCancel;
	@Wire
	private Button btnDelete;
	@Wire
	private Combobox cbProductgroup;
	@Wire
	private Paging paging;
	@Wire
	private Listbox listbox;
	@Wire
	private Combobox cbProductunit;
	@Wire
	private Intbox ibProductunitqty;
	@Wire
	private Div divForm;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("filteralert") String filteralert) {
		Selectors.wireComponents(view, this, false);
		this.filteralert = filteralert;		
		if (filteralert != null && filteralert.trim().length() > 0)
			divForm.setVisible(false);
		
		paging.addEventListener("onPaging", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				refreshModel(pageStartNumber);
			}
		});

		needsPageUpdate = true;
		doReset();

		if (listbox != null) {
			listbox.setItemRenderer(new ListitemRenderer<Mproducttype>() {

				@Override
				public void render(Listitem item, final Mproducttype data, int index)
						throws Exception {
					Listcell cell = new Listcell(String
							.valueOf((SysUtils.PAGESIZE * pageStartNumber)
									+ index + 1));
					item.appendChild(cell);
					cell = new Listcell(AppData.getProductgroupLabel(data.getProductgroupcode()));
					item.appendChild(cell);
					cell = new Listcell(data.getProducttype());
					item.appendChild(cell);
					cell = new Listcell(data.getLaststock() != null ? NumberFormat.getInstance().format(data.getLaststock()) : "0");
					item.appendChild(cell);
					cell = new Listcell(data.getStockmin() != null ? NumberFormat.getInstance().format(data.getStockmin()) : "0");
					item.appendChild(cell);

				}
			});
		}

		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (listbox.getSelectedIndex() != -1) {
					isInsert = false;
					btnSave.setLabel(Labels.getLabel("common.update"));
					btnCancel.setDisabled(false);
					btnDelete.setDisabled(false);
					
					cbProductgroup.setValue(AppData.getProductgroupLabel(objForm.getProductgroupcode()));
					cbProductunit.setValue(AppData.getProductunitLabel(objForm.getProductunit()));
					doProductgroupSelected(objForm.getProductgroupcode());					
				}
			}
		});
	}

	@NotifyChange("pageTotalSize")
	public void refreshModel(int activePage) {
		orderby = "productgroupcode,producttype";
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new MproducttypeListModel(activePage, SysUtils.PAGESIZE, filter,
				orderby);
		if (needsPageUpdate) {
			pageTotalSize = model.getTotalSize(filter);
			needsPageUpdate = false;
		}
		paging.setTotalSize(pageTotalSize);
		listbox.setModel(model);
	}

	@Command
	@NotifyChange("pageTotalSize")
	public void doSearch() {
		if (filteralert != null && filteralert.trim().length() > 0)
			filter = filteralert;
		else filter = "productgroupcode != '" + AppUtils.PRODUCTGROUP_CARD + "'";
		if (productgroupcode != null && productgroupcode.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "productgroupcode = '" + productgroupcode.trim().toUpperCase() + "'";
		}
		if (producttype != null && producttype.trim().length() > 0) {
			if (filter.length() > 0)
				filter += " and ";
			filter += "producttype like '%" + producttype.trim().toUpperCase()
					+ "%'";
		}
		needsPageUpdate = true;
		paging.setActivePage(0);
		pageStartNumber = 0;
		refreshModel(pageStartNumber);
	}
	
	@Command
	public void doProductgroupSelected(@BindingParam("item") String item) {
		if (item != null && item.trim().length() > 0) {
			if (item.equals(AppUtils.PRODUCTGROUP_SUPPLIES)) {
				cbProductunit.setDisabled(false);
				ibProductunitqty.setReadonly(false);
			} else {
				cbProductunit.setDisabled(true);
				ibProductunitqty.setReadonly(true);
			}
		}
	}
	
	@Command
	public void doProductunitSelected(@BindingParam("item") String item) {
		if (item != null && item.trim().length() > 0) {
			if (item.equals(AppUtils.PRODUCTUNIT_BOX)) {				
				ibProductunitqty.setReadonly(false);
			} else {
				ibProductunitqty.setValue(1);
				ibProductunitqty.setReadonly(true);
			}
		}
	}

	@Command
	@NotifyChange("*")
	public void cancel() {
		doReset();
	}

	@Command
	@NotifyChange("*")
	public void save() {
		try {
			Muser oUser = (Muser) zkSession.getAttribute("oUser");
			if (oUser == null)
				oUser = new Muser();

			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			
			objForm.setProductgroupname(AppData.getProductgroupLabel(objForm.getProductgroupcode()));
			objForm.setLastupdated(new Date());
			objForm.setUpdatedby(oUser.getUserid());
			if (objForm.getProductunit().equals(AppUtils.PRODUCTUNIT_PCS) || objForm.getProductunitqty() == null || objForm.getProductunitqty() <= 0)
				objForm.setProductunitqty(1);
			
			oDao.save(session, objForm);
			transaction.commit();
			session.close();
			if (isInsert) {
				needsPageUpdate = true;
				Clients.showNotification(Labels.getLabel("common.add.success"),
						"info", null, "middle_center", 3000);
			} else
				Clients.showNotification(
						Labels.getLabel("common.update.success"), "info", null,
						"middle_center", 3000);
			doReset();
		} catch (HibernateException e) {
			transaction.rollback();
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange("*")
	public void delete() {
		try {
			Messagebox.show(Labels.getLabel("common.delete.confirm"),
					"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								try {
									session = StoreHibernateUtil.openSession();
									transaction = session.beginTransaction();
									oDao.delete(session, objForm);
									transaction.commit();
									session.close();

									Clients.showNotification(Labels
											.getLabel("common.delete.success"),
											"info", null, "middle_center", 3000);

									needsPageUpdate = true;
									doReset();
									BindUtils.postNotifyChange(null, null,
											MproducttypeNonCardVm.this, "*");
								} catch (HibernateException e) {
									transaction.rollback();
									Messagebox.show(
											"Error : " + e.getMessage(),
											"Error", Messagebox.OK,
											Messagebox.ERROR);
									e.printStackTrace();
								} catch (Exception e) {
									Messagebox.show(
											"Error : " + e.getMessage(),
											"Error", Messagebox.OK,
											Messagebox.ERROR);
									e.printStackTrace();
								}
							}
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {
		isInsert = true;
		objForm = new Mproducttype();		
		objForm.setProductunit(AppUtils.PRODUCTUNIT_PCS);
		objForm.setProductunitqty(1);
		objForm.setSlacountertype(AppUtils.SLACOUNTERTYPE_DATEORDER);
		cbProductgroup.setValue(null);
		cbProductunit.setValue(AppData.getProductunitLabel(objForm.getProductunit()));
		doProductunitSelected(objForm.getProductunit());
		btnCancel.setDisabled(true);
		btnDelete.setDisabled(true);
		btnSave.setLabel(Labels.getLabel("common.save"));
		doSearch();
	}

	public Validator getValidator() {
		return new AbstractValidator() {
			
			@Override
			public void validate(ValidationContext ctx) {												
				String productgroupcode = (String) ctx.getProperties("productgroupcode")[0]
						.getValue();
				String producttype = (String) ctx.getProperties("producttype")[0]
						.getValue();				
					
				if (productgroupcode == null || "".equals(productgroupcode.trim()))
					this.addInvalidMessage(ctx, "productgroupcode",
							Labels.getLabel("common.validator.empty"));
				if (producttype == null || "".equals(producttype.trim()))
					this.addInvalidMessage(ctx, "producttype",
							Labels.getLabel("common.validator.empty"));				
			}
		};
	}

	public Mproducttype getObjForm() {
		return objForm;
	}

	public void setObjForm(Mproducttype objForm) {
		this.objForm = objForm;
	}


	public String getProductgroupcode() {
		return productgroupcode;
	}

	public void setProductgroupcode(String productgroupcode) {
		this.productgroupcode = productgroupcode;
	}

	public String getProductgroupname() {
		return productgroupname;
	}

	public void setProductgroupname(String productgroupname) {
		this.productgroupname = productgroupname;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public ProductgroupBean getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(ProductgroupBean productgroup) {
		this.productgroup = productgroup;
	}

}

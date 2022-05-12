package com.sdd.caption.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;

import com.sdd.caption.dao.MproductgroupDAO;
import com.sdd.caption.dao.TreturnDAO;
import com.sdd.caption.domain.Mproductgroup;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Vsumbyproductgroup;
import com.sdd.caption.utils.AppUtils;

public class ReturnVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private String arg;
	private String filter;

	private Div divContent;

	@Wire
	private Div divEntry, divList, divApproval;
	@Wire
	private Div divProduct;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("arg") String arg,
			@ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		this.arg = arg;
		this.divContent = divContent;
		if (arg.equals("entry")) {
			divEntry.setVisible(true);
		} else if (arg.equals("list")) {
			divList.setVisible(true);
		} else if (arg.equals("approval") || arg.equals("approvalwil") || arg.equals("approvalpfa")
				|| arg.equals("approvalopr")) {
			divApproval.setVisible(true);
		}
		doRender();
	}

	@NotifyChange("*")
	public void doRender() {
		try {
			filter = "0=0";
			if (oUser.getMbranch().getBranchlevel() == 2)
				filter += " and mregionfk = " + oUser.getMbranch().getMregion().getMregionpk();
			else if (oUser.getMbranch().getBranchlevel() == 3)
				filter += " and mbranchfk = " + oUser.getMbranch().getMbranchpk();

			if (arg.equals("approval")) {
				filter += " and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVAL + "'";
			} else if (arg.equals("approvalwil")) {
				filter += " and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH + "'";
			} else if (arg.equals("approvalpfa")) {
				filter += " and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALPFA + "'";
			} else if (arg.equals("approvalopr")) {
				filter += " and status = '" + AppUtils.STATUS_RETUR_WAITAPPROVALOPR + "'";
			}

			List<Mproductgroup> objList = new MproductgroupDAO().listByFilter("productgroupcode != '01'",
					"productgroupcode");
			for (Mproductgroup obj : objList) {
				List<Vsumbyproductgroup> productList = new TreturnDAO()
						.getSumdataByProductgroup(filter + " and productgroup = '" + obj.getProductgroupcode() + "'");

				Div divRow = new Div();
				divRow.setClass("col-md-3");

				Div divCard = new Div();
				divCard.setClass("card");

				Div divCardHeader = new Div();
				divCardHeader.setClass("card-header");

				Image image = new Image();
				image.setWidth("100%");
				image.setHeight("100%");
				if (obj.getProductgroupcode().equals("01")) {
					image.setSrc("/files/img/card.png");
					divCard.setClass("card text-white bg-primary mb-3");
				} else if (obj.getProductgroupcode().equals("02")) {
					image.setSrc("/files/img/tokenlogo.png");
					divCard.setClass("card text-white bg-success mb-3");
				} else if (obj.getProductgroupcode().equals("03")) {
					image.setSrc("/files/img/pinpadlogo.png");
					divCard.setClass("card text-white bg-danger mb-3");
				} else if (obj.getProductgroupcode().equals("04")) {
					image.setSrc("/files/img/surat.png");
					divCard.setClass("card text-white bg-secondary mb-3");
				} else if (obj.getProductgroupcode().equals("06")) {
					image.setSrc("/files/img/pinmailer.png");
					divCard.setClass("card text-white bg-info mb-3");
				} else if (obj.getProductgroupcode().equals("09")) {
					image.setSrc("/files/img/derivatif.png");
					divCard.setClass("card text-white bg-light mb-3");
				}
				Label lbl = new Label();
				lbl.setValue(" " + obj.getProductgroup());
				lbl.setStyle("font-size: 14px; font-weight: bold");

				divCardHeader.appendChild(image);

				Separator s = new Separator();
				Div div = new Div();
				div.setStyle("text-align:center");
				div.appendChild(s);
				div.appendChild(lbl);
				divCardHeader.appendChild(div);

				Div divBody = new Div();
				divBody.setClass("card-body");
				divBody.setStyle("text-align:right");
				lbl = new Label("Jumlah Usulan : ");
				lbl.setStyle("font-size: 14px");
				divBody.appendChild(lbl);
				lbl = new Label();
				if (productList.size() > 0)
					lbl.setValue(String.valueOf(productList.get(0).getTotal()));
				else
					lbl.setValue(String.valueOf(0));
				lbl.setStyle("font-size: 14px");
				divBody.appendChild(lbl);

				Div divLabel = new Div();
				divLabel.setClass("card-footer");
				Button btn = new Button();
				btn.setSclass("btn btn-light btn-sm");
				if (arg.equals("entry")) {
					btn.setLabel("Buat Incoming Baru");
				} else {
					btn.setLabel("Tampilkan Data");
				}
				btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						divContent.getChildren().clear();
						Map<String, Object> map = new HashMap<>();
						map.put("arg", obj.getProductgroupcode());
						map.put("content", divContent);
						if (arg.equals("entry")) {
							if (obj.getProductgroupcode().equals(AppUtils.PRODUCTGROUP_CARD))
								Executions.createComponents("/view/return/returnentry.zul", divContent, map);
							else if (obj.getProductgroupcode().equals(AppUtils.PRODUCTGROUP_TOKEN))
								Executions.createComponents("/view/return/returentrytoken.zul", divContent, map);
							else if (obj.getProductgroupcode().equals(AppUtils.PRODUCTGROUP_PINPAD))
								Executions.createComponents("/view/return/returentrypinpad.zul", divContent, map);
							else if (obj.getProductgroupcode().equals(AppUtils.PRODUCTGROUP_DOCUMENT))
								Executions.createComponents("/view/return/returentrydocument.zul", divContent, map);
						} else if (arg.equals("list")) {
							Executions.createComponents("/view/return/returproductlist.zul", divContent, map);
						} else if (arg.equals("approval") || arg.equals("approvalwil") || arg.equals("approvalpfa")
								|| arg.equals("approvalopr")) {
							if (arg.equals("approval")) {
								map.put("stats", AppUtils.STATUS_RETUR_WAITAPPROVAL);
							} else if (arg.equals("approvalwil")) {
								map.put("stats", AppUtils.STATUS_RETUR_WAITAPPROVALWILAYAH);
							} else if (arg.equals("approvalpfa")) {
								map.put("stats", AppUtils.STATUS_RETUR_WAITAPPROVALPFA);
							} else if (arg.equals("approvalopr")) {
								map.put("stats", AppUtils.STATUS_RETUR_WAITAPPROVALOPR);
							}
							Executions.createComponents("/view/return/returproductapproval.zul", divContent, map);
						}
					}
				});
				divLabel.appendChild(btn);

				Separator separator = new Separator();
				Separator separator2 = new Separator();

				divCard.appendChild(divCardHeader);
				divCard.appendChild(divBody);
				divCard.appendChild(divLabel);

				divRow.appendChild(divCard);
				divRow.appendChild(separator);
				divRow.appendChild(separator2);
				divProduct.appendChild(divRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

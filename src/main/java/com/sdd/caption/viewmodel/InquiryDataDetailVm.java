package com.sdd.caption.viewmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TdeliveryDAO;
import com.sdd.caption.dao.TdeliverydataDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.dao.TpaketDAO;
import com.sdd.caption.dao.TpaketdataDAO;
import com.sdd.caption.dao.TsecuritiesitemDAO;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.domain.Tdeliverydata;
import com.sdd.caption.domain.Torderitem;
import com.sdd.caption.domain.Tpaket;
import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.domain.Tsecuritiesitem;
import com.sdd.caption.pojo.InquiryDetailBean;
import com.sdd.caption.pojo.InquiryOrder;
import com.sdd.caption.utils.AppData;

public class InquiryDataDetailVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private InquiryOrder obj;
	private Integer branchlevel;

	List<InquiryDetailBean> objList = new ArrayList<InquiryDetailBean>();

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd MMM yyyy");

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") InquiryOrder obj) throws ParseException {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		if(oUser != null)
			branchlevel = oUser.getMbranch().getBranchlevel();

		this.obj = obj;
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<InquiryDetailBean>() {

				@Override
				public void render(Row row, final InquiryDetailBean data, int index) throws Exception {
					row.getChildren().add(new Label(data.getTanggal()));
					row.getChildren().add(new Label(data.getKeterangan()));
				}
			});
		}
		doReset();
	}

	public void doReset() {
		try {
			if (branchlevel == 1) {
				List<Tsecuritiesitem> incomingList = new TsecuritiesitemDAO().listNativeByFilter("mproducttypefk = "
						+ obj.getMproducttype().getMproducttypepk() + " and itemno = '" + obj.getItemno().trim() + "'",
						"tsecuritiesitempk desc");
				if (incomingList.size() > 0) {
					Tsecuritiesitem incoming = incomingList.get(0);

					InquiryDetailBean bean = new InquiryDetailBean();
					bean.setTanggal("Incoming, " + dateLocalFormatter.format(incoming.getTincoming().getEntrytime()));
					bean.setKeterangan("Data dalam proses incoming dengan status "
							+ AppData.getStatusLabel(incoming.getStatus()) + ".");
					objList.add(bean);

					List<Torderitem> orderList = new TorderitemDAO()
							.listNativeByFilter("mproducttypefk = " + obj.getMproducttype().getMproducttypepk()
									+ " and itemno = '" + obj.getItemno().trim() + "'", "torderitempk desc");
					if (orderList.size() > 0) {
						Torderitem order = orderList.get(0);

						bean = new InquiryDetailBean();
						bean.setTanggal("Order, " + dateLocalFormatter.format(order.getTorder().getInserttime()));
						bean.setKeterangan("Data dalam proses order dengan status "
								+ AppData.getStatusLabel(order.getTorder().getStatus()) + ".");
						objList.add(bean);

						Tpaket paket = new TpaketDAO().findByFilter("torderfk = " + order.getTorder().getTorderpk());
						if (paket != null) {
							bean = new InquiryDetailBean();
							bean.setTanggal("Paket, " + dateLocalFormatter.format(paket.getProcesstime()));
							bean.setKeterangan("Data dalam proses pembuatan paket dengan status "
									+ AppData.getStatusLabel(order.getTorder().getStatus()) + ".");
							objList.add(bean);

							Tpaketdata paketdata = new TpaketdataDAO()
									.findByFilter("tpaketfk = " + paket.getTpaketpk());
							Tdeliverydata deliverydata = new TdeliverydataDAO()
									.findByFilter("tpaketdatafk = " + paketdata.getTpaketdatapk());
							if (deliverydata != null) {
								Tdelivery delivery = new TdeliveryDAO()
										.findByFilter("tdeliverypk = " + deliverydata.getTdelivery().getTdeliverypk());
								
								bean = new InquiryDetailBean();
								bean.setTanggal("Delivery, " + dateLocalFormatter.format(delivery.getProcesstime()));
								bean.setKeterangan("Data dalam proses delivery dengan status "
										+ AppData.getStatusLabel(delivery.getStatus()) + ".");
								objList.add(bean);
							}
						}
					}
				}
			}
			
			grid.setModel(new ListModelList<InquiryDetailBean>(objList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

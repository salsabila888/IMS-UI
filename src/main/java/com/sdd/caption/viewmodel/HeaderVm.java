package com.sdd.caption.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Button;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.TnotifDAO;
import com.sdd.caption.domain.Mmenu;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Tnotif;
import com.sdd.caption.domain.Vnotif;
import com.sdd.utils.db.StoreHibernateUtil;

public class HeaderVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	private Integer totalnotif;
	private String user;

	private TnotifDAO oDao = new TnotifDAO();

	@Wire
	private Div divNotif;
	@Wire
	private Label lblNotif, lblUser;
	@Wire
	private Button btnNotif;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		if (oUser != null) {
			user = oUser.getUserid() + " - " + oUser.getMusergroup().getUsergroupname();
		}
		doNotif();

	}

	@Command
	public void doCheck() {
		Window win = (Window) Executions.createComponents("/accountinformation.zul", null, null);
		win.setWidth("40%");
		win.setClosable(true);
		win.doModal();
	}

	@Command
	public void doLogout() {
		if (zkSession.getAttribute("oUser") != null) {
			zkSession.removeAttribute("oUser");
		}
		Executions.sendRedirect("/logout.zul");
	}

	@NotifyChange("*")
	public void doNotif() {
		try {
			List<Vnotif> oList = oDao.listNotif("musergrouppk = " + oUser.getMusergroup().getMusergrouppk());
			totalnotif = 0;
			if (oList.size() > 0) {
				for (Vnotif obj : oList) {
					A a = new A();
					a.setStyle("font-weight: bold");
					a.setLabel(obj.getTotalnotif() + " " + obj.getNotiftxt());
					a.setClass("dropdown-item");
					a.setStyle("font-size: 15px;font-weight: bold");
					a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {

							Mmenu menu = null;
							List<Tnotif> notifList = oDao.listByFilter("mmenufk = " + obj.getMmenupk(), "tnotifpk");
							Session session = StoreHibernateUtil.openSession();
							Transaction transaction = session.beginTransaction();
							for (Tnotif notif : notifList) {
								menu = notif.getMmenu();
								oDao.delete(session, notif);
							}
							transaction.commit();
							Sessions.getCurrent().setAttribute("menu", menu);
							Executions.sendRedirect("/view/index.zul");
							session.close();
						}
					});
					divNotif.appendChild(a);
					totalnotif = totalnotif + obj.getTotalnotif();
				}
				btnNotif.setSclass("btn btn-danger btn-sm dropdown-toggle");
			} else {
				btnNotif.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getTotalnotif() {
		return totalnotif;
	}

	public void setTotalnotif(Integer totalnotif) {
		this.totalnotif = totalnotif;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}

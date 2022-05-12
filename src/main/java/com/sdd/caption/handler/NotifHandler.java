package com.sdd.caption.handler;

import org.hibernate.Session;

import com.sdd.caption.dao.TnotifDAO;
import com.sdd.caption.domain.Mmenu;
import com.sdd.caption.domain.Tnotif;

public class NotifHandler {

	public static void doNotif(Session session, Mmenu menu, String text) {
		try {
			Tnotif notif = new Tnotif();
			notif.setNotiftxt(text);
			notif.setNotifcount(1);
			notif.setMmenu(menu);

			new TnotifDAO().save(session, notif);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

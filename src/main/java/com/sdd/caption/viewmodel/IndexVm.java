package com.sdd.caption.viewmodel;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.sdd.caption.dao.DashboardDAO;
import com.sdd.caption.domain.Muser;

public class IndexVm {
	
	private Muser oUser;
	private DashboardDAO oDao = new DashboardDAO();
	
	@Wire
	private Window winIndex;
	@Wire
	private Div divStockEstimate;
	@Wire
	private Div divOrdervsPerso;
	@Wire
	private Div divPersovsdlv;
	@Wire
	private Div divSlaorder;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);	
		Map<String, Object> map1 = new HashMap<>();
		map1.put("isIndex", new Boolean(true));
		Executions.createComponents("/view/dashboard/chartstockestimate.zul", divStockEstimate, map1);
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("isIndex", new Boolean(true));
		Executions.createComponents("/view/dashboard/chartordervsperso.zul", divOrdervsPerso, map2);	
		
		Map<String, Object> map3 = new HashMap<>();
		map3.put("isIndex", new Boolean(true));
		Executions.createComponents("/view/dashboard/chartpersovsdlv.zul", divPersovsdlv, map3);
		
		Map<String, Object> map4 = new HashMap<>();
		map4.put("isIndex", new Boolean(true));
		Executions.createComponents("/view/dashboard/chartslaorder.zul", divSlaorder, map4);
	}
	
	@Command
	public void doViewStockestimate() {
		Div divContent = (Div) winIndex.getParent();
		divContent.getChildren().clear();
		Executions.createComponents("/view/dashboard/chartstockestimate.zul", divContent, null);
	}

	@Command
	public void doViewOrdervsperso() {
		Div divContent = (Div) winIndex.getParent();
		divContent.getChildren().clear();
		Executions.createComponents("/view/dashboard/chartordervsperso.zul", divContent, null);
	}
	
	@Command
	public void doViewPersovsdlv() {
		if (oUser != null) {
            try {
                if (oDao.usermenuChecker("muserpk = " + oUser.getMuserpk() + " and menupath = '/view/dashboard/chartstockestimate.zul'") > 0) {
                    Div divContent = (Div) winIndex.getParent();
                    divContent.getChildren().clear();
                    Executions.createComponents("/view/dashboard/chartstockestimate.zul", divContent, null);
                } else {
                    Messagebox.show("Anda tidak punya kewenangan untuk mengakses modul ini", "Info", 1, "z-messagebox-icon z-messagebox-information");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	@Command
	public void doViewSlaorder() {
		Div divContent = (Div) winIndex.getParent();
		divContent.getChildren().clear();
		Executions.createComponents("/view/dashboard/chartslaorder.zul", divContent, null);
	}

}

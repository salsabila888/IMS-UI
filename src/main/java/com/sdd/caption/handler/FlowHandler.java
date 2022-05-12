package com.sdd.caption.handler;

import java.util.Date;

import org.hibernate.Session;

import com.sdd.caption.dao.TorderflowDAO;
import com.sdd.caption.domain.Tembossbranch;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Torderflow;

public class FlowHandler {

	public static void doFlow(Session session, Tembossbranch tembossbranch, Torder torder, String flowgroup, String memo, String user) throws Exception {
		try {
			Torderflow obj = new Torderflow();
			if(tembossbranch != null) {
				obj.setTembossbranch(tembossbranch);
				obj.setFlowname(tembossbranch.getStatus());
				if(tembossbranch.getTembossproduct().getMproduct() != null)
					obj.setProductgroup(tembossbranch.getTembossproduct().getMproduct().getProductgroup());
				else obj.setProductgroup("01");
				obj.setTotaldata(tembossbranch.getTotaldata());
			} else {
				obj.setTorder(torder);
				obj.setFlowname(torder.getStatus());
				obj.setProductgroup(torder.getProductgroup());
				obj.setTotaldata(torder.getItemqty());	
			}
			obj.setFlowuser(user);
			obj.setFlowgroup(flowgroup);
			obj.setFlowtime(new Date());
			obj.setMemo(memo);
			new TorderflowDAO().save(session, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TdeliveryDAO;
import com.sdd.caption.domain.Vreportdlv;


public class VreportdlvListModel extends AbstractPagingListModel<Vreportdlv> implements Sortable<Vreportdlv> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Vreportdlv> oList;  

	public VreportdlvListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vreportdlv> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TdeliveryDAO oDao = new TdeliveryDAO();		
		try {
			oList = oDao.listPagingReportdlv(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TdeliveryDAO oDao = new TdeliveryDAO();	
		try {
			_size = oDao.pageCountReportdlv(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vreportdlv> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vreportdlv> cmpr) {
		return null;
	}
}

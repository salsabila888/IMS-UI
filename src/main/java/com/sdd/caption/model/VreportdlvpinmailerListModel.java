package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TdeliveryDAO;
import com.sdd.caption.domain.Vreportdlvpinmailer;


public class VreportdlvpinmailerListModel extends AbstractPagingListModel<Vreportdlvpinmailer> implements Sortable<Vreportdlvpinmailer> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Vreportdlvpinmailer> oList;  

	public VreportdlvpinmailerListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vreportdlvpinmailer> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TdeliveryDAO oDao = new TdeliveryDAO();		
		try {
			oList = oDao.listPagingReportdlvPm(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TdeliveryDAO oDao = new TdeliveryDAO();	
		try {
			_size = oDao.pageCountReportdlvPm(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vreportdlvpinmailer> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vreportdlvpinmailer> cmpr) {
		return null;
	}
}

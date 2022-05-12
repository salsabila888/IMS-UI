package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpinpadorderdataDAO;
import com.sdd.caption.domain.Tpinpadorderdata;



public class TpinpadorderdataListModel extends AbstractPagingListModel<Tpinpadorderdata> implements Sortable<Tpinpadorderdata> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tpinpadorderdata> oList;  

	public TpinpadorderdataListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpinpadorderdata> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpinpadorderdataDAO oDao = new TpinpadorderdataDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpinpadorderdataDAO oDao = new TpinpadorderdataDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tpinpadorderdata> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tpinpadorderdata> cmpr) {
		return null;
	}
}

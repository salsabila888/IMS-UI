package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpinpaditemDAO;
import com.sdd.caption.domain.Tpinpaditem;



public class TpinpaditemListModel extends AbstractPagingListModel<Tpinpaditem> implements Sortable<Tpinpaditem> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tpinpaditem> oList;  

	public TpinpaditemListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpinpaditem> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpinpaditemDAO oDao = new TpinpaditemDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpinpaditemDAO oDao = new TpinpaditemDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tpinpaditem> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tpinpaditem> cmpr) {
		return null;
	}
}

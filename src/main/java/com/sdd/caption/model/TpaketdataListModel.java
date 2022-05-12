package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpaketdataDAO;
import com.sdd.caption.domain.Tpaketdata;

public class TpaketdataListModel extends AbstractPagingListModel<Tpaketdata> implements Sortable<Tpaketdata> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tpaketdata> oList;  

	public TpaketdataListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpaketdata> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpaketdataDAO oDao = new TpaketdataDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpaketdataDAO oDao = new TpaketdataDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tpaketdata> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tpaketdata> cmpr) {
		return null;
	}
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TdeliverycourierDAO;
import com.sdd.caption.domain.Tdeliverycourier;

public class TdeliverycourierListModel extends AbstractPagingListModel<Tdeliverycourier> implements Sortable<Tdeliverycourier>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tdeliverycourier> oList;  

	public TdeliverycourierListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tdeliverycourier> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TdeliverycourierDAO oDao = new TdeliverycourierDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TdeliverycourierDAO oDao = new TdeliverycourierDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tdeliverycourier> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tdeliverycourier> cmpr) {
		return null;
	}

}

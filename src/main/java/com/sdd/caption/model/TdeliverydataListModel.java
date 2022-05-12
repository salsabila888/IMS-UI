package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TdeliverydataDAO;
import com.sdd.caption.domain.Tdeliverydata;


public class TdeliverydataListModel extends AbstractPagingListModel<Tdeliverydata> implements Sortable<Tdeliverydata> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tdeliverydata> oList;  

	public TdeliverydataListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tdeliverydata> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TdeliverydataDAO oDao = new TdeliverydataDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TdeliverydataDAO oDao = new TdeliverydataDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tdeliverydata> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tdeliverydata> cmpr) {
		return null;
	}

}

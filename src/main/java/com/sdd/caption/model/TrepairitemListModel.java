package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TrepairitemDAO;
import com.sdd.caption.dao.TreturnitemDAO;
import com.sdd.caption.domain.Trepairitem;
import com.sdd.caption.domain.Treturnitem;

public class TrepairitemListModel extends AbstractPagingListModel<Trepairitem> implements Sortable<Trepairitem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Trepairitem> oList;  

	public TrepairitemListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Trepairitem> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TrepairitemDAO oDao = new TrepairitemDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TrepairitemDAO oDao = new TrepairitemDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Trepairitem> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Trepairitem> cmpr) {
		return null;
	}
	
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TorderitemDAO;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Torderitem;

public class TorderitemListModel extends AbstractPagingListModel<Torderitem> implements Sortable<Torderitem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Torderitem> oList;  

	public TorderitemListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Torderitem> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {
		TorderitemDAO oDao = new TorderitemDAO();
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}


	@Override
	public int getTotalSize(String filter) {
		TorderitemDAO oDao = new TorderitemDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Torderitem> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Torderitem> cmpr) {
		return null;
	}
	
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.ToutgoingDAO;
import com.sdd.caption.domain.Toutgoing;


public class ToutgoingListModel extends AbstractPagingListModel<Toutgoing> implements Sortable<Toutgoing> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Toutgoing> oList;  

	public ToutgoingListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Toutgoing> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		ToutgoingDAO oDao = new ToutgoingDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		ToutgoingDAO oDao = new ToutgoingDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Toutgoing> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Toutgoing> cmpr) {
		return null;
	}

}

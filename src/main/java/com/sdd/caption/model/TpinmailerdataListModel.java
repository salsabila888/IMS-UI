package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpinmailerdataDAO;
import com.sdd.caption.domain.Tpinmailerdata;


public class TpinmailerdataListModel extends AbstractPagingListModel<Tpinmailerdata> implements Sortable<Tpinmailerdata> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tpinmailerdata> oList;  

	public TpinmailerdataListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpinmailerdata> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpinmailerdataDAO oDao = new TpinmailerdataDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpinmailerdataDAO oDao = new TpinmailerdataDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tpinmailerdata> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tpinmailerdata> cmpr) {
		return null;
	}

}

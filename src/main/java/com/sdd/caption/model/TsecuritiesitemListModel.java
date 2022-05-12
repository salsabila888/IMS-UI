package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TsecuritiesitemDAO;
import com.sdd.caption.domain.Tsecuritiesitem;

public class TsecuritiesitemListModel extends AbstractPagingListModel<Tsecuritiesitem> implements Sortable<Tsecuritiesitem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tsecuritiesitem> oList; 
	
	public TsecuritiesitemListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tsecuritiesitem> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TsecuritiesitemDAO oDao = new TsecuritiesitemDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}
	
	@Override
	public int getTotalSize(String filter) {
		TsecuritiesitemDAO oDao = new TsecuritiesitemDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tsecuritiesitem> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tsecuritiesitem> cmpr) {
		return null;
	}

}

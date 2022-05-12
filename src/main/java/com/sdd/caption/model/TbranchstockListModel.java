package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.domain.Tbranchstock;

public class TbranchstockListModel extends AbstractPagingListModel<Tbranchstock> implements Sortable<Tbranchstock>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tbranchstock> oList;  

	public TbranchstockListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tbranchstock> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TbranchstockDAO oDao = new TbranchstockDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TbranchstockDAO oDao = new TbranchstockDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tbranchstock> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tbranchstock> cmpr) {
		return null;
	}

}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TderivatifproductDAO;
import com.sdd.caption.domain.Tderivatifproduct;

public class TderivatifproductListModel extends AbstractPagingListModel<Tderivatifproduct> implements Sortable<Tderivatifproduct>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tderivatifproduct> oList;  

	public TderivatifproductListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tderivatifproduct> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TderivatifproductDAO oDao = new TderivatifproductDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TderivatifproductDAO oDao = new TderivatifproductDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tderivatifproduct> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tderivatifproduct> cmpr) {
		return null;
	}

}

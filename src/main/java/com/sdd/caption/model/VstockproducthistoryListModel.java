package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.MproducttypeDAO;
import com.sdd.caption.domain.Vstockproducthistory;


public class VstockproducthistoryListModel extends AbstractPagingListModel<Vstockproducthistory> implements Sortable<Vstockproducthistory> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Vstockproducthistory> oList;  

	public VstockproducthistoryListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vstockproducthistory> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		MproducttypeDAO oDao = new MproducttypeDAO();		
		try {
			oList = oDao.listStockProductHistory(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MproducttypeDAO oDao = new MproducttypeDAO();	
		try {
			_size = oDao.pageCountStockHistory(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vstockproducthistory> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vstockproducthistory> cmpr) {
		return null;
	}
}

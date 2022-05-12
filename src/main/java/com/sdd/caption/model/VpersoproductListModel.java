package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpersoDAO;
import com.sdd.caption.domain.Vpersoproduct;


public class VpersoproductListModel extends AbstractPagingListModel<Vpersoproduct> implements Sortable<Vpersoproduct> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Vpersoproduct> oList;  

	public VpersoproductListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vpersoproduct> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpersoDAO oDao = new TpersoDAO();		
		try {
			oList = oDao.listPagingPersoProduct(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpersoDAO oDao = new TpersoDAO();	
		try {
			_size = oDao.pageCountPersoProduct(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vpersoproduct> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vpersoproduct> cmpr) {
		return null;
	}
}

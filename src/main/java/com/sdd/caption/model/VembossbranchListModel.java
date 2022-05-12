package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TembossbranchDAO;
import com.sdd.caption.domain.Vembossbranch;


public class VembossbranchListModel extends AbstractPagingListModel<Vembossbranch> implements Sortable<Vembossbranch> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Vembossbranch> oList;  

	public VembossbranchListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vembossbranch> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TembossbranchDAO oDao = new TembossbranchDAO() ;
		try {
			oList = oDao.listPagingInquiry(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TembossbranchDAO oDao = new TembossbranchDAO();	
		try {
			_size = oDao.pageCountInquiry(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vembossbranch> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vembossbranch> cmpr) {
		return null;
	}

}

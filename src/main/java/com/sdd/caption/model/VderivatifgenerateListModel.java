package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TderivatifDAO;
import com.sdd.caption.domain.Vderivatifgenerate;

public class VderivatifgenerateListModel extends AbstractPagingListModel<Vderivatifgenerate> implements Sortable<Vderivatifgenerate> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Vderivatifgenerate> oList;  

	public VderivatifgenerateListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Vderivatifgenerate> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TderivatifDAO oDao = new TderivatifDAO();		
		try {
			oList = oDao.listDerivatifGeneratePaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TderivatifDAO oDao = new TderivatifDAO();	
		try {
			_size = oDao.pageDerivatifGenerateCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Vderivatifgenerate> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Vderivatifgenerate> cmpr) {
		return null;
	}
}


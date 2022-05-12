package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.MpicbranchDAO;
import com.sdd.caption.dao.MpicproductDAO;
import com.sdd.caption.domain.Mpicbranch;

public class MpicbranchListModel extends AbstractPagingListModel<Mpicbranch> implements Sortable<Mpicbranch> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Mpicbranch> oList;  

	public MpicbranchListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Mpicbranch> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		MpicbranchDAO oDao = new MpicbranchDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MpicproductDAO oDao = new MpicproductDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Mpicbranch> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Mpicbranch> cmpr) {
		return null;
	}
}

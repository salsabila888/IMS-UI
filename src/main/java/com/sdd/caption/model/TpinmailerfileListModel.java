package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TpinmailerfileDAO;
import com.sdd.caption.domain.Tpinmailerfile;

public class TpinmailerfileListModel extends AbstractPagingListModel<Tpinmailerfile> implements Sortable<Tpinmailerfile>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Tpinmailerfile> oList;  

	public TpinmailerfileListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpinmailerfile> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpinmailerfileDAO oDao = new TpinmailerfileDAO() ;
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpinmailerfileDAO oDao = new TpinmailerfileDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Tpinmailerfile> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Tpinmailerfile> cmpr) {
		return null;
	}

}

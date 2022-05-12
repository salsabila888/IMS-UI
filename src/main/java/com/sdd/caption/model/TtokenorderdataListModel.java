package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.TtokenorderdataDAO;
import com.sdd.caption.domain.Ttokenorderdata;




public class TtokenorderdataListModel extends AbstractPagingListModel<Ttokenorderdata> implements Sortable<Ttokenorderdata> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Ttokenorderdata> oList;  

	public TtokenorderdataListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Ttokenorderdata> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TtokenorderdataDAO oDao = new TtokenorderdataDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TtokenorderdataDAO oDao = new TtokenorderdataDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Ttokenorderdata> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Ttokenorderdata> cmpr) {
		return null;
	}
}

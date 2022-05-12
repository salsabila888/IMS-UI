package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.McouriervendorbranchDAO;
import com.sdd.caption.domain.Mcouriervendorbranch;

public class McouriervendorbranchListModel extends AbstractPagingListModel<Mcouriervendorbranch> implements Sortable<Mcouriervendorbranch> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	List<Mcouriervendorbranch> oList;  

	public McouriervendorbranchListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Mcouriervendorbranch> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		McouriervendorbranchDAO oDao = new McouriervendorbranchDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		McouriervendorbranchDAO oDao = new McouriervendorbranchDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Mcouriervendorbranch> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<Mcouriervendorbranch> cmpr) {
		return null;
	}
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.McourierDAO;
import com.sdd.caption.domain.Mcourier;

public class McourierListModel extends AbstractPagingListModel<Mcourier>
		implements Sortable<Mcourier> {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private int _size = -1;
	List<Mcourier> oList;

	public McourierListModel(int startPageNumber, int pageSize, String filter,
			String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}

	@Override
	protected List<Mcourier> getPageData(int itemStartNumber, int pageSize,
			String filter, String orderby) {
		McourierDAO oDao = new McourierDAO();
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		McourierDAO oDao = new McourierDAO();
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Mcourier> cmpr, boolean ascending) {
		Collections.sort(oList, cmpr);
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);

	}

	@Override
	public String getSortDirection(Comparator<Mcourier> cmpr) {
		return null;
	}
}

package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.MpendingreasonDAO;
import com.sdd.caption.domain.Mpendingreason;


public class MpendingreasonListModel extends AbstractPagingListModel<Mpendingreason> implements Sortable<Mpendingreason> {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private int _size = -1;
	List<Mpendingreason> oList;

	public MpendingreasonListModel(int startPageNumber, int pageSize, String filter,
			String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}

	@Override
	protected List<Mpendingreason> getPageData(int itemStartNumber, int pageSize,
			String filter, String orderby) {
		MpendingreasonDAO oDao = new MpendingreasonDAO();
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MpendingreasonDAO oDao = new MpendingreasonDAO();
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Mpendingreason> cmpr, boolean ascending) {
		Collections.sort(oList, cmpr);
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);

	}

	@Override
	public String getSortDirection(Comparator<Mpendingreason> cmpr) {
		return null;
	}
}

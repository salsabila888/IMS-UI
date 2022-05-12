package com.sdd.caption.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.caption.dao.MreturnreasonDAO;
import com.sdd.caption.domain.Mreturnreason;


public class MreturnreasonListModel extends AbstractPagingListModel<Mreturnreason> implements Sortable<Mreturnreason> {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private int _size = -1;
	List<Mreturnreason> oList;

	public MreturnreasonListModel(int startPageNumber, int pageSize, String filter,
			String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}

	@Override
	protected List<Mreturnreason> getPageData(int itemStartNumber, int pageSize,
			String filter, String orderby) {
		MreturnreasonDAO oDao = new MreturnreasonDAO();
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MreturnreasonDAO oDao = new MreturnreasonDAO();
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<Mreturnreason> cmpr, boolean ascending) {
		Collections.sort(oList, cmpr);
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);

	}

	@Override
	public String getSortDirection(Comparator<Mreturnreason> cmpr) {
		return null;
	}
}

package com.sdd.caption.pojo;

import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mproducttype;

public class InquiryOrder {
	
	private String itemno;
	private Mbranch mbranch;
	private Mproducttype mproducttype;
	private String status;
	
	public InquiryOrder() {
		
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public Mproducttype getMproducttype() {
		return mproducttype;
	}

	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

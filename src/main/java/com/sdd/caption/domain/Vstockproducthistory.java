package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Vstockproducthistory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String producttype;
	private String trxtype;
	private Date trxtime;
	private Integer itemqty;
	private String memo;
	
	@Id
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	@Id
	public String getTrxtype() {
		return trxtype;
	}
	public void setTrxtype(String trxtype) {
		this.trxtype = trxtype;
	}
	@Id
	@Temporal(TemporalType.TIMESTAMP)	
	public Date getTrxtime() {
		return trxtime;
	}
	public void setTrxtime(Date trxtime) {
		this.trxtime = trxtime;
	}
	@Id
	public Integer getItemqty() {
		return itemqty;
	}
	public void setItemqty(Integer itemqty) {
		this.itemqty = itemqty;
	}
	@Id
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	

}

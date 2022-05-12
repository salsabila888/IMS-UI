package com.sdd.caption.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Vproductprod {
	
	private String productcode;
	private Integer totaldata;
	private Date orderdate;
	
	@Id
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

}

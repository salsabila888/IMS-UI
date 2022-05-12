package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Vembossbranch implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer mbranchfk;
	private String producttype;
	private String productcode;
	private String productname;
	private Date orderdate;
	private Integer total;
	private String status;
	
	@Id
	public Integer getMbranchfk() {
		return mbranchfk;
	}
	public void setMbranchfk(Integer mbranchfk) {
		this.mbranchfk = mbranchfk;
	}
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@Id
	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

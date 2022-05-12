package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vderivatifgenerate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer tderivatifpk;
	private String orderno;
	private String branchname;
	private Date orderdate;	
	private Date batchtime;	
	private Integer totaldata;
	private Integer totalorder;
	private Integer batchno;
	private String isgenerate;
	
	
	@Id
	public Integer getTderivatifpk() {
		return tderivatifpk;
	}
	public void setTderivatifpk(Integer tderivatifpk) {
		this.tderivatifpk = tderivatifpk;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public Date getBatchtime() {
		return batchtime;
	}
	public void setBatchtime(Date batchtime) {
		this.batchtime = batchtime;
	}
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	
	public Integer getTotalorder() {
		return totalorder;
	}
	public void setTotalorder(Integer totalorder) {
		this.totalorder = totalorder;
	}
	@Id
	public Integer getBatchno() {
		return batchno;
	}
	public void setBatchno(Integer batchno) {
		this.batchno = batchno;
	}
	
	public String getIsgenerate() {
		return isgenerate;
	}
	public void setIsgenerate(String isgenerate) {
		this.isgenerate = isgenerate;
	}
}

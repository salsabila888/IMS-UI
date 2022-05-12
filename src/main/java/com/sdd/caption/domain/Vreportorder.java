package com.sdd.caption.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Vreportorder {
	
	private Integer tembossbranchpk;
	private String org;
	private String productcode;
	private String productname;
	private Date orderdate;
	private String regioncode;
	private String regionname;
	private String branchid;
	private String branchname;
	private Integer totaldata;
	private String status;
	
	@Id
	public Integer getTembossbranchpk() {
		return tembossbranchpk;
	}
	public void setTembossbranchpk(Integer tembossbranchpk) {
		this.tembossbranchpk = tembossbranchpk;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
	
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}

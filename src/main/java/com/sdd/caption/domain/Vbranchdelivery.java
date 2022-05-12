package com.sdd.caption.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vbranchdelivery {
	
	/**
	 * 
	 */
	private String branchid;
	private String branchcode;
	private String branchname;
	private Date orderdate;
	private Integer total;
		
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}

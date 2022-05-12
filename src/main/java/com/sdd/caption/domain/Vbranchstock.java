package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vbranchstock {

	private Integer mbranchpk;
	private String branchid;
	private String branchname;
	private Integer totalinstant;
	private Integer totalnotinstant;
	
	@Id
	public Integer getMbranchpk() {
		return mbranchpk;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return branchid;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return branchname;
	}
	
	public Integer getTotalinstant() {
		return totalinstant;
	}
	public Integer getTotalnotinstant() {
		return totalnotinstant;
	}
	public void setMbranchpk(Integer mbranchpk) {
		this.mbranchpk = mbranchpk;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
	public void setTotalinstant(Integer totalinstant) {
		this.totalinstant = totalinstant;
	}
	public void setTotalnotinstant(Integer totalnotinstant) {
		this.totalnotinstant = totalnotinstant;
	}
	
	
}

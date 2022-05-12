package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vgroupbybranch {

	private Integer mbranchpk;
	private String branchname;
	private Integer total;
	
	@Id
	public Integer getMbranchpk() {
		return mbranchpk;
	}
	public void setMbranchpk(Integer mbranchpk) {
		this.mbranchpk = mbranchpk;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}

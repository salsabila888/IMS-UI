package com.sdd.caption.domain;

import org.hibernate.annotations.Type;

public class Vgroupbybranchdata {
	
	private Integer mbranchpk;	
	private String branchname;	
	private Integer totalperso;
	private Integer totaldeliv;
		
	public Integer getMbranchpk() {
		return mbranchpk;
	}
	public void setMbranchpk(Integer mbranchpk) {
		this.mbranchpk = mbranchpk;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public Integer getTotalperso() {
		return totalperso;
	}
	public void setTotalperso(Integer totalperso) {
		this.totalperso = totalperso;
	}
	public Integer getTotaldeliv() {
		return totaldeliv;
	}
	public void setTotaldeliv(Integer totaldeliv) {
		this.totaldeliv = totaldeliv;
	}
	
}

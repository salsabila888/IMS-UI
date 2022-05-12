package com.sdd.caption.domain;

import org.hibernate.annotations.Type;

public class Vpersodelivdata {
	
	private String org;	
	private String description;	
	private Integer totalperso;
	private Integer totaldeliv;
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

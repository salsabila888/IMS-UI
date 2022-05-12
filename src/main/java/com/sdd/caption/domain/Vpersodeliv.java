package com.sdd.caption.domain;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Vpersodeliv {
	
	private String org;	
	private String description;
	private Integer totaldata;
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	
}

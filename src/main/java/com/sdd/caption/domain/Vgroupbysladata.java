package com.sdd.caption.domain;

import org.hibernate.annotations.Type;

public class Vgroupbysladata {
	
	private String org;	
	private String description;	
	private Integer totalsla1;
	private Integer totalsla2;
	private Integer totalsla3;
	private Integer totalsla4;
		
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
	public Integer getTotalsla1() {
		return totalsla1;
	}
	public void setTotalsla1(Integer totalsla1) {
		this.totalsla1 = totalsla1;
	}
	public Integer getTotalsla2() {
		return totalsla2;
	}
	public void setTotalsla2(Integer totalsla2) {
		this.totalsla2 = totalsla2;
	}
	public Integer getTotalsla3() {
		return totalsla3;
	}
	public void setTotalsla3(Integer totalsla3) {
		this.totalsla3 = totalsla3;
	}
	public Integer getTotalsla4() {
		return totalsla4;
	}
	public void setTotalsla4(Integer totalsla4) {
		this.totalsla4 = totalsla4;
	}
	
	
}

package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Vgroupbysla implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String org;	
	private String description;
	private Integer sla;
	private Integer total;
	
	@Id
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
	@Id
	public Integer getSla() {
		return sla;
	}
	public void setSla(Integer sla) {
		this.sla = sla;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}

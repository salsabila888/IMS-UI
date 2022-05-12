package com.sdd.caption.domain;


import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Vdeliverytotal {
	
	private String id;
	private Integer totaldata;
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

}

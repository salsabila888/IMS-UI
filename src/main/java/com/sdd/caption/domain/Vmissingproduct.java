package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vmissingproduct implements Serializable {
	private static final long serialVersionUID = 1;
	private String productcode;
	private String isinstant;
	private Integer totaldata;
	
	public Vmissingproduct() {
		
	}

	@Id
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Id
	public String getIsinstant() {
		return isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	@Id
	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	
}

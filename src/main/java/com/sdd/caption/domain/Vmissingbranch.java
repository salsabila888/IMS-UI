package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vmissingbranch implements Serializable {
	private static final long serialVersionUID = 1;
	private String branchid;
	private Integer totaldata;
	
	public Vmissingbranch() {
		
	}

	@Id
	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	
	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
}

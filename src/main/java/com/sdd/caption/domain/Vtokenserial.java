package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vtokenserial implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer totaldata;
	private Integer outstanding;
	private Integer injected;
	private Integer outproduksi;
	
	@Id
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	
	@Id
	public Integer getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(Integer outstanding) {
		this.outstanding = outstanding;
	}
	
	@Id
	public Integer getInjected() {
		return injected;
	}
	public void setInjected(Integer injected) {
		this.injected = injected;
	}
	
	@Id
	public Integer getOutproduksi() {
		return outproduksi;
	}
	public void setOutproduksi(Integer outproduksi) {
		this.outproduksi = outproduksi;
	}
}

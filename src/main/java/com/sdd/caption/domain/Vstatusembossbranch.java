package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vstatusembossbranch implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer pendingproduksi;
	private Integer prosesproduksi;
	private Integer pendingpaket;
	private Integer prosespaket;
	private Integer pendingdelivery;
	private Integer prosesdelivery;
	private Integer delivered;
	
	@Id
	public Integer getPendingproduksi() {
		return pendingproduksi;
	}
	public void setPendingproduksi(Integer pendingproduksi) {
		this.pendingproduksi = pendingproduksi;
	}
	
	@Id
	public Integer getProsesproduksi() {
		return prosesproduksi;
	}
	public void setProsesproduksi(Integer prosesproduksi) {
		this.prosesproduksi = prosesproduksi;
	}
	
	@Id
	public Integer getPendingpaket() {
		return pendingpaket;
	}
	public void setPendingpaket(Integer pendingpaket) {
		this.pendingpaket = pendingpaket;
	}
	
	@Id
	public Integer getProsespaket() {
		return prosespaket;
	}
	public void setProsespaket(Integer prosespaket) {
		this.prosespaket = prosespaket;
	}
	
	@Id
	public Integer getPendingdelivery() {
		return pendingdelivery;
	}
	public void setPendingdelivery(Integer pendingdelivery) {
		this.pendingdelivery = pendingdelivery;
	}
	
	@Id
	public Integer getProsesdelivery() {
		return prosesdelivery;
	}
	public void setProsesdelivery(Integer prosesdelivery) {
		this.prosesdelivery = prosesdelivery;
	}
	
	@Id
	public Integer getDelivered() {
		return delivered;
	}
	public void setDelivered(Integer delivered) {
		this.delivered = delivered;
	}

}

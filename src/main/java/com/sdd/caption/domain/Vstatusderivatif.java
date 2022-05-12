package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vstatusderivatif implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer scan;
	private Integer crop;
	private Integer merging;
	private Integer perso;
	private Integer paket;
	private Integer delivery;
	
	@Id
	public Integer getScan() {
		return scan;
	}
	public void setScan(Integer scan) {
		this.scan = scan;
	}
	
	@Id
	public Integer getCrop() {
		return crop;
	}
	public void setCrop(Integer crop) {
		this.crop = crop;
	}
	
	@Id
	public Integer getMerging() {
		return merging;
	}
	public void setMerging(Integer merging) {
		this.merging = merging;
	}
	
	@Id
	public Integer getPerso() {
		return perso;
	}
	public void setPerso(Integer perso) {
		this.perso = perso;
	}
	
	@Id
	public Integer getPaket() {
		return paket;
	}
	public void setPaket(Integer paket) {
		this.paket = paket;
	}
	
	@Id
	public Integer getDelivery() {
		return delivery;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

}

package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vreportdaily {
	
	private Integer id;
	private String productorg;
	private String producttype;
	private String productcode;
	private String productname;
	private Integer total;
	private Integer perso;
	private Integer pending;
	private Integer delivery;
	
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer  id) {
		this.id = id;
	}
	public String getProductorg() {
		return productorg;
	}
	public void setProductorg(String productorg) {
		this.productorg = productorg;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPerso() {
		return perso;
	}
	public void setPerso(Integer perso) {
		this.perso = perso;
	}	
	public Integer getPending() {
		return pending;
	}
	public void setPending(Integer pending) {
		this.pending = pending;
	}
	public Integer getDelivery() {
		return delivery;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}
	
	

}

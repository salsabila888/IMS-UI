package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vproducttypestock {

	private Integer mproducttypepk;
	private String producttype;
	private Integer totalincoming;
	private Integer totaloutgoing;
	private Integer totalstock;
	
	@Id
	public Integer getMproducttypepk() {
		return mproducttypepk;
	}
	public void setMproducttypepk(Integer mproducttypepk) {
		this.mproducttypepk = mproducttypepk;
	}	
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public Integer getTotalincoming() {
		return totalincoming;
	}	
	public void setTotalincoming(Integer totalincoming) {
		this.totalincoming = totalincoming;
	}
	public Integer getTotaloutgoing() {
		return totaloutgoing;
	}
	public void setTotaloutgoing(Integer totaloutgoing) {
		this.totaloutgoing = totaloutgoing;
	}
	public Integer getTotalstock() {
		return totalstock;
	}
	public void setTotalstock(Integer totalstock) {
		this.totalstock = totalstock;
	}
	
	
}

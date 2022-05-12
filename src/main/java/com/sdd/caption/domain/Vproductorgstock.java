package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vproductorgstock {

	private String productorg;
	private Integer totalincoming;
	private Integer totaloutgoing;
	private Integer totalstock;
	
	@Id
	public String getProductorg() {
		return productorg;
	}
	public void setProductorg(String productorg) {
		this.productorg = productorg;
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

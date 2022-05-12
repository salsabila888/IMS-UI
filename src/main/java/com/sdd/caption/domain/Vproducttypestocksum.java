package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vproducttypestocksum {

	private Integer totalstock;
	private Integer totalincoming;
	private Integer totaloutgoing;	
	
	@Id
	public Integer getTotalstock() {
		return totalstock;
	}
	public void setTotalstock(Integer totalstock) {
		this.totalstock = totalstock;
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
	
}

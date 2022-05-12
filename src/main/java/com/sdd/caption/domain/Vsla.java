package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vsla {

	private String id;
	private Integer sla;
	private Integer total;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSla() {
		return sla;
	}
	public void setSla(Integer sla) {
		this.sla = sla;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}	
		
}

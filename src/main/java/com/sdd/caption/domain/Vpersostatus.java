package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vpersostatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer year;
	private Integer month;
	private String status;
	private Integer count;
	private Integer sum;
	
	@Id
	public Integer getYear() {
		return year;
	}
	@Id
	public Integer getMonth() {
		return month;
	}
	@Id
	public String getStatus() {
		return status;
	}
	public Integer getCount() {
		return count;
	}
	public Integer getSum() {
		return sum;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}	
	
}

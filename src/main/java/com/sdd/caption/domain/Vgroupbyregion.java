package com.sdd.caption.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vgroupbyregion {

	private Integer mregionpk;
	private String regionname;
	private Integer total;
	
	@Id
	public Integer getMregionpk() {
		return mregionpk;
	}
	public void setMregionpk(Integer mregionpk) {
		this.mregionpk = mregionpk;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}

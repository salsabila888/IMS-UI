package com.sdd.caption.domain;

import org.hibernate.annotations.Type;

public class Vgroupbyregiondata {
	
	private Integer mregionpk;	
	private String regionname;	
	private Integer totalperso;
	private Integer totaldeliv;
		
	public Integer getMregionpk() {
		return mregionpk;
	}
	public void setMregionpk(Integer mregionpk) {
		this.mregionpk = mregionpk;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public Integer getTotalperso() {
		return totalperso;
	}
	public void setTotalperso(Integer totalperso) {
		this.totalperso = totalperso;
	}
	public Integer getTotaldeliv() {
		return totaldeliv;
	}
	public void setTotaldeliv(Integer totaldeliv) {
		this.totaldeliv = totaldeliv;
	}
	
}

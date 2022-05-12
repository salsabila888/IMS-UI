package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vnotif implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer musergrouppk;
	private Integer mmenupk;
	private Integer totalnotif;
	private String notiftxt;
	
	@Id
	public Integer getMusergrouppk() {
		return musergrouppk;
	}
	public void setMusergrouppk(Integer musergrouppk) {
		this.musergrouppk = musergrouppk;
	}
	
	@Id
	public Integer getMmenupk() {
		return mmenupk;
	}
	public void setMmenupk(Integer mmenupk) {
		this.mmenupk = mmenupk;
	}
	
	@Id
	public Integer getTotalnotif() {
		return totalnotif;
	}
	public void setTotalnotif(Integer totalnotif) {
		this.totalnotif = totalnotif;
	}
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getNotiftxt() {
		return notiftxt;
	}
	public void setNotiftxt(String notiftxt) {
		this.notiftxt = notiftxt;
	}

}

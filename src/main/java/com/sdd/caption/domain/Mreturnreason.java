package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the mreturnreason database table.
 * 
 */
@Entity
@Table(name="mreturnreason")
@NamedQuery(name="Mreturnreason.findAll", query="SELECT m FROM Mreturnreason m")
public class Mreturnreason implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mreturnreasonpk;
	private Date lastupdated;
	private String returnreason;
	private String isDestroy;
	private String updatedby;

	public Mreturnreason() {
	}


	@Id
	@SequenceGenerator(name="MRETURNREASON_MRETURNREASONPK_GENERATOR", sequenceName = "MRETURNREASON_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MRETURNREASON_MRETURNREASONPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getMreturnreasonpk() {
		return this.mreturnreasonpk;
	}

	public void setMreturnreasonpk(Integer mreturnreasonpk) {
		this.mreturnreasonpk = mreturnreasonpk;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getReturnreason() {
		return this.returnreason;
	}

	public void setReturnreason(String returnreason) {
		this.returnreason = returnreason;
	}


	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsDestroy() {
		return isDestroy;
	}


	public void setIsDestroy(String isDestroy) {
		this.isDestroy = isDestroy;
	}

}
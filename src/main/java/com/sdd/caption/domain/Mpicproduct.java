package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the mpicproduct database table.
 * 
 */
@Entity
@Table(name="mpicproduct")
@NamedQuery(name="Mpicproduct.findAll", query="SELECT m FROM Mpicproduct m")
public class Mpicproduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mpicproductpk;
	private Date lastupdated;
	private String picemail;
	private String pichp;
	private String picname;
	private String productgroup;
	private String updatedby;

	public Mpicproduct() {
	}


	@Id
	@SequenceGenerator(name="MPICPRODUCT_MPICPRODUCTPK_GENERATOR", sequenceName = "MPICPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MPICPRODUCT_MPICPRODUCTPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getMpicproductpk() {
		return this.mpicproductpk;
	}

	public void setMpicproductpk(Integer mpicproductpk) {
		this.mpicproductpk = mpicproductpk;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimLowerCaseUserType")
	public String getPicemail() {
		return this.picemail;
	}

	public void setPicemail(String picemail) {
		this.picemail = picemail;
	}


	@Column(length=40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPichp() {
		return this.pichp;
	}

	public void setPichp(String pichp) {
		this.pichp = pichp;
	}


	@Column(length=40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPicname() {
		return this.picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}


	@Column(length=3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}


	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}
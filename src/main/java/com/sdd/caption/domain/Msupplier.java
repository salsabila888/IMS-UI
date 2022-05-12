package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "msupplier")
@NamedQuery(name = "Msupplier.findAll", query = "SELECT t FROM Msupplier t")
public class Msupplier implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer msupplierpk;
	private String suppliername;
	private String picname;
	private String pichp;
	private String picemail;
	private String updatedby;
	private Date lastupdated;

	public Msupplier() {

	}

	@Id
	@SequenceGenerator(name = "MSUPPLIER_MSUPPLIERPK_GENERATOR", sequenceName = "MSUPPLIER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSUPPLIER_MSUPPLIERPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMsupplierpk() {
		return msupplierpk;
	}

	public void setMsupplierpk(Integer msupplierpk) {
		this.msupplierpk = msupplierpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getPichp() {
		return pichp;
	}

	public void setPichp(String pichp) {
		this.pichp = pichp;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getPicemail() {
		return picemail;
	}

	public void setPicemail(String picemail) {
		this.picemail = picemail;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
}

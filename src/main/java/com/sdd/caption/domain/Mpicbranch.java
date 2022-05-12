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
@Table(name="mpicbranch")
@NamedQuery(name="Mpicbranch.findAll", query="SELECT m FROM Mpicbranch m")
public class Mpicbranch implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mpicbranchpk;
	private Date lastupdated;
	private String picemail;
	private String picphone;
	private String picname;
	private String updatedby;
	private Mbranch mbranch;

	public Mpicbranch() {
	}


	@Id
	@SequenceGenerator(name="MPICBRANCH_MPICBRANCHPK_GENERATOR", sequenceName = "MPICBRANCH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MPICBRANCH_MPICBRANCHPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getMpicbranchpk() {
		return this.mpicbranchpk;
	}

	public void setMpicbranchpk(Integer mpicbranchpk) {
		this.mpicbranchpk = mpicbranchpk;
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
	public String getPicphone() {
		return this.picphone;
	}

	public void setPicphone(String picphone) {
		this.picphone = picphone;
	}


	@Column(length=40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPicname() {
		return this.picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

}
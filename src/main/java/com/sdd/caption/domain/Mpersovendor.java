package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the mpersovendor database table.
 * 
 */
@Entity
@Table(name="mpersovendor")
@NamedQuery(name="Mpersovendor.findAll", query="SELECT m FROM Mpersovendor m")
public class Mpersovendor implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mpersovendorpk;
	private Date lastupdated;
	private String updatedby;
	private String vendorcode;
	private String vendorname;
	private String vendorpicemail;
	private String vendorpicname;
	private String vendorpicphone;
	private String vendorlogo;

	public Mpersovendor() {
	}


	@Id
	@SequenceGenerator(name="MPERSOVENDOR_MPERSOVENDORPK_GENERATOR", sequenceName = "MPERSOVENDOR_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MPERSOVENDOR_MPERSOVENDORPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getMpersovendorpk() {
		return this.mpersovendorpk;
	}

	public void setMpersovendorpk(Integer mpersovendorpk) {
		this.mpersovendorpk = mpersovendorpk;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	@Column(length=10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorcode() {
		return this.vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}


	@Column(length=70)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorname() {
		return this.vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimLowerCaseUserType")
	public String getVendorpicemail() {
		return this.vendorpicemail;
	}

	public void setVendorpicemail(String vendorpicemail) {
		this.vendorpicemail = vendorpicemail;
	}


	@Column(length=40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorpicname() {
		return this.vendorpicname;
	}
	public void setVendorpicname(String vendorpicname) {
		this.vendorpicname = vendorpicname;
	}

	@Column(length=20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorpicphone() {
		return this.vendorpicphone;
	}

	public void setVendorpicphone(String vendorpicphone) {
		this.vendorpicphone = vendorpicphone;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getVendorlogo() {
		return vendorlogo;
	}


	public void setVendorlogo(String vendorlogo) {
		this.vendorlogo = vendorlogo;
	}

}
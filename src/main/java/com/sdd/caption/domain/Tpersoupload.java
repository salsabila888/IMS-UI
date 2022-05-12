package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "Tpersoupload")
@NamedQuery(name = "Tpersoupload.findAll", query = "SELECT t FROM Tpersoupload t")
public class Tpersoupload implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer tpersouploadpk;
	private String filename;
	private Integer totaldata;
	private Date uploadtime;
	private String uploadedby;
	
	public Tpersoupload() {
	}

	@Id
	@SequenceGenerator(name = "TPERSOUPLOAD_TPERSOUPLOADPK_GENERATOR", sequenceName = "TPERSOUPLOAD_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPERSOUPLOAD_TPERSOUPLOADPK_GENERATOR")
	public Integer getTpersouploadpk() {
		return tpersouploadpk;
	}

	public void setTpersouploadpk(Integer tpersouploadpk) {
		this.tpersouploadpk = tpersouploadpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUploadedby() {
		return uploadedby;
	}

	public void setUploadedby(String uploadedby) {
		this.uploadedby = uploadedby;
	}
}

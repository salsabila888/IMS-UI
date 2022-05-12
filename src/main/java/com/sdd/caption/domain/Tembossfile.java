package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tembossfile database table.
 * 
 */
@Entity
@Table(name = "tembossfile")
@NamedQuery(name="Tembossfile.findAll", query="SELECT t FROM Tembossfile t")
public class Tembossfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tembossfilepk;
	private String filename;
	private String memo;
	private String embossid;
	private String uploadedby;
	private Date uploadtime;
	private Integer totaldata;
	

	public Tembossfile() {
	}

	@Id
	@SequenceGenerator(name = "TEMBOSSFILE_TEMBOSSFILEPK_GENERATOR", sequenceName = "TEMBOSSFILE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMBOSSFILE_TEMBOSSFILEPK_GENERATOR")
	public Integer getTembossfilepk() {
		return this.tembossfilepk;
	}

	public void setTembossfilepk(Integer tembossfilepk) {
		this.tembossfilepk = tembossfilepk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
		

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getEmbossid() {
		return this.embossid;
	}

	public void setEmbossid(String embossid) {
		this.embossid = embossid;
	}

	public String getUploadedby() {
		return uploadedby;
	}

	public void setUploadedby(String uploadedby) {
		this.uploadedby = uploadedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}
	
	
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}


}
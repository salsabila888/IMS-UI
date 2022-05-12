package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tpinmailerfile database table.
 * 
 */
@Entity
@Table(name = "tpinmailerfile")
@NamedQuery(name="Tpinmailerfile.findAll", query="SELECT t FROM Tpinmailerfile t")
public class Tpinmailerfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinmailerfilepk;
	private String batchid;
	private String filename;
	private String memo;
	private Integer totaldata;
	private String status;
	private String uploadedby;
	private Date uploadtime;
	private Mproduct mproduct;

	public Tpinmailerfile() {
	}

	@Id
	@SequenceGenerator(name = "TPINMAILERFILE_TPINMAILERFILEPK_GENERATOR", sequenceName = "TPINMAILERFILE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINMAILERFILE_TPINMAILERFILEPK_GENERATOR")
	public Integer getTpinmailerfilepk() {
		return this.tpinmailerfilepk;
	}

	public void setTpinmailerfilepk(Integer tpinmailerfilepk) {
		this.tpinmailerfilepk = tpinmailerfilepk;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
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
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
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

	@ManyToOne
	@JoinColumn(name = "mproductfk")	
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

}
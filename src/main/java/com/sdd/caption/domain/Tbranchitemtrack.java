package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="tbranchitemtrack")
@NamedQuery(name="Tbranchitemtrack.findAll", query="SELECT t FROM Tbranchitemtrack t")
public class Tbranchitemtrack implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tbranchitemtrackpk;
	private String productgroup;
	private String itemno;
	private Date tracktime;
	private String trackstatus;
	private String trackdesc;
	private Mproduct mproduct;
	
	public Tbranchitemtrack() {
		
	}

	@Id
	@SequenceGenerator(name="TBRANCHITEMTRACK_TBRANCHITEMTRACKPK_GENERATOR", sequenceName = "TBRANCHITEMTRACK_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBRANCHITEMTRACK_TBRANCHITEMTRACKPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTbranchitemtrackpk() {
		return tbranchitemtrackpk;
	}

	public void setTbranchitemtrackpk(Integer tbranchitemtrackpk) {
		this.tbranchitemtrackpk = tbranchitemtrackpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getTracktime() {
		return tracktime;
	}

	public void setTracktime(Date tracktime) {
		this.tracktime = tracktime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTrackstatus() {
		return trackstatus;
	}

	public void setTrackstatus(String trackstatus) {
		this.trackstatus = trackstatus;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTrackdesc() {
		return trackdesc;
	}

	public void setTrackdesc(String trackdesc) {
		this.trackdesc = trackdesc;
	}

	@ManyToOne
	@JoinColumn(name="mproductfk", nullable=false)
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}
	
	
}

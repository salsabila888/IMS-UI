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
@Table(name = "trepair")
@NamedQuery(name = "Trepair.findAll", query = "SELECT t FROM Trepair t")
public class Trepair implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer trepairpk;
	private String regid;
	private Integer itemqty;
	private String status;
	private String insertedby;
	private Date inserttime;
	private String decisionby;
	private Date decisiontime;
	private Mproduct mproduct;
	private Mbranch mbranch;
	private Toutgoing toutgoing;

	public Trepair() {
	}

	@Id
	@SequenceGenerator(name = "TREPAIR_TREPAIRPK_GENERATOR", sequenceName = "TREPAIR_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TREPAIR_TREPAIRPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTrepairpk() {
		return trepairpk;
	}

	public void setTrepairpk(Integer trepairpk) {
		this.trepairpk = trepairpk;
	}

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public Integer getItemqty() {
		return itemqty;
	}

	public void setItemqty(Integer itemqty) {
		this.itemqty = itemqty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 15)
	public String getInsertedby() {
		return insertedby;
	}

	public void setInsertedby(String insertedby) {
		this.insertedby = insertedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}

	public String getDecisionby() {
		return decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	public Date getDecisiontime() {
		return decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	// bi-directional many-to-one association to Mproduct
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	// bi-directional many-to-one association to Mbranch
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

}

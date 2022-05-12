package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

@Entity
@Table(name="treturn")
@NamedQuery(name="Treturn.findAll", query="SELECT t FROM Treturn t")
public class Treturn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer treturnpk;
	private String regid;
	private Integer returnlevel;
	private Integer itemqty;
	private String status;
	private String lettertype;
	private String insertedby;
	private Date inserttime;
	private String decisionby;
	private Date decisiontime;
	private Mproduct mproduct;
	private Mbranch mbranch;
	private Mreturnreason mreturnreason;

	public Treturn() {
	}


	@Id
	@SequenceGenerator(name="TRETURN_TRETURNPK_GENERATOR", sequenceName = "TRETURN_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRETURN_TRETURNPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTreturnpk() {
		return this.treturnpk;
	}

	public void setTreturnpk(Integer treturnpk) {
		this.treturnpk = treturnpk;
	}


	@Column(length=20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegid() {
		return this.regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public Integer getReturnlevel() {
		return this.returnlevel;
	}

	public void setReturnlevel(Integer returnlevel) {
		this.returnlevel = returnlevel;
	}
	
	public Integer getItemqty() {
		return this.itemqty;
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

	public String getLettertype() {
		return lettertype;
	}


	public void setLettertype(String lettertype) {
		this.lettertype = lettertype;
	}


	@Column(length=15)
	public String getInsertedby() {
		return this.insertedby;
	}

	public void setInsertedby(String insertedby) {
		this.insertedby = insertedby;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getInserttime() {
		return this.inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	
	@Column(length=15)
	public String getDecisionby() {
		return decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return decisiontime;
	}


	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	//bi-directional many-to-one association to Mproduct
	@ManyToOne
	@JoinColumn(name="mproductfk")
	public Mproduct getMproduct() {
		return this.mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	//bi-directional many-to-one association to Mbranch
	@ManyToOne
	@JoinColumn(name="mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}


	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	@ManyToOne
	@JoinColumn(name="mreturnreasonfk")
	public Mreturnreason getMreturnreason() {
		return mreturnreason;
	}


	public void setMreturnreason(Mreturnreason mreturnreason) {
		this.mreturnreason = mreturnreason;
	}
	

}
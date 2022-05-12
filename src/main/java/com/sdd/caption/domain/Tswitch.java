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
@Table(name = "tswitch")
@NamedQuery(name = "Tswitch.findAll", query = "SELECT t FROM Tswitch t")
public class Tswitch implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tswitchpk;
	private String branchidreq;
	private String outletreq;
	private String branchidpool;
	private String outletpool;
	private String regid;
	private Integer itemqty;
	private String status;
	private String insertedby;
	private Date inserttime;
	private String decisionby;
	private Date decisiontime;
	private Mproduct mproduct;
	private Mbranch mbranch;
	private Torder torder;

	public Tswitch() {
	}

	@Id
	@SequenceGenerator(name = "TSWITCH_TSWITCHPK_GENERATOR", sequenceName = "TSWITCH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TSWITCH_TSWITCHPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTswitchpk() {
		return tswitchpk;
	}

	public void setTswitchpk(Integer tswitchpk) {
		this.tswitchpk = tswitchpk;
	}

	public String getBranchidreq() {
		return branchidreq;
	}

	public void setBranchidreq(String branchidreq) {
		this.branchidreq = branchidreq;
	}

	public String getOutletreq() {
		return outletreq;
	}

	public void setOutletreq(String outletreq) {
		this.outletreq = outletreq;
	}

	public String getBranchidpool() {
		return branchidpool;
	}

	public void setBranchidpool(String branchidpool) {
		this.branchidpool = branchidpool;
	}

	public String getOutletpool() {
		return outletpool;
	}

	public void setOutletpool(String outletpool) {
		this.outletpool = outletpool;
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

	@Temporal(TemporalType.TIMESTAMP)
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

	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

}

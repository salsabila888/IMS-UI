package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the toutgoing database table.
 * 
 */
@Entity
@Table(name = "toutgoing")
@NamedQuery(name = "Toutgoing.findAll", query = "SELECT t FROM Toutgoing t")
public class Toutgoing implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer toutgoingpk;
	private String decisionby;
	private String decisionmemo;
	private Date decisiontime;
	private String entryby;
	private Date entrytime;
	private String isverified;
	private Integer itemqty;
	private Date lastupdated;
	private String memo;
	private String outgoingid;
	private String productgroup;
	private String status;
	private String updatedby;
	private Torder torder;
	private Tperso tperso;
	private Mproduct mproduct;
	private Trepair trepair;

	public Toutgoing() {
	}

	@Id
	@SequenceGenerator(name = "TOUTGOING_TOUTGOINGPK_GENERATOR", sequenceName = "TOUTGOING_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOUTGOING_TOUTGOINGPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getToutgoingpk() {
		return this.toutgoingpk;
	}

	public void setToutgoingpk(Integer toutgoingpk) {
		this.toutgoingpk = toutgoingpk;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDecisionby() {
		return this.decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDecisionmemo() {
		return decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return this.decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return this.entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	public String getIsverified() {
		return isverified;
	}

	public void setIsverified(String isverified) {
		this.isverified = isverified;
	}

	public Integer getItemqty() {
		return this.itemqty;
	}

	public void setItemqty(Integer itemqty) {
		this.itemqty = itemqty;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOutgoingid() {
		return outgoingid;
	}

	public void setOutgoingid(String outgoingid) {
		this.outgoingid = outgoingid;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	// bi-directional many-to-one association to Mproduct
	@ManyToOne
	@JoinColumn(name = "mproductfk", nullable = false)
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

	// bi-directional many-to-one association to Tperso
	@ManyToOne
	@JoinColumn(name = "tpersofk")
	public Tperso getTperso() {
		return tperso;
	}

	public void setTperso(Tperso tperso) {
		this.tperso = tperso;
	}

	// bi-directional many-to-one association to Toutgoing
	@ManyToOne
	@JoinColumn(name = "trepairfk")
	public Trepair getTrepair() {
		return trepair;
	}

	public void setTrepair(Trepair trepair) {
		this.trepair = trepair;
	}

}
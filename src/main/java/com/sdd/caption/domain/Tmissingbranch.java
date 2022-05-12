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
@Table(name = "tmissingbranch")
@NamedQuery(name = "Tmissingbranch.findAll", query = "SELECT m FROM Tmissingbranch m")
public class Tmissingbranch implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer tmissingbranchpk;
	private String branchid;
	private Date orderdate;
	private Date entrytime;
	private Integer totaldata;
	private Tembossbranch tembossbranch;
	private Tpinmailerbranch tpinmailerbranch;

	public Tmissingbranch() {

	}

	@Id
	@SequenceGenerator(name = "TMISSINGBRANCH_TMISSINGBRANCHPK_GENERATOR", sequenceName = "TMISSINGBRANCH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TMISSINGBRANCH_TMISSINGBRANCHPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTmissingbranchpk() {
		return tmissingbranchpk;
	}

	public void setTmissingbranchpk(Integer tmissingbranchpk) {
		this.tmissingbranchpk = tmissingbranchpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

	// bi-directional many-to-one association to Tpinmailerbranch
	@ManyToOne
	@JoinColumn(name = "tpinmailerbranchfk")
	public Tpinmailerbranch getTpinmailerbranch() {
		return tpinmailerbranch;
	}

	public void setTpinmailerbranch(Tpinmailerbranch tpinmailerbranch) {
		this.tpinmailerbranch = tpinmailerbranch;
	}

}

package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tpinmailerbranch database table.
 * 
 */
@Entity
@Table(name = "tpinmailerbranch")
@NamedQuery(name = "Tpinmailerbranch.findAll", query = "SELECT t FROM Tpinmailerbranch t")
public class Tpinmailerbranch implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinmailerbranchpk;
	private String branchid;
	private Date orderdate;
	private String status;
	private Integer totaldata;
	private Mbranch mbranch;
	private Tpinmailerfile tpinmailerfile;

	public Tpinmailerbranch() {
	}

	@Id
	@SequenceGenerator(name = "TPINMAILERBRANCH_TPINMAILERBRANCHPK_GENERATOR", sequenceName = "TPINMAILERBRANCH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINMAILERBRANCH_TPINMAILERBRANCHPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpinmailerbranchpk() {
		return this.tpinmailerbranchpk;
	}

	public void setTpinmailerbranchpk(Integer tpinmailerbranchpk) {
		this.tpinmailerbranchpk = tpinmailerbranchpk;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Column(length = 3)
	public String getStatus() {
		return this.status;
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

	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	
	@ManyToOne
	@JoinColumn(name = "tpinmailerfilefk", nullable = false)
	public Tpinmailerfile getTpinmailerfile() {
		return this.tpinmailerfile;
	}

	public void setTpinmailerfile(Tpinmailerfile tpinmailerfile) {
		this.tpinmailerfile = tpinmailerfile;
	}

	
	/*@ManyToOne
	@JoinColumn(name = "tpinmailerproductfk")
	public Tpinmailerproduct getTpinmailerproduct() {
		return tpinmailerproduct;
	}

	public void setTpinmailerproduct(Tpinmailerproduct tpinmailerproduct) {
		this.tpinmailerproduct = tpinmailerproduct;
	}*/

}
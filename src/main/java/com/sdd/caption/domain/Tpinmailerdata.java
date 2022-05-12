package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tpinmailerdata database table.
 * 
 */
@Entity
@Table(name = "tpinmailerdata")
@NamedQuery(name = "Tpinmailerdata.findAll", query = "SELECT t FROM Tpinmailerdata t")
public class Tpinmailerdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinmailerdatapk;
	private String branchid;
	private String cardno;
	private String isinstant;
	private String iswithcard;
	private String klncode;
	private String name;
	private Date orderdate;
	private String productcode;
	private String seqno;
	private Mproduct mproduct;
	private Tpinmailerbranch tpinmailerbranch;
	private Tpinmailerproduct tpinmailerproduct;

	public Tpinmailerdata() {
	}

	@Id
	@SequenceGenerator(name = "TPINMAILERDATA_TPINMAILERDATAPK_GENERATOR", sequenceName = "TPINMAILERDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINMAILERDATA_TPINMAILERDATAPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpinmailerdatapk() {
		return this.tpinmailerdatapk;
	}

	public void setTpinmailerdatapk(Integer tpinmailerdatapk) {
		this.tpinmailerdatapk = tpinmailerdatapk;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Column(length = 1)
	public String getIsinstant() {
		return this.isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	public String getIswithcard() {
		return iswithcard;
	}

	public void setIswithcard(String iswithcard) {
		this.iswithcard = iswithcard;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKlncode() {
		return this.klncode;
	}

	public void setKlncode(String klncode) {
		this.klncode = klncode;
	}

	@Column(length = 40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSeqno() {
		return this.seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	// bi-directional many-to-one association to Tpinmailerbranch
	@ManyToOne
	@JoinColumn(name = "tpinmailerbranchfk")
	public Tpinmailerbranch getTpinmailerbranch() {
		return this.tpinmailerbranch;
	}

	public void setTpinmailerbranch(Tpinmailerbranch tpinmailerbranch) {
		this.tpinmailerbranch = tpinmailerbranch;
	}

	// bi-directional many-to-one association to Tpinmailerproduct
	@ManyToOne
	@JoinColumn(name = "tpinmailerproductfk")
	public Tpinmailerproduct getTpinmailerproduct() {
		return tpinmailerproduct;
	}

	public void setTpinmailerproduct(Tpinmailerproduct tpinmailerproduct) {
		this.tpinmailerproduct = tpinmailerproduct;
	}

}
package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tpinmailerproduct database table.
 * 
 */
@Entity
@Table(name = "tpinmailerproduct")
@NamedQuery(name = "Tpinmailerproduct.findAll", query = "SELECT t FROM Tpinmailerproduct t")
public class Tpinmailerproduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinmailerproductpk;
	private String productcode;
	private Date orderdate;
	private Date entrytime;
	private String status;
	private String isinstant;
	private String org;
	private Integer orderos;
	private Integer totalproses;
	private Integer totaldata;
	private Mproduct mproduct;
	private Tpinmailerfile tpinmailerfile;
	private Tpinmailerbranch tpinmailerbranch;

	public Tpinmailerproduct() {
	}

	@Id
	@SequenceGenerator(name = "TPINMAILERPRODUCT_TPINMAILERPRODUCTPK_GENERATOR", sequenceName = "TPINMAILERPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINMAILERPRODUCT_TPINMAILERPRODUCTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpinmailerproductpk() {
		return this.tpinmailerproductpk;
	}

	public void setTpinmailerproductpk(Integer tpinmailerproductpk) {
		this.tpinmailerproductpk = tpinmailerproductpk;
	}
	

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 1)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	
	@Column(length = 10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	
	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Integer getOrderos() {
		return orderos;
	}

	public void setOrderos(Integer orderos) {
		this.orderos = orderos;
	}

	public Integer getTotalproses() {
		return totalproses;
	}

	public void setTotalproses(Integer totalproses) {
		this.totalproses = totalproses;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	
	@ManyToOne
	@JoinColumn(name = "tpinmailerfilefk", nullable = false)
	public Tpinmailerfile getTpinmailerfile() {
		return this.tpinmailerfile;
	}

	public void setTpinmailerfile(Tpinmailerfile tpinmailerfile) {
		this.tpinmailerfile = tpinmailerfile;
	}

	
	@ManyToOne
	@JoinColumn(name = "tpinmailerbranchfk")
	public Tpinmailerbranch getTpinmailerbranch() {
		return tpinmailerbranch;
	}

	public void setTpinmailerbranch(Tpinmailerbranch tpinmailerbranch) {
		this.tpinmailerbranch = tpinmailerbranch;
	}

}
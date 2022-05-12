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
@Table(name = "tmissingproduct")
@NamedQuery(name = "Tmissingproduct.findAll", query = "SELECT m FROM Tmissingproduct m")
public class Tmissingproduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer tmissingproductpk;
	private String productgroup;
	private String productcode;
	private String isinstant;
	private Integer totaldata;
	private Date entrytime;
	private Date orderdate;
	private Tembossproduct tembossproduct;
	private Tpinmailerproduct tpinmailerproduct;

	public Tmissingproduct() {

	}

	@Id
	@SequenceGenerator(name = "TMISSINGPRODUCT_TMISSINGPRODUCTPK_GENERATOR", sequenceName = "TMISSINGPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TMISSINGPRODUCT_TMISSINGPRODUCTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTmissingproductpk() {
		return tmissingproductpk;
	}

	public void setTmissingproductpk(Integer tmissingproductpk) {
		this.tmissingproductpk = tmissingproductpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
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

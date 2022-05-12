package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tderivatifproduct database table.
 * 
 */
@Entity
@Table(name = "tderivatifproduct")
@NamedQuery(name = "Tderivatifproduct.findAll", query = "SELECT t FROM Tderivatifproduct t")
public class Tderivatifproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer tderivatifproductpk;
	private Date entrytime;
	private Date orderdate;
	private String nopaket;
	private Date pakettime;
	private Integer totaldata;
	private Tderivatif tderivatif;
	private Mproduct mproduct;
	private Tembossbranch tembossbranch;

	public Tderivatifproduct() {
	}

	@Id
	@SequenceGenerator(name = "TDERIVATIFPRODUCT_TDERIVATIFPRODUCTPK_GENERATOR", sequenceName = "TDERIVATIFPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDERIVATIFPRODUCT_TDERIVATIFPRODUCTPK_GENERATOR")
	public Integer getTderivatifproductpk() {
		return this.tderivatifproductpk;
	}

	public void setTderivatifproductpk(Integer tderivatifproductpk) {
		this.tderivatifproductpk = tderivatifproductpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return this.entrytime;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNopaket() {
		return nopaket;
	}

	public void setNopaket(String nopaket) {
		this.nopaket = nopaket;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPakettime() {
		return pakettime;
	}

	public void setPakettime(Date pakettime) {
		this.pakettime = pakettime;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	// bi-directional many-to-one association to Torderdata
	@ManyToOne
	@JoinColumn(name = "tderivatiffk")
	public Tderivatif getTderivatif() {
		return tderivatif;
	}

	public void setTderivatif(Tderivatif tderivatif) {
		this.tderivatif = tderivatif;
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

	// bi-directional many-to-one association to Tembossbranch
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

}
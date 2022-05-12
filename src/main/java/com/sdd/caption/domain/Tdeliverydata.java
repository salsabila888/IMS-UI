package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tdeliverydata database table.
 * 
 */
@Entity
@Table(name = "tdeliverydata")
@NamedQuery(name = "Tdeliverydata.findAll", query = "SELECT t FROM Tdeliverydata t")
public class Tdeliverydata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tdeliverydatapk;
	private Date orderdate;
	private String productgroup;
	private Integer quantity;
	private Mproduct mproduct;
	private Tdelivery tdelivery;
	private Tpaketdata tpaketdata;

	public Tdeliverydata() {
	}

	@Id
	@SequenceGenerator(name = "TDELIVERYDATA_TDELIVERYDATAPK_GENERATOR", sequenceName = "TDELIVERYDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDELIVERYDATA_TDELIVERYDATAPK_GENERATOR")
	public Integer getTdeliverydatapk() {
		return this.tdeliverydatapk;
	}

	public void setTdeliverydatapk(Integer tdeliverydatapk) {
		this.tdeliverydatapk = tdeliverydatapk;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	// bi-directional many-to-one association to Tdelivery
	@ManyToOne
	@JoinColumn(name = "tdeliveryfk")
	public Tdelivery getTdelivery() {
		return this.tdelivery;
	}

	public void setTdelivery(Tdelivery tdelivery) {
		this.tdelivery = tdelivery;
	}

	// bi-directional many-to-one association to Tdelivery
	@ManyToOne
	@JoinColumn(name = "tpaketdatafk")
	public Tpaketdata getTpaketdata() {
		return tpaketdata;
	}

	public void setTpaketdata(Tpaketdata tpaketdata) {
		this.tpaketdata = tpaketdata;
	}

}
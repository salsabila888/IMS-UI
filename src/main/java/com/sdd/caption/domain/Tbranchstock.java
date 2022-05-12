package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the tbranchstock database table.
 * 
 */
@Entity
@Table(name="tbranchstock")
@NamedQuery(name="Tbranchstock.findAll", query="SELECT t FROM Tbranchstock t")
public class Tbranchstock implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tbranchstockpk;
	private Integer stockcabang;
	private Integer stockreserved;
	private Integer stockdelivered;
	private Integer stockactivated;
	private String productgroup;
	private String outlet;
	private Mbranch mbranch;
	private Mproduct mproduct;

	public Tbranchstock() {
	}


	@Id
	@SequenceGenerator(name="TBRANCHSTOCK_TBRANCHSTOCKPK_GENERATOR", sequenceName = "TBRANCHSTOCK_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBRANCHSTOCK_TBRANCHSTOCKPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTbranchstockpk() {
		return this.tbranchstockpk;
	}

	public void setTbranchstockpk(Integer tbranchstockpk) {
		this.tbranchstockpk = tbranchstockpk;
	}


	public Integer getStockcabang() {
		return this.stockcabang;
	}

	public void setStockcabang(Integer stockcabang) {
		this.stockcabang = stockcabang;
	}


	public Integer getStockdelivered() {
		return stockdelivered;
	}


	public void setStockdelivered(Integer stockdelivered) {
		this.stockdelivered = stockdelivered;
	}


	public Integer getStockactivated() {
		return stockactivated;
	}


	public void setStockactivated(Integer stockactivated) {
		this.stockactivated = stockactivated;
	}


	//bi-directional many-to-one association to Mbranch
	@ManyToOne
	@JoinColumn(name="mbranchfk", nullable=false)
	public Mbranch getMbranch() {
		return this.mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}


	//bi-directional many-to-one association to Mproduct
	@ManyToOne
	@JoinColumn(name="mproductfk", nullable=false)
	public Mproduct getMproduct() {
		return this.mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}


	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOutlet() {
		return outlet;
	}


	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}


	public Integer getStockreserved() {
		return stockreserved;
	}


	public void setStockreserved(Integer stockreserved) {
		this.stockreserved = stockreserved;
	}

}
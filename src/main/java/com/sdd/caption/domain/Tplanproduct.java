package com.sdd.caption.domain;

import java.io.Serializable;

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

@Entity
@Table(name = "tplanproduct")
@NamedQuery(name = "Tplanproduct.findAll", query = "SELECT m FROM Tplanproduct m")
public class Tplanproduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tplanproductpk;
	private Integer unitqty;
	private Tplan tplan;
	private Mproducttype mproducttype;
	
	public Tplanproduct () {
		
	}

	@Id
	@SequenceGenerator(name = "TPLANPRODUCT_TPLANPRODUCTPK_GENERATOR", sequenceName = "TPLANPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPLANPRODUCT_TPLANPRODUCTPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTplanproductpk() {
		return tplanproductpk;
	}

	public void setTplanproductpk(Integer tplanproductpk) {
		this.tplanproductpk = tplanproductpk;
	}

	public Integer getUnitqty() {
		return unitqty;
	}

	public void setUnitqty(Integer unitqty) {
		this.unitqty = unitqty;
	}

	@ManyToOne
	@JoinColumn(name = "tplanfk")
	public Tplan getTplan() {
		return tplan;
	}

	public void setTplan(Tplan tplan) {
		this.tplan = tplan;
	}

	@ManyToOne
	@JoinColumn(name = "mproducttypefk")
	public Mproducttype getMproducttype() {
		return mproducttype;
	}

	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}
	
	
}

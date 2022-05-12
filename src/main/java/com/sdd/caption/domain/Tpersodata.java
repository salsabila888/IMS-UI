package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the tpersodata database table.
 * 
 */
@Entity
@Table(name = "tpersodata")
@NamedQuery(name = "Tpersodata.findAll", query = "SELECT t FROM Tpersodata t")
public class Tpersodata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpersodatapk;
	private String isgetpaket;
	private Date orderdate;
	private Integer quantity;	
	private String status;	
	private Mbranch mbranch;
	private Tperso tperso;
	private Tembossbranch tembossbranch;

	public Tpersodata() {
	}

	@Id
	@SequenceGenerator(name = "TPERSODATA_TPERSODATAPK_GENERATOR", sequenceName = "TPERSODATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPERSODATA_TPERSODATAPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpersodatapk() {
		return this.tpersodatapk;
	}

	public void setTpersodatapk(Integer tpersodatapk) {
		this.tpersodatapk = tpersodatapk;
	}

	
	public String getIsgetpaket() {
		return isgetpaket;
	}

	public void setIsgetpaket(String isgetpaket) {
		this.isgetpaket = isgetpaket;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// bi-directional many-to-one association to Tperso
	@ManyToOne
	@JoinColumn(name = "tpersofk")
	public Tperso getTperso() {
		return this.tperso;
	}

	public void setTperso(Tperso tperso) {
		this.tperso = tperso;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// bi-directional many-to-one association to Tperso
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	// bi-directional many-to-one association to Tderivatifproduct
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

}
package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tstockcard database table.
 * 
 */
@Entity
@Table(name = "tstockcard")
@NamedQuery(name="Tstockcard.findAll", query="SELECT t FROM Tstockcard t")
public class Tstockcard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer tstockcardpk;
	private Integer incomingtotal;
	private Date lastupdated;
	private Integer outgoingtotal;
	private String productgroup;
	private String productorg;
	private String producttype;
	private Integer stock;
	private Date trxdate;
	private String updatedby;
	private Mproducttype mproducttype;

	public Tstockcard() {
	}

	@Id
	@SequenceGenerator(name="TSTOCKCARD_TSTOCKCARDPK_GENERATOR", sequenceName = "TSTOCKCARD_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TSTOCKCARD_TSTOCKCARDPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTstockcardpk() {
		return this.tstockcardpk;
	}

	public void setTstockcardpk(Integer tstockcardpk) {
		this.tstockcardpk = tstockcardpk;
	}


	public Integer getIncomingtotal() {
		return this.incomingtotal;
	}

	public void setIncomingtotal(Integer incomingtotal) {
		this.incomingtotal = incomingtotal;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public Integer getOutgoingtotal() {
		return this.outgoingtotal;
	}

	public void setOutgoingtotal(Integer outgoingtotal) {
		this.outgoingtotal = outgoingtotal;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductorg() {
		return productorg;
	}


	public void setProductorg(String productorg) {
		this.productorg = productorg;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}


	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}


	@Column(length=40)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProducttype() {
		return this.producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}


	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}


	@Temporal(TemporalType.DATE)
	public Date getTrxdate() {
		return this.trxdate;
	}

	public void setTrxdate(Date trxdate) {
		this.trxdate = trxdate;
	}


	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}


	//bi-directional many-to-one association to Mproducttype
	@ManyToOne
	@JoinColumn(name="mproducttypefk")
	public Mproducttype getMproducttype() {
		return this.mproducttype;
	}

	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}

}
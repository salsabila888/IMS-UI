package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the tpinpadserial database table.
 * 
 */
@Entity
@Table(name = "tpinpadserial")
@NamedQuery(name="Tpinpadserial.findAll", query="SELECT t FROM Tpinpadserial t")
public class Tpinpadserial implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinpadserialpk;
	private String serialno;
	private String status;
	private Mbranch mbranch;
	private Mproducttype mproducttype;
	private Tincoming tincoming;

	public Tpinpadserial() {
	}


	@Id
	@SequenceGenerator(name = "TPINPADSERIAL_TPINPADSERIALPK_GENERATOR", sequenceName = "TPINPADSERIAL_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINPADSERIAL_TPINPADSERIALPK_GENERATOR")
	public Integer getTpinpadserialpk() {
		return this.tpinpadserialpk;
	}

	public void setTpinpadserialpk(Integer tpinpadserialpk) {
		this.tpinpadserialpk = tpinpadserialpk;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@ManyToOne
	@JoinColumn(name="mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}


	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}


	@ManyToOne
	@JoinColumn(name="mproducttypefk")
	public Mproducttype getMproducttype() {
		return mproducttype;
	}


	public void setMproducttype(Mproducttype mproducttype) {
		this.mproducttype = mproducttype;
	}


	@ManyToOne
	@JoinColumn(name="tincomingfk")
	public Tincoming getTincoming() {
		return tincoming;
	}


	public void setTincoming(Tincoming tincoming) {
		this.tincoming = tincoming;
	}

}
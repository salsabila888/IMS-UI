package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the ttokenserial database table.
 * 
 */
@Entity
@Table(name = "ttokenserial")
@NamedQuery(name="Ttokenserial.findAll", query="SELECT t FROM Ttokenserial t")
public class Ttokenserial implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ttokenserialpk;
	private String serialno;
	private String serialnoinjected;
	private String status;
	private Mbranch mbranch;
	private Mproducttype mproducttype;
	private Tincoming tincoming;
	

	public Ttokenserial() {
	}


	@Id
	@SequenceGenerator(name = "TTOKENSERIAL_TTOKENSERIALPK_GENERATOR", sequenceName = "TTOKENSERIAL_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TTOKENSERIAL_TTOKENSERIALPK_GENERATOR")
	public Integer getTtokenserialpk() {
		return this.ttokenserialpk;
	}

	public void setTtokenserialpk(Integer ttokenserialpk) {
		this.ttokenserialpk = ttokenserialpk;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSerialnoinjected() {
		return this.serialnoinjected;
	}

	public void setSerialnoinjected(String serialnoinjected) {
		this.serialnoinjected = serialnoinjected;
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
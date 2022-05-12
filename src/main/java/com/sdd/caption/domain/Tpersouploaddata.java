package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the Tpersouploaddata database table.
 * 
 */
@Entity
@Table(name = "Tpersouploaddata")
@NamedQuery(name = "Tpersouploaddata.findAll", query = "SELECT t FROM Tpersouploaddata t")
public class Tpersouploaddata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpersouploaddatapk;
	private String cardno;
	private Date orderdate;
	private Tembossdata tembossdata;
	private Tpersoupload tpersoupload;

	public Tpersouploaddata() {
	}

	@Id
	@SequenceGenerator(name = "Tpersouploaddata_TpersouploaddataPK_GENERATOR", sequenceName = "Tpersouploaddata_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Tpersouploaddata_TpersouploaddataPK_GENERATOR")
	public Integer getTpersouploaddatapk() {
		return this.tpersouploaddatapk;
	}

	public void setTpersouploaddatapk(Integer tpersouploaddatapk) {
		this.tpersouploaddatapk = tpersouploaddatapk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@ManyToOne
	@JoinColumn(name = "tembossdatafk")
	public Tembossdata getTembossdata() {
		return this.tembossdata;
	}

	public void setTembossdata(Tembossdata tembossdata) {
		this.tembossdata = tembossdata;
	}

	@ManyToOne
	@JoinColumn(name = "tpersouploadfk")
	public Tpersoupload getTpersoupload() {
		return tpersoupload;
	}

	public void setTpersoupload(Tpersoupload tpersoupload) {
		this.tpersoupload = tpersoupload;
	}
}
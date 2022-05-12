package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tpinpadorder database table.
 * 
 */
@Entity
@Table(name = "tpinpadorder")
@NamedQuery(name = "Tpinpadorder.findAll", query = "SELECT t FROM Tpinpadorder t")
public class Tpinpadorder implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinpadorderpk;
	private String entrytype;
	private String memo;
	private Date orderdate;
	private String orderinputby;
	private Date orderinputtime;
	private String orderno;
	private Integer orderqty;
	private String status;
	private Mbranch mbranch;
	private Mproduct mproduct;

	public Tpinpadorder() {
	}

	@Id
	@SequenceGenerator(name = "TPINPADORDER_TPINPADORDERPK_GENERATOR", sequenceName = "TPINPADORDER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINPADORDER_TPINPADORDERPK_GENERATOR")
	public Integer getTpinpadorderpk() {
		return this.tpinpadorderpk;
	}

	public void setTpinpadorderpk(Integer tpinpadorderpk) {
		this.tpinpadorderpk = tpinpadorderpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getEntrytype() {
		return this.entrytype;
	}

	public void setEntrytype(String entrytype) {
		this.entrytype = entrytype;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderinputby() {
		return this.orderinputby;
	}

	public void setOrderinputby(String orderinputby) {
		this.orderinputby = orderinputby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getOrderinputtime() {
		return this.orderinputtime;
	}

	public void setOrderinputtime(Date orderinputtime) {
		this.orderinputtime = orderinputtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Integer getOrderqty() {
		return this.orderqty;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

}
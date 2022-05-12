package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tpaket database table.
 * 
 */
@Entity
@Table(name = "tpaket")
@NamedQuery(name = "Tpaket.findAll", query = "SELECT t FROM Tpaket t")
public class Tpaket implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpaketpk;
	private Date orderdate;
	private String paketid;
	private String processedby;
	private Date processtime;
	private String productgroup;
	private String status;
	private Integer totaldata;
	private Integer totaldone;
	private Mproduct mproduct;
	private Torder torder;
	private Tperso tperso;
	private Tembossproduct tembossproduct;
	private Tderivatifproduct tderivatifproduct;
	private Treturn treturn;
	private Tswitch tswitch;

	public Tpaket() {
	}

	@Id
	@SequenceGenerator(name = "TPAKET_TPAKETPK_GENERATOR", sequenceName = "TPAKET_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPAKET_TPAKETPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpaketpk() {
		return this.tpaketpk;
	}

	public void setTpaketpk(Integer tpaketpk) {
		this.tpaketpk = tpaketpk;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPaketid() {
		return this.paketid;
	}

	public void setPaketid(String paketid) {
		this.paketid = paketid;
	}

	public String getProcessedby() {
		return processedby;
	}

	public void setProcessedby(String processedby) {
		this.processedby = processedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getProcesstime() {
		return processtime;
	}

	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Column(length = 2)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Integer getTotaldone() {
		return totaldone;
	}

	public void setTotaldone(Integer totaldone) {
		this.totaldone = totaldone;
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

	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

	@ManyToOne
	@JoinColumn(name = "tpersofk")
	public Tperso getTperso() {
		return tperso;
	}

	public void setTperso(Tperso tperso) {
		this.tperso = tperso;
	}

	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}

	@ManyToOne
	@JoinColumn(name = "tderivatifproductfk")
	public Tderivatifproduct getTderivatifproduct() {
		return tderivatifproduct;
	}

	public void setTderivatifproduct(Tderivatifproduct tderivatifproduct) {
		this.tderivatifproduct = tderivatifproduct;
	}

	@ManyToOne
	@JoinColumn(name = "treturnfk")
	public Treturn getTreturn() {
		return treturn;
	}

	public void setTreturn(Treturn treturn) {
		this.treturn = treturn;
	}

	@ManyToOne
	@JoinColumn(name = "tswitchfk")
	public Tswitch getTswitch() {
		return tswitch;
	}

	public void setTswitch(Tswitch tswitch) {
		this.tswitch = tswitch;
	}

}
package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;



/**
 * The persistent class for the Mbinno database table.
 * 
 */
@Entity
@Table(name = "trollback")
@NamedQuery(name = "trollback.findAll", query = "SELECT m FROM Trollback m")
public class Trollback implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer trollbackpk;
	private Date orderdate;
	private Integer totaldata;
	private Date rollbacktime;
	private String rollbackby;
	private String status;
	private Mproduct mproduct;
	private Mbranch mbranch;
	private Tpaketdata Tpaketdata;

	public Trollback(){
		
	}

	@Id
	@SequenceGenerator(name = "TROLLBACK_TROLLBACKPK_GENERATOR", sequenceName = "TROLLBACK_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TROLLBACK_TROLLBACKPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTrollbackpk() {
		return trollbackpk;
	}

	public void setTrollbackpk(Integer trollbackpk) {
		this.trollbackpk = trollbackpk;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Date getRollbacktime() {
		return rollbacktime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public void setRollbacktime(Date rollbacktime) {
		this.rollbacktime = rollbacktime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRollbackby() {
		return rollbackby;
	}

	public void setRollbackby(String rollbackby) {
		this.rollbackby = rollbackby;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	@ManyToOne
	@JoinColumn(name = "tpaketdatafk")
	public Tpaketdata getTpaketdata() {
		return Tpaketdata;
	}

	public void setTpaketdata(Tpaketdata tpaketdata) {
		Tpaketdata = tpaketdata;
	}

}
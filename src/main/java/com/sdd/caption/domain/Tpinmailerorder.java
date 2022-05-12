package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tpinmailerorder database table.
 * 
 */
@Entity
@Table(name="tpinmailerorder")
@NamedQuery(name="Tpinmailerorder.findAll", query="SELECT t FROM Tpinmailerorder t")
public class Tpinmailerorder implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinmailerorderpk;
	private String decisionby;
	private String decisionmemo;
	private Date decisiontime;
	private String entryby;
	private Date entrytime;
	private String filename;
	private String memo;
	private String orderid;
	private String status;
	private Integer totaldata;
	private Mproduct mproduct;
	
	public Tpinmailerorder() {
	}


	@Id
	@SequenceGenerator(name="TPINMAILERORDER_TPINMAILERORDERPK_GENERATOR", sequenceName = "TPINMAILERORDER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TPINMAILERORDER_TPINMAILERORDERPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTpinmailerorderpk() {
		return this.tpinmailerorderpk;
	}

	public void setTpinmailerorderpk(Integer tpinmailerorderpk) {
		this.tpinmailerorderpk = tpinmailerorderpk;
	}


	@Column(length=15)
	public String getDecisionby() {
		return this.decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDecisionmemo() {
		return this.decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return this.decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	
	public String getEntryby() {
		return entryby;
	}


	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}


	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}


	@Column(length=70)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


	@Column(length=15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	

	public String getStatus() {
		return status;
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


	@ManyToOne
	@JoinColumn(name="mproductfk")	
	public Mproduct getMproduct() {
		return mproduct;
	}


	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

}
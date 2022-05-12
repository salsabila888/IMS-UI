package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "tordermemo")
@NamedQuery(name = "Tordermemo.findAll", query = "SELECT t FROM Tordermemo t")
public class Tordermemo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tordermemopk;
	private String memo;
	private String memoby;
	private Date memotime;
	private Torder torder;
	
	public Tordermemo() {
		
	}

	@Id
	@SequenceGenerator(name = "TORDERMEMO_TORDERMEMOPK_GENERATOR", sequenceName = "TORDERMEMO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TORDERMEMO_TORDERMEMOPK_GENERATOR")
	public Integer getTordermemopk() {
		return tordermemopk;
	}

	public void setTordermemopk(Integer tordermemopk) {
		this.tordermemopk = tordermemopk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemoby() {
		return memoby;
	}

	public void setMemoby(String memoby) {
		this.memoby = memoby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getMemotime() {
		return memotime;
	}

	public void setMemotime(Date memotime) {
		this.memotime = memotime;
	}

	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}
	
	

}

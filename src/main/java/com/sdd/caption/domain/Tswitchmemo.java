package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

@Entity
@Table(name="tswitchmemo")
@NamedQuery(name="Tswitchmemo.findAll", query="SELECT t FROM Tswitchmemo t")
public class Tswitchmemo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer tswitchmemopk;
	private String memo;
	private String memoby;
	private Date memotime;
	private Tswitch tswitch;
	
	public Tswitchmemo() {
	}
	
	@Id
	@SequenceGenerator(name="TSWITCHMEMO_TSWITCHMEMOPK_GENERATOR", sequenceName = "TSWITCHMEMO_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TSWITCHMEMO_TSWITCHMEMOPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTswitchmemopk() {
		return tswitchmemopk;
	}

	public void setTswitchmemopk(Integer tswitchmemopk) {
		this.tswitchmemopk = tswitchmemopk;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(length=15)
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

	//bi-directional many-to-one association to Tswitch
	@ManyToOne
	@JoinColumn(name="tswitchfk")
	public Tswitch getTswitch() {
		return tswitch;
	}

	public void setTswitch(Tswitch tswitch) {
		this.tswitch = tswitch;
	}
	

}

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

import org.hibernate.annotations.Type;

@Entity
@Table(name="treturnmemo")
@NamedQuery(name="Treturnmemo.findAll", query="SELECT t FROM Treturnmemo t")
public class Treturnmemo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer treturnmemopk;
	private Treturn treturn;
	private String memo;
	private String memoby;
	private Date memotime;
	
	public Treturnmemo() {
	}
	
	@Id
	@SequenceGenerator(name="TRETURNMEMO_TRETURNMEMOPK_GENERATOR", sequenceName = "TRETURNMEMO_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRETURNMEMO_TRETURNMEMOPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTreturnmemopk() {
		return treturnmemopk;
	}
	public void setTreturnmemopk(Integer treturnmemopk) {
		this.treturnmemopk = treturnmemopk;
	}
	
	//bi-directional many-to-one association to Treturn
	@ManyToOne
	@JoinColumn(name="treturnfk")
	public Treturn getTreturn() {
		return treturn;
	}
	public void setTreturn(Treturn treturn) {
		this.treturn = treturn;
	}
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

}

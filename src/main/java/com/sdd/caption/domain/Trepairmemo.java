package com.sdd.caption.domain;

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
@Table(name="trepairmemo")
@NamedQuery(name="Trepairmemo.findAll", query="SELECT t FROM Trepairmemo t")
public class Trepairmemo {
	private static final long serialVersionUID = 1L;
	private Integer trepairmemopk;
	private Trepair trepair;
	private String memo;
	private String memoby;
	private Date memotime;
	
	public Trepairmemo() {
	}
	
	@Id
	@SequenceGenerator(name="TREPAIRMEMO_TREPAIRMEMOPK_GENERATOR", sequenceName = "TREPAIRMEMO_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TREPAIRMEMO_TREPAIRMEMOPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTrepairmemopk() {
		return trepairmemopk;
	}
	public void setTrepairmemopk(Integer trepairmemopk) {
		this.trepairmemopk = trepairmemopk;
	}
	
	//bi-directional many-to-one association to Trepair
	@ManyToOne
	@JoinColumn(name="trepairfk")
	public Trepair getTrepair() {
		return trepair;
	}
	public void setTrepair(Trepair trepair) {
		this.trepair = trepair;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
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

package com.sdd.caption.domain;

import java.io.Serializable;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name="tbranchstockitem")
@NamedQuery(name="Tbranchstockitem.findAll", query="SELECT t FROM Tbranchstockitem t")
public class Tbranchstockitem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tbranchstockitempk;
	private String productgroup;
	private String itemno;
	private Integer numerator;
	private String status;
	private Tbranchstock tbranchstock;
	
	public Tbranchstockitem() {
		
	}

	@Id
	@SequenceGenerator(name="TBRANCHSTOCKITEM_TBRANCHSTOCKITEMPK_GENERATOR", sequenceName = "TBRANCHSTOCKITEM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBRANCHSTOCKITEM_TBRANCHSTOCKITEMPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTbranchstockitempk() {
		return tbranchstockitempk;
	}

	public void setTbranchstockitempk(Integer tbranchstockitempk) {
		this.tbranchstockitempk = tbranchstockitempk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name="tbranchstockfk", nullable=false)
	public Tbranchstock getTbranchstock() {
		return tbranchstock;
	}

	public void setTbranchstock(Tbranchstock tbranchstock) {
		this.tbranchstock = tbranchstock;
	}

	public Integer getNumerator() {
		return numerator;
	}

	public void setNumerator(Integer numerator) {
		this.numerator = numerator;
	}
}

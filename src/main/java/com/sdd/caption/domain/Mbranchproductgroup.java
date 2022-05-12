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

@Entity
@Table(name = "mbranchproductgroup")
@NamedQuery(name = "Mbranchproductgroup.findAll", query = "SELECT m FROM Mbranchproductgroup m")
public class Mbranchproductgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mbranchproductgrouppk;
	private Mbranch mbranch;
	private Mproductgroup mproductgroup;
	
	public Mbranchproductgroup() {
		
	}

	@Id
	@SequenceGenerator(name = "MBRANCHPRODUCTGROUP_MBRANCHPRODUCTGROUPPK_GENERATOR", sequenceName = "MBRANCHPRODUCTGROUP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MBRANCHPRODUCTGROUP_MBRANCHPRODUCTGROUPPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMbranchproductgrouppk() {
		return mbranchproductgrouppk;
	}

	public void setMbranchproductgrouppk(Integer mbranchproductgrouppk) {
		this.mbranchproductgrouppk = mbranchproductgrouppk;
	}

	@ManyToOne
	@JoinColumn(name="mbranchfk", nullable=false)
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	@ManyToOne
	@JoinColumn(name="mproductgroupfk", nullable=false)
	public Mproductgroup getMproductgroup() {
		return mproductgroup;
	}

	public void setMproductgroup(Mproductgroup mproductgroup) {
		this.mproductgroup = mproductgroup;
	}
	
	
}

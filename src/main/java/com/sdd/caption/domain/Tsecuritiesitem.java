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
@Table(name = "tsecuritiesitem")
@NamedQuery(name = "Tsecurities.findAll", query = "SELECT t FROM Tsecuritiesitem t")
public class Tsecuritiesitem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tsecuritiesitempk;
	private String itemno;
	private Integer numerator;
	private String status;
	private Tincoming tincoming;

	public Tsecuritiesitem() {

	}

	@Id
	@SequenceGenerator(name = "TSECURITIESITEM_TSECURITIESITEMPK_GENERATOR", sequenceName = "TSECURITIESITEM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TSECURITIESITEM_TSECURITIESITEMPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTsecuritiesitempk() {
		return tsecuritiesitempk;
	}

	public void setTsecuritiesitempk(Integer tsecuritiesitempk) {
		this.tsecuritiesitempk = tsecuritiesitempk;
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
	@JoinColumn(name="tincomingfk")
	public Tincoming getTincoming() {
		return tincoming;
	}

	public void setTincoming(Tincoming tincoming) {
		this.tincoming = tincoming;
	}

	public Integer getNumerator() {
		return numerator;
	}

	public void setNumerator(Integer numerator) {
		this.numerator = numerator;
	}
}

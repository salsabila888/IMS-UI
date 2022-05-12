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
@Table(name="treturnitem")
@NamedQuery(name="Treturnitem.findAll", query="SELECT t FROM Treturnitem t")
public class Treturnitem implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer treturnitempk;
	private Treturn treturn;
	private String itemno;
	private String itemstatus;
	
	public Treturnitem() {
	}

	@Id
	@SequenceGenerator(name="TRETURNITEM_TRETURNITEMPK_GENERATOR", sequenceName = "TRETURNITEM_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRETURNITEM_TRETURNITEMPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTreturnitempk() {
		return treturnitempk;
	}

	public void setTreturnitempk(Integer treturnitempk) {
		this.treturnitempk = treturnitempk;
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

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}

}

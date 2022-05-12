package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the tpinpaditem database table.
 * 
 */
@Entity
@Table(name = "tpinpaditem")
@NamedQuery(name="Tpinpaditem.findAll", query="SELECT t FROM Tpinpaditem t")
public class Tpinpaditem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinpaditempk;
	private String itemno;
	private String status;
	private Tincoming tincoming;

	public Tpinpaditem() {
	}


	@Id
	@SequenceGenerator(name = "TPINPADITEM_TPINPADITEMPK_GENERATOR", sequenceName = "TPINPADITEM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINPADITEM_TPINPADITEMPK_GENERATOR")
	public Integer getTpinpaditempk() {
		return this.tpinpaditempk;
	}

	public void setTpinpaditempk(Integer tpinpaditempk) {
		this.tpinpaditempk = tpinpaditempk;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getItemno() {
		return this.itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
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

}
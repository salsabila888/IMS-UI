package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the ttokenitem database table.
 * 
 */
@Entity
@Table(name = "ttokenitem")
@NamedQuery(name="Ttokenitem.findAll", query="SELECT t FROM Ttokenitem t")
public class Ttokenitem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ttokenitempk;
	private String itemno;
	private String itemnoinject;
	private String status;
	private Tincoming tincoming;
	

	public Ttokenitem() {
	}


	@Id
	@SequenceGenerator(name = "TTOKENITEM_TTOKENITEMPK_GENERATOR", sequenceName = "TTOKENITEM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TTOKENITEM_TTOKENITEMPK_GENERATOR")
	public Integer getTtokenitempk() {
		return this.ttokenitempk;
	}

	public void setTtokenitempk(Integer ttokenitempk) {
		this.ttokenitempk = ttokenitempk;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getItemno() {
		return this.itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getItemnoinject() {
		return this.itemnoinject;
	}

	public void setItemnoinject(String itemnoinject) {
		this.itemnoinject = itemnoinject;
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
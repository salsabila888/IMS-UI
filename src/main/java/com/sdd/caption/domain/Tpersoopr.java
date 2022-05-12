package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tpersoopr database table.
 * 
 */
@Entity
@Table(name = "tpersoopr")
@NamedQuery(name = "Tpersoopr.findAll", query = "SELECT m FROM Tpersoopr m")
public class Tpersoopr implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpersooprpk;
	private String oprid;
	private String oprname;
	private Tperso tperso;

	public Tpersoopr() {
	}

	@Id
	@SequenceGenerator(name = "TPERSOOPR_TPERSOOPRPK_GENERATOR", sequenceName = "TPERSOOPR_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPERSOOPR_TPERSOOPRPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpersooprpk() {
		return this.tpersooprpk;
	}

	public void setTpersooprpk(Integer tpersooprpk) {
		this.tpersooprpk = tpersooprpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOprid() {
		return oprid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOprname() {
		return oprname;
	}

	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

	public void setOprname(String oprname) {
		this.oprname = oprname;
	}

	// bi-directional many-to-one association to Tperso
	@ManyToOne
	@JoinColumn(name = "tpersofk")
	public Tperso getTperso() {
		return this.tperso;
	}

	public void setTperso(Tperso tperso) {
		this.tperso = tperso;
	}

}
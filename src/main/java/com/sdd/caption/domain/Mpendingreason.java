package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the mpendingreason database table.
 * 
 */
@Entity
@Table(name = "mpendingreason")
@NamedQuery(name = "Mpendingreason.findAll", query = "SELECT m FROM Mpendingreason m")
public class Mpendingreason implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mpendingreasonpk;
	private Date lastupdated;
	private String pendingreason;
	private String updatedby;

	public Mpendingreason() {
	}


	@Id
	@SequenceGenerator(name="MPENDINGREASON_MPENDINGREASONPK_GENERATOR", sequenceName = "MPENDINGREASON_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MPENDINGREASON_MPENDINGREASONPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMpendingreasonpk() {
		return this.mpendingreasonpk;
	}

	public void setMpendingreasonpk(Integer mpendingreasonpk) {
		this.mpendingreasonpk = mpendingreasonpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPendingreason() {
		return this.pendingreason;
	}

	public void setPendingreason(String pendingreason) {
		this.pendingreason = pendingreason;
	}


	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}
package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "mproductreq")
@NamedQuery(name = "Mproductreq.findAll", query = "SELECT m FROM Mproductreq m")
public class Mproductreq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mproductreqpk;
	private String productcode;
	private String productname;
	private String status;
	private String isinstant;
	private String isderivatif;
	private Date lastupdated;
	private Date entrytime;
	private String updatedby;
	
	public Mproductreq() {
		
	}

	@Id
	@SequenceGenerator(name = "MPRODUCTREQ_MPRODUCTREQPK_GENERATOR", sequenceName = "MPRODUCTREQ_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MPRODUCTREQ_MPRODUCTREQPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getMproductreqpk() {
		return mproductreqpk;
	}

	public void setMproductreqpk(Integer mproductreqpk) {
		this.mproductreqpk = mproductreqpk;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsderivatif() {
		return isderivatif;
	}

	public void setIsderivatif(String isderivatif) {
		this.isderivatif = isderivatif;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	

}

package com.sdd.caption.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "tproductmm")
@NamedQuery(name = "Tproductmm.findAll", query = "SELECT m FROM Tproductmm m")
public class Tproductmm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tproductmmpk;
	private String ismatch;
	private Date orderdate;
	private String org;
	private String productcode;
	private String productname;
	private String producttype;
	private Integer totaldata;
	private Integer totalmerge;
	private Integer totalos;
	private Date entrytime;
	private String rekonby;
	private Date rekontime;
	private Tembossfile tembossfile;

	public Tproductmm() {

	}

	@Id
	@SequenceGenerator(name = "TPRODUCTMM_TPRODUCTMMPK_GENERATOR", sequenceName = "TPRODUCTMM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPRODUCTMM_TPRODUCTMMPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTproductmmpk() {
		return tproductmmpk;
	}

	public void setTproductmmpk(Integer tproductmmpk) {
		this.tproductmmpk = tproductmmpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsmatch() {
		return ismatch;
	}

	public void setIsmatch(String ismatch) {
		this.ismatch = ismatch;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	@Column(length = 5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Integer getTotalmerge() {
		return totalmerge;
	}

	public void setTotalmerge(Integer totalmerge) {
		this.totalmerge = totalmerge;
	}

	public Integer getTotalos() {
		return totalos;
	}

	public void setTotalos(Integer totalos) {
		this.totalos = totalos;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRekonby() {
		return rekonby;
	}

	public void setRekonby(String rekonby) {
		this.rekonby = rekonby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRekontime() {
		return rekontime;
	}

	public void setRekontime(Date rekontime) {
		this.rekontime = rekontime;
	}

	@ManyToOne
	@JoinColumn(name = "tembossfilefk")
	public Tembossfile getTembossfile() {
		return this.tembossfile;
	}

	public void setTembossfile(Tembossfile tembossfile) {
		this.tembossfile = tembossfile;
	}

}

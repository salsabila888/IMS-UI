package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tembossproduct database table.
 * 
 */
@Entity
@Table(name = "tembossproduct")
@NamedQuery(name="Tembossproduct.findAll", query="SELECT t FROM Tembossproduct t")
public class Tembossproduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tembossproductpk;
	private Date entrytime;
	private String isinstant;
	private String isneeddoc;
	private Date orderdate;
	private String org;
	private Integer orderos;
	private String productcode;
	private String status;
	private Integer totaldata;
	private Integer totalproses;
	private Tembossfile tembossfile;
	private Mproduct mproduct;

	public Tembossproduct() {
	}

	@Id
	@SequenceGenerator(name = "TEMBOSSPRODUCT_TEMBOSSPRODUCTPK_GENERATOR", sequenceName = "TEMBOSSPRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMBOSSPRODUCT_TEMBOSSPRODUCTPK_GENERATOR")
	public Integer getTembossproductpk() {
		return this.tembossproductpk;
	}

	public void setTembossproductpk(Integer tembossproductpk) {
		this.tembossproductpk = tembossproductpk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return this.entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return this.isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsneeddoc() {
		return this.isneeddoc;
	}

	public void setIsneeddoc(String isneeddoc) {
		this.isneeddoc = isneeddoc;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrg() {
		return this.org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	
	
	public Integer getOrderos() {
		return orderos;
	}

	public void setOrderos(Integer orderos) {
		this.orderos = orderos;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalproses() {
		return totalproses;
	}

	public void setTotalproses(Integer totalproses) {
		this.totalproses = totalproses;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	@ManyToOne
	@JoinColumn(name="tembossfilefk")
	public Tembossfile getTembossfile() {
		return this.tembossfile;
	}

	public void setTembossfile(Tembossfile tembossfile) {
		this.tembossfile = tembossfile;
	}

	//bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name="mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

}
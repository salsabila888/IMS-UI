package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tembossdata database table.
 * 
 */
@Entity
@Table(name = "tembossdata")
@NamedQuery(name = "Tembossdata.findAll", query = "SELECT t FROM Tembossdata t")
public class Tembossdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tembossdatapk;
	private String branchid;
	private String branchname;
	private String cardno;
	private String isinstant;
	private String klncode;
	private String klnname;
	private String nameoncard;
	private String nameonid;
	private Mbranch mbranch;
	private Mproduct mproduct;
	private Date orderdate;
	private String productcode;
	private String seqno;
	private String isactivated;
	private String filebranchactv;
	private Date dateactivated;
	private Date activatedtime;
	private Tembossbranch tembossbranch;
	private Tembossproduct tembossproduct;
	private Tembossfile tembossfile;

	public Tembossdata() {
	}

	@Id
	@SequenceGenerator(name = "TEMBOSSDATA_TEMBOSSDATAPK_GENERATOR", sequenceName = "TEMBOSSDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMBOSSDATA_TEMBOSSDATAPK_GENERATOR")
	public Integer getTembossdatapk() {
		return this.tembossdatapk;
	}

	public void setTembossdatapk(Integer tembossdatapk) {
		this.tembossdatapk = tembossdatapk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return this.branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return this.isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKlncode() {
		return this.klncode;
	}

	public void setKlncode(String klncode) {
		this.klncode = klncode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKlnname() {
		return this.klnname;
	}

	public void setKlnname(String klnname) {
		this.klnname = klnname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNameoncard() {
		return this.nameoncard;
	}

	public void setNameoncard(String nameoncard) {
		this.nameoncard = nameoncard;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNameonid() {
		return this.nameonid;
	}

	public void setNameonid(String nameonid) {
		this.nameonid = nameonid;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getProductcode() {
		return this.productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSeqno() {
		return this.seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getIsactivated() {
		return isactivated;
	}

	public void setIsactivated(String isactivated) {
		this.isactivated = isactivated;
	}

	public String getFilebranchactv() {
		return filebranchactv;
	}

	public void setFilebranchactv(String filebranchactv) {
		this.filebranchactv = filebranchactv;
	}

	public Date getDateactivated() {
		return dateactivated;
	}

	public void setDateactivated(Date dateactivated) {
		this.dateactivated = dateactivated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getActivatedtime() {
		return activatedtime;
	}

	public void setActivatedtime(Date activatedtime) {
		this.activatedtime = activatedtime;
	}

	@ManyToOne
	@JoinColumn(name = "tembossfilefk")
	public Tembossfile getTembossfile() {
		return this.tembossfile;
	}

	public void setTembossfile(Tembossfile tembossfile) {
		this.tembossfile = tembossfile;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}

}
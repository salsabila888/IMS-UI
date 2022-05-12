package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tembossbranch database table.
 * 
 */
@Entity
@Table(name = "tembossbranch")
@NamedQuery(name="Tembossbranch.findAll", query="SELECT t FROM Tembossbranch t")
public class Tembossbranch implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tembossbranchpk;
	private String branchid;
	private String status;
	private Date entrytime;
	private Date orderdate;
	private Integer totaldata;
	private Integer totalos;
	private Integer totalproses;
	private Integer prodsla;
	private Integer dlvsla;
	private Integer slatotal;
	private Date dlvstarttime;
	private Date dlvfinishtime;
	private Tembossproduct tembossproduct;
	private Tembossfile tembossfile;
	private Mbranch mbranch;
	private Mproduct mproduct;

	public Tembossbranch() {
	}
	
	@Id
	@SequenceGenerator(name = "TEMBOSSBRANCH_TEMBOSSBRANCHPK_GENERATOR", sequenceName = "TEMBOSSBRANCH_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMBOSSBRANCH_TEMBOSSBRANCHPK_GENERATOR")
	public Integer getTembossbranchpk() {
		return this.tembossbranchpk;
	}

	public void setTembossbranchpk(Integer tembossbranchpk) {
		this.tembossbranchpk = tembossbranchpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return this.entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}	
	
	public Integer getTotalos() {
		return totalos;
	}

	public void setTotalos(Integer totalos) {
		this.totalos = totalos;
	}

	public Integer getTotalproses() {
		return totalproses;
	}

	public void setTotalproses(Integer totalproses) {
		this.totalproses = totalproses;
	}

	public Integer getProdsla() {
		return prodsla;
	}

	public void setProdsla(Integer prodsla) {
		this.prodsla = prodsla;
	}

	public Integer getDlvsla() {
		return dlvsla;
	}

	public void setDlvsla(Integer dlvsla) {
		this.dlvsla = dlvsla;
	}

	public Integer getSlatotal() {
		return slatotal;
	}

	public void setSlatotal(Integer slatotal) {
		this.slatotal = slatotal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDlvstarttime() {
		return dlvstarttime;
	}

	public void setDlvstarttime(Date dlvstarttime) {
		this.dlvstarttime = dlvstarttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDlvfinishtime() {
		return dlvfinishtime;
	}

	public void setDlvfinishtime(Date dlvfinishtime) {
		this.dlvfinishtime = dlvfinishtime;
	}

	//bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name="tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return this.tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}

	//bi-directional many-to-one association to Torder
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
	@JoinColumn(name="mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
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
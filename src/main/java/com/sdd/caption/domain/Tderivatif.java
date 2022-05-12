package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the tderivatif database table.
 * 
 */
@Entity
@Table(name = "tderivatif")
@NamedQuery(name = "Tderivatif.findAll", query = "SELECT t FROM Tderivatif t")
public class Tderivatif implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tderivatifpk;
	private String memo;
	private Date orderdate;
	private String orderno;
	private String filename;
	private Integer status;
	private Integer totaldata;
	private Integer totaladj;
	private Integer totalreject;
	private Integer prodsla;
	private Integer dlvsla;
	private Integer slatotal;
	private Date dlvstarttime;
	private Date dlvfinishtime;
	private Date entrytime;
	private String entryby;
	private String isscan;
	private String iscrop;
	private String ismerge;	
	private Date scandate;
	private Date cropdate;
	private Date mergedate;
	private Mbranch mbranch;
	private Mproduct mproduct;
	private Tdelivery tdelivery;

	public Tderivatif() {
	}

	@Id
	@SequenceGenerator(name = "TDERIVATIF_TDERIVATIFPK_GENERATOR", sequenceName = "TDERIVATIF_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDERIVATIF_TDERIVATIFPK_GENERATOR")
	public Integer getTderivatifpk() {
		return this.tderivatifpk;
	}

	public void setTderivatifpk(Integer tderivatifpk) {
		this.tderivatifpk = tderivatifpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Integer getTotaladj() {
		return totaladj;
	}

	public void setTotaladj(Integer totaladj) {
		this.totaladj = totaladj;
	}

	public Integer getTotalreject() {
		return totalreject;
	}

	public void setTotalreject(Integer totalreject) {
		this.totalreject = totalreject;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}

	public String getEntryby() {
		return entryby;
	}

	public void setEntryby(String entryby) {
		this.entryby = entryby;
	}

	public String getIsscan() {
		return isscan;
	}

	public void setIsscan(String isscan) {
		this.isscan = isscan;
	}

	public String getIscrop() {
		return iscrop;
	}

	public void setIscrop(String iscrop) {
		this.iscrop = iscrop;
	}

	public String getIsmerge() {
		return ismerge;
	}

	public void setIsmerge(String ismerge) {
		this.ismerge = ismerge;
	}

	public Date getScandate() {
		return scandate;
	}

	public void setScandate(Date scandate) {
		this.scandate = scandate;
	}

	public Date getCropdate() {
		return cropdate;
	}

	public void setCropdate(Date cropdate) {
		this.cropdate = cropdate;
	}

	public Date getMergedate() {
		return mergedate;
	}

	public void setMergedate(Date mergedate) {
		this.mergedate = mergedate;
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

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	@ManyToOne
	@JoinColumn(name = "tdeliveryfk")
	public Tdelivery getTdelivery() {
		return tdelivery;
	}

	public void setTdelivery(Tdelivery tdelivery) {
		this.tdelivery = tdelivery;
	}

}
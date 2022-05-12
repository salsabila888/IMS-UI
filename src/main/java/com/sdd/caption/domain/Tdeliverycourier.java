package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the tdeliverycourier database table.
 * 
 */
@Entity
@Table(name = "tdeliverycourier")
@NamedQuery(name = "Tdeliverycourier.findAll", query = "SELECT t FROM Tdeliverycourier t")
public class Tdeliverycourier implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer tdeliverycourierpk;
	private String dlvcourierid;
	private String isurgent;
	private String memo;
	private String processedby;
	private Date processtime;
	private String productgroup;
	private String status;
	private Integer totaldata;
	private Mcourier mcourier;
	private Mcouriervendor mcouriervendor;

	public Tdeliverycourier() {
	}

	@Id
	@SequenceGenerator(name = "TDELIVERYCOURIER_TDELIVERYCOURIERPK_GENERATOR", sequenceName = "TDELIVERYCOURIER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDELIVERYCOURIER_TDELIVERYCOURIERPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTdeliverycourierpk() {
		return this.tdeliverycourierpk;
	}

	public void setTdeliverycourierpk(Integer tdeliverycourierpk) {
		this.tdeliverycourierpk = tdeliverycourierpk;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDlvcourierid() {
		return this.dlvcourierid;
	}

	public void setDlvcourierid(String dlvcourierid) {
		this.dlvcourierid = dlvcourierid;
	}

	public String getIsurgent() {
		return this.isurgent;
	}

	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(length = 15)
	public String getProcessedby() {
		return this.processedby;
	}

	public void setProcessedby(String processedby) {
		this.processedby = processedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getProcesstime() {
		return this.processtime;
	}

	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Column(length = 2)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "mcourierfk")
	public Mcourier getMcourier() {
		return mcourier;
	}

	public void setMcourier(Mcourier mcourier) {
		this.mcourier = mcourier;
	}

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "mcouriervendorfk")
	public Mcouriervendor getMcouriervendor() {
		return mcouriervendor;
	}

	public void setMcouriervendor(Mcouriervendor mcouriervendor) {
		this.mcouriervendor = mcouriervendor;
	}

}
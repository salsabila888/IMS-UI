package com.sdd.caption.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tplan")
@NamedQuery(name = "Tplan.findAll", query = "SELECT m FROM Tplan m")
public class Tplan implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tplanpk;
	private String planno;
	private String memono;
	private Date memodate;
	private String memofileori;
	private String memofileid;
	private BigDecimal anggaran;
	private String status;
	private String inputer;
	private Date inputtime;
	private String decisionby;
	private Date decisiontime;
	private String decisiondesc;
	private String productgroup;
	private Integer totalqty;
	private Mbranch mbranch;
	
	public Tplan() {
		
	}

	@Id
	@SequenceGenerator(name = "TPLAN_TPLANPK_GENERATOR", sequenceName = "TPLAN_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPLAN_TPLANPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTplanpk() {
		return tplanpk;
	}

	public void setTplanpk(Integer tplanpk) {
		this.tplanpk = tplanpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 20)
	public String getPlanno() {
		return planno;
	}

	public void setPlanno(String planno) {
		this.planno = planno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 20)
	public String getMemono() {
		return memono;
	}

	public void setMemono(String memono) {
		this.memono = memono;
	}

	@Temporal(TemporalType.DATE)
	public Date getMemodate() {
		return memodate;
	}

	public void setMemodate(Date memodate) {
		this.memodate = memodate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	@Column(length = 100)
	public String getMemofileori() {
		return memofileori;
	}

	public void setMemofileori(String memofileori) {
		this.memofileori = memofileori;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 100)
	public String getMemofileid() {
		return memofileid;
	}

	public void setMemofileid(String memofileid) {
		this.memofileid = memofileid;
	}

	public BigDecimal getAnggaran() {
		return anggaran;
	}

	public void setAnggaran(BigDecimal anggaran) {
		this.anggaran = anggaran;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 40)
	public String getInputer() {
		return inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 40)
	public String getDecisionby() {
		return decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 200)
	public String getDecisiondesc() {
		return decisiondesc;
	}

	public void setDecisiondesc(String decisiondesc) {
		this.decisiondesc = decisiondesc;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	@Column(length = 2)
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@ManyToOne
	@JoinColumn(name="mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public Integer getTotalqty() {
		return totalqty;
	}

	public void setTotalqty(Integer totalqty) {
		this.totalqty = totalqty;
	}


	
	
}

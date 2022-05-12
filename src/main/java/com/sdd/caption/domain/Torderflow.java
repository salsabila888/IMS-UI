package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "torderflow")
@NamedQuery(name = "Torderflow.findAll", query = "SELECT t FROM Torderflow t")
public class Torderflow implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer torderflowpk;
	private String productgroup;
	private Integer totaldata;
	private Date flowtime;
	private String flowname;
	private String flowgroup;
	private String flowuser;
	private String memo;
	private Tembossbranch tembossbranch;
	private Torder torder;

	public Torderflow() {

	}

	@Id
	@SequenceGenerator(name = "TORDERFLOW_TORDERFLOWPK_GENERATOR", sequenceName = "TORDERFLOW_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TORDERFLOW_TORDERFLOWPK_GENERATOR")
	public Integer getTorderflowpk() {
		return torderflowpk;
	}

	public void setTorderflowpk(Integer torderflowpk) {
		this.torderflowpk = torderflowpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFlowtime() {
		return flowtime;
	}

	public void setFlowtime(Date flowtime) {
		this.flowtime = flowtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getFlowuser() {
		return flowuser;
	}

	public void setFlowuser(String flowuser) {
		this.flowuser = flowuser;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getFlowgroup() {
		return flowgroup;
	}

	public void setFlowgroup(String flowgroup) {
		this.flowgroup = flowgroup;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	// bi-directional many-to-one association to Tembossbranch
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
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

}

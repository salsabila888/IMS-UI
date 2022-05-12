package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "torderitem")
@NamedQuery(name = "Torderitem.findAll", query = "SELECT t FROM Torderitem t")
public class Torderitem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer torderitempk;
	private Torder torder;
	private Ttokenitem ttokenitem;
	private Tpinpaditem tpinpaditem;
	private Tsecuritiesitem tsecuritiesitem;
	private String productgroup;
	private String itemno;
	private Integer numerator;
	private String tid;
	private String pinpadtype;
	private String pinpadmemo;
	private String mid;

	public Torderitem() {
	}

	@Id
	@SequenceGenerator(name = "TORDERITEM_TORDERITEMPK_GENERATOR", sequenceName = "TORDERITEM_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TORDERITEM_TORDERITEMPK_GENERATOR")
	public Integer getTorderitempk() {
		return torderitempk;
	}

	public void setTorderitempk(Integer torderitempk) {
		this.torderitempk = torderitempk;
	}

	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

	@ManyToOne
	@JoinColumn(name = "ttokenitemfk")
	public Ttokenitem getTtokenitem() {
		return ttokenitem;
	}

	public void setTtokenitem(Ttokenitem ttokenitem) {
		this.ttokenitem = ttokenitem;
	}

	@ManyToOne
	@JoinColumn(name = "tpinpaditemfk")
	public Tpinpaditem getTpinpaditem() {
		return tpinpaditem;
	}

	public void setTpinpaditem(Tpinpaditem tpinpaditem) {
		this.tpinpaditem = tpinpaditem;
	}

	@ManyToOne
	@JoinColumn(name = "tsecuritiesitemfk")
	public Tsecuritiesitem getTsecuritiesitem() {
		return tsecuritiesitem;
	}

	public void setTsecuritiesitem(Tsecuritiesitem tsecuritiesitem) {
		this.tsecuritiesitem = tsecuritiesitem;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPinpadtype() {
		return pinpadtype;
	}

	public void setPinpadtype(String pinpadtype) {
		this.pinpadtype = pinpadtype;
	}

	public String getPinpadmemo() {
		return pinpadmemo;
	}

	public void setPinpadmemo(String pinpadmemo) {
		this.pinpadmemo = pinpadmemo;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Integer getNumerator() {
		return numerator;
	}

	public void setNumerator(Integer numerator) {
		this.numerator = numerator;
	}
}
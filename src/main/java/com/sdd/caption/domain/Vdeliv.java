package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
public class Vdeliv implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchcode;
	private String branchname;
	private String memo;
	private String nopaket;
	private Date orderdate;
	private String productcode;
	private String productgroup;
	private String productname;
	private String producttype;
	private Integer quantity;
	private String status;
	private Tdelivery tdelivery;
	private Date prodfinishdate;
	private Integer dlvsla;
	
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchcode() {
		return this.branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return this.branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNopaket() {
		return nopaket;
	}


	public void setNopaket(String nopaket) {
		this.nopaket = nopaket;
	}

	@Id
	public Date getOrderdate() {
		return orderdate;
	}


	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProducttype() {
		return this.producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}


	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	//bi-directional many-to-one association to Tdelivery
	@Id
	@ManyToOne
	@JoinColumn(name="tdeliveryfk")
	public Tdelivery getTdelivery() {
		return this.tdelivery;
	}

	public void setTdelivery(Tdelivery tdelivery) {
		this.tdelivery = tdelivery;
	}

	public Date getProdfinishdate() {
		return prodfinishdate;
	}

	public void setProdfinishdate(Date prodfinishdate) {
		this.prodfinishdate = prodfinishdate;
	}
	
	@Id
	public Integer getDlvsla() {
		return dlvsla;
	}
	public void setDlvsla(Integer dlvsla) {
		this.dlvsla = dlvsla;
	}
	
}

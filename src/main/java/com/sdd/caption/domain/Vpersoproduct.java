package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vpersoproduct implements Serializable {
	private static final long serialVersionUID = 1;
	private Integer tpersopk;
	private Tembossproduct tembossproduct;
	private Date orderdate;
	private String productgroup;
	private String status;
	private Integer totaldata;

	@Id
	public Integer getTpersopk() {
		return tpersopk;
	}

	public void setTpersopk(Integer tpersopk) {
		this.tpersopk = tpersopk;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}

	public Integer getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

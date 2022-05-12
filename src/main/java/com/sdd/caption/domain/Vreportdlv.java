package com.sdd.caption.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Vreportdlv {
	
	private Integer tdeliverydatapk;
	private String dlvid;
	private String productgroup;
	private String branchid;
	private String branchname;
	private String regionname;
	private String nopaket;
	private String productcode;
	private String productname;
	private String vendorcode;
	private String isurgent;
	private String penerima;
	private Date tglterima;
	private Date processtime;
	private Date orderdate;
	private Integer totaldata;
	private Integer quantity;
	
	
	@Id
	public Integer getTdeliverydatapk() {
		return tdeliverydatapk;
	}
	public void setTdeliverydatapk(Integer tdeliverydatapk) {
		this.tdeliverydatapk = tdeliverydatapk;
	}
	
	@Column(length=20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDlvid() {
		return dlvid;
	}
	public void setDlvid(String dlvid) {
		this.dlvid = dlvid;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}
	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
	@Column(length=20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNopaket() {
		return nopaket;
	}
	public void setNopaket(String nopaket) {
		this.nopaket = nopaket;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsurgent() {
		return isurgent;
	}
	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPenerima() {
		return penerima;
	}
	public void setPenerima(String penerima) {
		this.penerima = penerima;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getTglterima() {
		return tglterima;
	}
	public void setTglterima(Date tglterima) {
		this.tglterima = tglterima;
	}
	
	
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}
	
	/*public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}*/
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getProcesstime() {
		return processtime;
	}
	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

}

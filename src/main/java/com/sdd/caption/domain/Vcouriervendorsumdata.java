package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Vcouriervendorsumdata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productgroup;
	private String vendorcode;
	private String vendorname;
	private Integer totalmanifest;
	private Integer totaldata;
	
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}
	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}
	@Id
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Integer getTotalmanifest() {
		return totalmanifest;
	}
	public void setTotalmanifest(Integer totalmanifest) {
		this.totalmanifest = totalmanifest;
	}
	public Integer getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}	
		
}

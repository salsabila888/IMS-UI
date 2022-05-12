package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import java.util.Date;
/**
 * The persistent class for the mregion database table.
 * 
 */
@Entity
@Table(name="mregion")
@NamedQuery(name="Mregion.findAll", query="SELECT m FROM Mregion m")
public class Mregion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mregionpk;
	private Date lastupdated;
	private String regioncode;
	private String regionid;
	private String regionname;
	private String updatedby;

	public Mregion() {
	}


	@Id
	@SequenceGenerator(name="MREGION_MREGIONPK_GENERATOR", sequenceName = "MREGION_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MREGION_MREGIONPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getMregionpk() {
		return this.mregionpk;
	}

	public void setMregionpk(Integer mregionpk) {
		this.mregionpk = mregionpk;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	@Column(length=10)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegioncode() {
		return this.regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}


	@Column(length=5)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegionid() {
		return this.regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}


	@Column(length=70)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRegionname() {
		return this.regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}


	@Column(length=15)
	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}
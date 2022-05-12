package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tfilebranchactv database table.
 * 
 */
@Entity
@Table(name="tfilebranchactv")
@NamedQuery(name="Tfilebranchactv.findAll", query="SELECT t FROM Tfilebranchactv t")
public class Tfilebranchactv implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tfilebranchactvpk;
	private Date filegettime;
	private String filename;

	public Tfilebranchactv() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getTfilebranchactvpk() {
		return this.tfilebranchactvpk;
	}

	public void setTfilebranchactvpk(Integer tfilebranchactvpk) {
		this.tfilebranchactvpk = tfilebranchactvpk;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getFilegettime() {
		return this.filegettime;
	}

	public void setFilegettime(Date filegettime) {
		this.filegettime = filegettime;
	}


	@Column(length=100)
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
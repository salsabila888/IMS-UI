package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the musergroupnotif database table.
 * 
 */@Entity
 @Table(name = "musergroupnotif")
 @NamedQuery(name = "Musergroupnotif.findAll", query = "SELECT m FROM Musergroupnotif m")
 public class Musergroupnotif implements Serializable {
 	private static final long serialVersionUID = 1L;
 	private Integer musergroupnotifpk;
 	private String notifid;
 	private String notiftype;
 	private Musergroup musergroup; 	
 

 	public Musergroupnotif() {
 	}

 	@Id
 	@SequenceGenerator(name = "MUSERGROUPNOTIF_MUSERGROUPNOTIFPK_GENERATOR", sequenceName = "MUSERGROUPNOTIF_SEQ", initialValue = 1, allocationSize = 1)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUSERGROUPNOTIF_MUSERGROUPNOTIFPK_GENERATOR")
 	@Column(unique = true, nullable = false)
 	public Integer getMusergroupnotifpk() {
 		return this.musergroupnotifpk;
 	}

 	public void setMusergroupnotifpk(Integer musergroupnotifpk) {
 		this.musergroupnotifpk = musergroupnotifpk;
 	}
 	 	
 	
 	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getNotifid() {
		return notifid;
	}

	public void setNotifid(String notifid) {
		this.notifid = notifid;
	}
	
	
	@Type(type = "com.sdd.utils.usertype.TrimUserType")
	public String getNotiftype() {
		return notiftype;
	}

	public void setNotiftype(String notiftype) {
		this.notiftype = notiftype;
	}

	@ManyToOne
	@JoinColumn(name = "musergroupfk")
	public Musergroup getMusergroup() {
		return musergroup;
	}
	
	public void setMusergroup(Musergroup musergroup) {
		this.musergroup = musergroup;
	}
 		
 }
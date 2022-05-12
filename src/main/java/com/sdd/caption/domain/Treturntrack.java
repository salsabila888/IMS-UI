package com.sdd.caption.domain;

import java.io.Serializable;
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

@Entity
@Table(name="treturntrack")
@NamedQuery(name="Treturntrack.findAll", query="SELECT t FROM Treturntrack t")
public class Treturntrack implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer treturntrackpk;
	private Treturn treturn;
	private Date tracktime;
	private String trackstatus;
	private String trackdesc;
	
	public Treturntrack() {
	}
	
	@Id
	@SequenceGenerator(name="TRETURNTRACK_TRETURNTRACKPK_GENERATOR", sequenceName = "TRETURNTRACK_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRETURNTRACK_TRETURNTRACKPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTreturntrackpk() {
		return treturntrackpk;
	}
	public void setTreturntrackpk(Integer treturntrackpk) {
		this.treturntrackpk = treturntrackpk;
	}
	
	//bi-directional many-to-one association to Treturn
	@ManyToOne
	@JoinColumn(name="treturnfk")
	public Treturn getTreturn() {
		return treturn;
	}
	public void setTreturn(Treturn treturn) {
		this.treturn = treturn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getTracktime() {
		return tracktime;
	}
	public void setTracktime(Date tracktime) {
		this.tracktime = tracktime;
	}
	public String getTrackstatus() {
		return trackstatus;
	}
	public void setTrackstatus(String trackstatus) {
		this.trackstatus = trackstatus;
	}
	public String getTrackdesc() {
		return trackdesc;
	}
	public void setTrackdesc(String trackdesc) {
		this.trackdesc = trackdesc;
	}
	

}

package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tuneod database table.
 * 
 */
@Entity
@Table(name = "tuneod")
@NamedQuery(name="Tuneod.findAll", query="SELECT t FROM Tuneod t")
public class Tuneod implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tuneodpk;
	private Date eoddate;

	public Tuneod() {
	}

	@Id
	@SequenceGenerator(name = "TUNEOD_TUNEODPK_GENERATOR", sequenceName = "TUNEOD_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TUNEOD_TUNEODPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTuneodpk() {
		return tuneodpk;
	}
	public void setTuneodpk(Integer tuneodpk) {
		this.tuneodpk = tuneodpk;
	}
	public Date getEoddate() {
		return eoddate;
	}
	public void setEoddate(Date eoddate) {
		this.eoddate = eoddate;
	}

}
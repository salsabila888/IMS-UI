package com.sdd.caption.domain;

import java.io.Serializable;

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

@Entity
@Table(name="trepairitem")
@NamedQuery(name="Trepairitem.findAll", query="SELECT t FROM Trepairitem t")
public class Trepairitem implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer trepairitempk;
	private Trepair trepair;
	private String itemno;
	private String itemstatus;
	
	public Trepairitem() {
	}
	
	@Id
	@SequenceGenerator(name="TREPAIRITEM_TREPAIRITEMPK_GENERATOR", sequenceName = "TREPAIRITEM_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TREPAIRITEM_TREPAIRITEMPK_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getTrepairitempk() {
		return trepairitempk;
	}
	public void setTrepairitempk(Integer trepairitempk) {
		this.trepairitempk = trepairitempk;
	}
	
	//bi-directional many-to-one association to Trepair
	@ManyToOne
	@JoinColumn(name="trepairfk")
	public Trepair getTrepair() {
		return trepair;
	}
	public void setTrepair(Trepair trepair) {
		this.trepair = trepair;
	}
	public String getItemno() {
		return itemno;
	}
	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	public String getItemstatus() {
		return itemstatus;
	}
	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}
	

}

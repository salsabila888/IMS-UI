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
@Table(name="tswitchitem")
@NamedQuery(name="Tswitchitem.findAll", query="SELECT t FROM Tswitchitem t")
public class Tswitchitem implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer tswitchitempk;
	private String itemno;
	private Tswitch tswitch;
	
	public Tswitchitem() {
	}
	
	
	@Id
	@SequenceGenerator(name="TSWITCHITEM_TSWITCHITEMPK_GENERATOR", sequenceName = "TSWITCHITEM_SEQ", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TSWITCHITEM_TSWITCHITEMPK_GENERATOR")
	@Column(unique=true, nullable=false)
	
	public Integer getTswitchitempk() {
		return tswitchitempk;
	}
	public void setTswitchitempk(Integer tswitchitempk) {
		this.tswitchitempk = tswitchitempk;
	}
	public String getItemno() {
		return itemno;
	}
	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	
	//bi-directional many-to-one association to Tswitch
	@ManyToOne
	@JoinColumn(name="tswitchfk")
	public Tswitch getTswitch() {
		return tswitch;
	}
	public void setTswitch(Tswitch tswitch) {
		this.tswitch = tswitch;
	}
	
}

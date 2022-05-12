package com.sdd.caption.domain;

import javax.persistence.*;

import java.util.Date;


@Entity
public class Vuneod {
	
	
	private Date trxdate;
	
	@Id
	public Date getTrxdate() {
		return trxdate;
	}
	public void setTrxdate(Date trxdate) {
		this.trxdate = trxdate;
	}
}

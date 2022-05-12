package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the tderivatifdata database table.
 * 
 */
@Entity
@Table(name = "tderivatifdata")
@NamedQuery(name = "Tderivatifdata.findAll", query = "SELECT t FROM Tderivatifdata t")
public class Tderivatifdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tderivatifdatapk;
	private String cardno;
	private Date orderdate;
	private String status;
	private String rejectmemo;
	private Tderivatif tderivatif;
	private Tderivatifproduct tderivatifproduct;
	private Tembossdata tembossdata;

	public Tderivatifdata() {
	}

	@Id
	@SequenceGenerator(name = "TDERIVATIFDATA_TDERIVATIFDATAPK_GENERATOR", sequenceName = "TDERIVATIFDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDERIVATIFDATA_TDERIVATIFDATAPK_GENERATOR")
	public Integer getTderivatifdatapk() {
		return this.tderivatifdatapk;
	}

	public void setTderivatifdatapk(Integer tderivatifdatapk) {
		this.tderivatifdatapk = tderivatifdatapk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getRejectmemo() {
		return rejectmemo;
	}

	public void setRejectmemo(String rejectmemo) {
		this.rejectmemo = rejectmemo;
	}

	// bi-directional many-to-one association to Tderivatif
	@ManyToOne
	@JoinColumn(name = "tderivatiffk")
	public Tderivatif getTderivatif() {
		return tderivatif;
	}

	public void setTderivatif(Tderivatif tderivatif) {
		this.tderivatif = tderivatif;
	}

	// bi-directional many-to-one association to Tderivatifproduct
	@ManyToOne
	@JoinColumn(name = "tderivatifproductfk")
	public Tderivatifproduct getTderivatifproduct() {
		return this.tderivatifproduct;
	}

	public void setTderivatifproduct(Tderivatifproduct tderivatifproduct) {
		this.tderivatifproduct = tderivatifproduct;
	}

	@ManyToOne
	@JoinColumn(name = "tembossdatafk")
	public Tembossdata getTembossdata() {
		return this.tembossdata;
	}

	public void setTembossdata(Tembossdata tembossdata) {
		this.tembossdata = tembossdata;
	}
	
	
	public static Comparator<Tderivatifdata> productComparator = new Comparator<Tderivatifdata>() {
		
		public int compare(Tderivatifdata obj1, Tderivatifdata obj2) {
			int c = 0;
			
			Integer product1 = obj1.getTembossdata().getMproduct().getMproductpk();
			Integer product2 = obj2.getTembossdata().getMproduct().getMproductpk();
			
			c = product1.compareTo(product2);
			if (c == 0)
				c = obj1.getTembossdata().getMproduct().getMproductpk().compareTo(obj2.getTembossdata().getMproduct().getMproductpk());
			
			return c;
		}
	
	};
	
	public static Comparator<Tderivatifdata> dateComparator = new Comparator<Tderivatifdata>() {
		
		public int compare(Tderivatifdata obj1, Tderivatifdata obj2) {
			int c = 0;
			
			Date date1 = obj1.getOrderdate();
			Date date2 = obj2.getOrderdate();
			
			c = date1.compareTo(date2);
			if (c == 0)
				c = obj1.getOrderdate().compareTo(obj2.getOrderdate());
			
			return c;
		}
	
	};
}
package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the tperso database table.
 * 
 */
@Entity
@Table(name = "tperso")
@NamedQuery(name = "Tperso.findAll", query = "SELECT t FROM Tperso t")
public class Tperso implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpersopk;
	private String decisionby;
	private String decisionmemo;
	private Date decisiontime;
	private String isgetallpaket;
	private String memo;
	private Date orderdate;
	private String persoid;
	private String persofinishby;
	private Date persofinishtime;
	private String persostartby;
	private Date persostarttime;
	private String status;
	private Integer totaldata;
	private Integer totalpaket;
	private Tembossproduct tembossproduct;
	private Mproduct mproduct;
	private Mpersovendor mpersovendor;
	private Tderivatifproduct tderivatifproduct;
	private Tpersoupload tpersoupload;

	public Tperso() {
	}

	@Id
	@SequenceGenerator(name = "TPERSO_TPERSOPK_GENERATOR", sequenceName = "TPERSO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPERSO_TPERSOPK_GENERATOR")
	public Integer getTpersopk() {
		return this.tpersopk;
	}

	public void setTpersopk(Integer tpersopk) {
		this.tpersopk = tpersopk;
	}

	public String getDecisionby() {
		return this.decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDecisionmemo() {
		return decisionmemo;
	}

	public void setDecisionmemo(String decisionmemo) {
		this.decisionmemo = decisionmemo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return this.decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	public String getIsgetallpaket() {
		return isgetallpaket;
	}

	public void setIsgetallpaket(String isgetallpaket) {
		this.isgetallpaket = isgetallpaket;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPersoid() {
		return this.persoid;
	}

	public void setPersoid(String persoid) {
		this.persoid = persoid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPersofinishby() {
		return persofinishby;
	}

	public void setPersofinishby(String persofinishby) {
		this.persofinishby = persofinishby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPersofinishtime() {
		return persofinishtime;
	}

	public void setPersofinishtime(Date persofinishtime) {
		this.persofinishtime = persofinishtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPersostartby() {
		return persostartby;
	}

	public void setPersostartby(String persostartby) {
		this.persostartby = persostartby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPersostarttime() {
		return persostarttime;
	}

	public void setPersostarttime(Date persostarttime) {
		this.persostarttime = persostarttime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	public Integer getTotalpaket() {
		return totalpaket;
	}

	public void setTotalpaket(Integer totalpaket) {
		this.totalpaket = totalpaket;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return this.tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}

	// bi-directional many-to-one association to Mproduct
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "mpersovendorfk")
	public Mpersovendor getMpersovendor() {
		return mpersovendor;
	}

	public void setMpersovendor(Mpersovendor mpersovendor) {
		this.mpersovendor = mpersovendor;
	}

	// bi-directional many-to-one association to Tembossproduct
	@ManyToOne
	@JoinColumn(name = "tderivatifproductfk")
	public Tderivatifproduct getTderivatifproduct() {
		return tderivatifproduct;
	}

	public void setTderivatifproduct(Tderivatifproduct tderivatifproduct) {
		this.tderivatifproduct = tderivatifproduct;
	}

	// bi-directional many-to-one association to Tpersoupload
	@ManyToOne
	@JoinColumn(name = "tpersouploadfk")
	public Tpersoupload getTpersoupload() {
		return tpersoupload;
	}

	public void setTpersoupload(Tpersoupload tpersoupload) {
		this.tpersoupload = tpersoupload;
	}

	public static Comparator<Tperso> fieldComparator = new Comparator<Tperso>() {

		public int compare(Tperso obj1, Tperso obj2) {
			int c = 0;

			Date id1 = obj1.getOrderdate();
			Date id2 = obj2.getOrderdate();

			c = id1.compareTo(id2);
			if (c == 0)
				c = obj1.getTembossproduct().getMproduct().getMproducttype().getProducttype()
						.compareTo(obj2.getTembossproduct().getMproduct().getMproducttype().getProducttype());
			if (c == 0)
				c = obj1.getMproduct().getProductname().compareTo(obj2.getMproduct().getProductname());

			// ascending order
			// return id1.compareTo(id2);
			return c;
		}

	};

	public static Comparator<Tperso> fieldComparatorProductCode = new Comparator<Tperso>() {

		public int compare(Tperso obj1, Tperso obj2) {
			int c = 0;

			String id1 = obj1.getMproduct().getProductcode();
			String id2 = obj2.getMproduct().getProductcode();

			c = id1.compareTo(id2);
			if (c == 0)
				c = obj1.getTembossproduct().getMproduct().getMproducttype().getProducttype()
						.compareTo(obj2.getMproduct().getProductcode());

			// ascending order
			// return id1.compareTo(id2);
			return c;
		}

	};

}
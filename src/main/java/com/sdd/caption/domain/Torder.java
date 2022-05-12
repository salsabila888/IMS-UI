package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the torder database table.
 * 
 */
@Entity
@Table(name = "torder")
@NamedQuery(name = "Torder.findAll", query = "SELECT t FROM Torder t")
public class Torder implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer torderpk;
	private String decisionby;
	private Date decisiontime;
	private String insertedby;
	private Date inserttime;
	private String orderid;
	private Date orderdate;
	private Integer orderlevel;
	private String orderoutlet;
	private String ordertype;
	private String productgroup;
	private Integer itemqty;
	private Integer totalproses;
	private String status;
	private Mproduct mproduct;
	private Mbranch mbranch;

	public Torder() {
	}

	@Id
	@SequenceGenerator(name = "TORDER_TORDERPK_GENERATOR", sequenceName = "TORDER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TORDER_TORDERPK_GENERATOR")
	public Integer getTorderpk() {
		return this.torderpk;
	}

	public void setTorderpk(Integer torderpk) {
		this.torderpk = torderpk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDecisionby() {
		return this.decisionby;
	}

	public void setDecisionby(String decisionby) {
		this.decisionby = decisionby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDecisiontime() {
		return this.decisiontime;
	}

	public void setDecisiontime(Date decisiontime) {
		this.decisiontime = decisiontime;
	}

	public String getInsertedby() {
		return insertedby;
	}

	public void setInsertedby(String insertedby) {
		this.insertedby = insertedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrderoutlet() {
		return orderoutlet;
	}

	public void setOrderoutlet(String orderoutlet) {
		this.orderoutlet = orderoutlet;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getOrderlevel() {
		return orderlevel;
	}

	public void setOrderlevel(Integer orderlevel) {
		this.orderlevel = orderlevel;
	}
	
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getItemqty() {
		return this.itemqty;
	}

	public void setItemqty(Integer itemqty) {
		this.itemqty = itemqty;
	}

	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	public static Comparator<Torder> fieldComparator = new Comparator<Torder>() {

		public int compare(Torder obj1, Torder obj2) {
			int c = 0;

			Date id1 = obj1.getInserttime();
			Date id2 = obj2.getInserttime();

			c = id1.compareTo(id2);
			if (c == 0)
				c = obj1.getMproduct().getMproducttype().getProducttype()
						.compareTo(obj2.getMproduct().getMproducttype().getProducttype());
			if (c == 0)
				c = obj1.getMproduct().getProductname().compareTo(obj2.getMproduct().getProductname());

			// ascending order
			// return id1.compareTo(id2);
			return c;
		}

	};

	public Integer getTotalproses() {
		return totalproses;
	}

	public void setTotalproses(Integer totalproses) {
		this.totalproses = totalproses;
	}

}
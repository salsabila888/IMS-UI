package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the torderdata database table.
 * 
 */
@Entity
@Table(name = "torderdata")
@NamedQuery(name = "Torderdata.findAll", query = "SELECT t FROM Torderdata t")
public class Torderdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer torderdatapk;
	private String branchid;
	private String branchname;
	private String cardno;
	private String isinstant;
	private String klncode;
	private String klnname;
	private String nameoncard;
	private String nameonid;
	private Mbranch mbranch;
	private Mproduct mproduct;
	private Date orderdate;
	private String productcode;
	private String seqno;
	private Tembossbranch tembossbranch;
	private Tembossproduct tembossproduct;
	private Torder torder;

	public Torderdata() {
	}

	@Id
	@SequenceGenerator(name = "TORDERDATA_TORDERDATAPK_GENERATOR", sequenceName = "TORDERDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TORDERDATA_TORDERDATAPK_GENERATOR")
	public Integer getTorderdatapk() {
		return this.torderdatapk;
	}

	public void setTorderdatapk(Integer torderdatapk) {
		this.torderdatapk = torderdatapk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getBranchname() {
		return this.branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsinstant() {
		return this.isinstant;
	}

	public void setIsinstant(String isinstant) {
		this.isinstant = isinstant;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKlncode() {
		return this.klncode;
	}

	public void setKlncode(String klncode) {
		this.klncode = klncode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getKlnname() {
		return this.klnname;
	}

	public void setKlnname(String klnname) {
		this.klnname = klnname;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNameoncard() {
		return this.nameoncard;
	}

	public void setNameoncard(String nameoncard) {
		this.nameoncard = nameoncard;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNameonid() {
		return this.nameonid;
	}

	public void setNameonid(String nameonid) {
		this.nameonid = nameonid;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getProductcode() {
		return this.productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getSeqno() {
		return this.seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "torderfk")
	public Torder getTorder() {
		return this.torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "mproductfk")
	public Mproduct getMproduct() {
		return mproduct;
	}

	public void setMproduct(Mproduct mproduct) {
		this.mproduct = mproduct;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

	// bi-directional many-to-one association to Torder
	@ManyToOne
	@JoinColumn(name = "tembossproductfk")
	public Tembossproduct getTembossproduct() {
		return tembossproduct;
	}

	public void setTembossproduct(Tembossproduct tembossproduct) {
		this.tembossproduct = tembossproduct;
	}
	
public static Comparator<Torderdata> fieldComparator = new Comparator<Torderdata>() {
		
		public int compare(Torderdata obj1, Torderdata obj2) {
			int c = 0;
			
			Date id1 = obj1.getOrderdate();
			Date id2 = obj2.getOrderdate();
			
			c = id1.compareTo(id2);
			if (c == 0)
				c = obj1.getMproduct().getMproducttype().getProducttype().compareTo(obj2.getMproduct().getMproducttype().getProducttype());
			if (c == 0)
				c = obj1.getMproduct().getProductname().compareTo(obj2.getMproduct().getProductname());
			
			//ascending order
			//return id1.compareTo(id2);
			return c;
		}
	
	};

}
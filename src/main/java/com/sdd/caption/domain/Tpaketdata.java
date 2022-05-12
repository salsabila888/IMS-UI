package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the tpaketdata database table.
 * 
 */
@Entity
@Table(name = "tpaketdata")
@NamedQuery(name = "Tpaketdata.findAll", query = "SELECT t FROM Tpaketdata t")
public class Tpaketdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpaketdatapk;
	private String isdlv;
	private String nopaket;
	private Date orderdate;
	private String paketstartby;
	private Date paketstarttime;
	private String paketfinishby;
	private Date paketfinishtime;
	private String productgroup;
	private Integer quantity;
	private String status;
	private Tpaket tpaket;
	private Mbranch mbranch;
	private Tembossbranch tembossbranch;
	private Tpinmailerbranch tpinmailerbranch;

	public Tpaketdata() {
	}

	@Id
	@SequenceGenerator(name = "TPAKETDATA_TPAKETDATAPK_GENERATOR", sequenceName = "TPAKETDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPAKETDATA_TPAKETDATAPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTpaketdatapk() {
		return this.tpaketdatapk;
	}

	public void setTpaketdatapk(Integer tpaketdatapk) {
		this.tpaketdatapk = tpaketdatapk;
	}

	public String getIsdlv() {
		return isdlv;
	}

	public void setIsdlv(String isdlv) {
		this.isdlv = isdlv;
	}

	@Column(length = 15)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getNopaket() {
		return this.nopaket;
	}

	public void setNopaket(String nopaket) {
		this.nopaket = nopaket;
	}

	@Temporal(TemporalType.DATE)
	public Date getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getPaketstartby() {
		return paketstartby;
	}

	public void setPaketstartby(String paketstartby) {
		this.paketstartby = paketstartby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPaketstarttime() {
		return paketstarttime;
	}

	public void setPaketstarttime(Date paketstarttime) {
		this.paketstarttime = paketstarttime;
	}

	public String getPaketfinishby() {
		return paketfinishby;
	}

	public void setPaketfinishby(String paketfinishby) {
		this.paketfinishby = paketfinishby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPaketfinishtime() {
		return paketfinishtime;
	}

	public void setPaketfinishtime(Date paketfinishtime) {
		this.paketfinishtime = paketfinishtime;
	}

	@Column(length = 3)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// bi-directional many-to-one association to Tpaket
	@ManyToOne
	@JoinColumn(name = "tpaketfk")
	public Tpaket getTpaket() {
		return this.tpaket;
	}

	public void setTpaket(Tpaket tpaket) {
		this.tpaket = tpaket;
	}

	// bi-directional many-to-one association to Tpaket
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}

	// bi-directional many-to-one association to Tderivatifproduct
	@ManyToOne
	@JoinColumn(name = "tembossbranchfk")
	public Tembossbranch getTembossbranch() {
		return tembossbranch;
	}

	public void setTembossbranch(Tembossbranch tembossbranch) {
		this.tembossbranch = tembossbranch;
	}

	// bi-directional many-to-one association to Tderivatifproduct
	@ManyToOne
	@JoinColumn(name = "tpinmailerbranchfk")
	public Tpinmailerbranch getTpinmailerbranch() {
		return tpinmailerbranch;
	}

	public void setTpinmailerbranch(Tpinmailerbranch tpinmailerbranch) {
		this.tpinmailerbranch = tpinmailerbranch;
	}
	
	public static Comparator<Tpaketdata> branchidComparator = new Comparator<Tpaketdata>() {

		public int compare(Tpaketdata obj1, Tpaketdata obj2) {

			String id1 = obj1.getMbranch().getBranchid();
			String id2 = obj2.getMbranch().getBranchid();

			// ascending order
			return id1.compareTo(id2);

			// descending order
			// return id2.compareTo(id1);
		}

	};

}
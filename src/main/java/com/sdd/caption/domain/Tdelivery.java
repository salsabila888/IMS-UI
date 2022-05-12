package com.sdd.caption.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Comparator;
import java.util.Date;

/**
 * The persistent class for the tdelivery database table.
 * 
 */
@Entity
@Table(name = "tdelivery")
@NamedQuery(name = "Tdelivery.findAll", query = "SELECT t FROM Tdelivery t")
public class Tdelivery implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tdeliverypk;
	private String awb;
	private String couriercode;
	private String dlvid;
	private String isproductphoto;
	private String isurgent;
	private String lettertype;
	private String penerima;
	private String processedby;
	private Date processtime;
	private String productgroup;	
	private String status;
	private String memo;
	private Date tglterima;
	private Integer totaldata;
	private Tdeliverycourier tdeliverycourier;
	private Mcouriervendor mcouriervendor;
	private Mbranch mbranch;

	public Tdelivery() {
	}

	@Id
	@SequenceGenerator(name = "TDELIVERY_TDELIVERYPK_GENERATOR", sequenceName = "TDELIVERY_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDELIVERY_TDELIVERYPK_GENERATOR")
	@Column(unique = true, nullable = false)
	public Integer getTdeliverypk() {
		return this.tdeliverypk;
	}

	public void setTdeliverypk(Integer tdeliverypk) {
		this.tdeliverypk = tdeliverypk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getAwb() {
		return this.awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getCouriercode() {
		return this.couriercode;
	}

	public void setCouriercode(String couriercode) {
		this.couriercode = couriercode;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getDlvid() {
		return this.dlvid;
	}

	public void setDlvid(String dlvid) {
		this.dlvid = dlvid;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsproductphoto() {
		return isproductphoto;
	}

	public void setIsproductphoto(String isproductphoto) {
		this.isproductphoto = isproductphoto;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getIsurgent() {
		return this.isurgent;
	}

	public void setIsurgent(String isurgent) {
		this.isurgent = isurgent;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getLettertype() {
		return this.lettertype;
	}

	public void setLettertype(String lettertype) {
		this.lettertype = lettertype;
	}

	public String getPenerima() {
		return this.penerima;
	}

	public void setPenerima(String penerima) {
		this.penerima = penerima;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProcessedby() {
		return this.processedby;
	}

	public void setProcessedby(String processedby) {
		this.processedby = processedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getProcesstime() {
		return this.processtime;
	}

	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getProductgroup() {
		return this.productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	public Date getTglterima() {
		return this.tglterima;
	}

	public void setTglterima(Date tglterima) {
		this.tglterima = tglterima;
	}

	public Integer getTotaldata() {
		return this.totaldata;
	}

	public void setTotaldata(Integer totaldata) {
		this.totaldata = totaldata;
	}

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "tdeliverycourierfk")
	public Tdeliverycourier getTdeliverycourier() {
		return this.tdeliverycourier;
	}

	public void setTdeliverycourier(Tdeliverycourier tdeliverycourier) {
		this.tdeliverycourier = tdeliverycourier;
	}

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "mcouriervendorfk")
	public Mcouriervendor getMcouriervendor() {
		return mcouriervendor;
	}

	public void setMcouriervendor(Mcouriervendor mcouriervendor) {
		this.mcouriervendor = mcouriervendor;
	}

	// bi-directional many-to-one association to Tdeliverycourier
	@ManyToOne
	@JoinColumn(name = "mbranchfk")
	public Mbranch getMbranch() {
		return mbranch;
	}

	public void setMbranch(Mbranch mbranch) {
		this.mbranch = mbranch;
	}
	
	public int compareTo(Tdelivery o) {
		int compareId = 0;
		try {
			compareId = Integer.parseInt(((Tdelivery) o).getMbranch().getBranchid());
		} catch (Exception e) {
			return 0;
		}
		
		//ascending order
		return Integer.parseInt(this.mbranch.getBranchid()) - compareId;
	}
	
	public static Comparator<Tdelivery> branchidComparator = new Comparator<Tdelivery>() {
	
	public int compare(Tdelivery obj1, Tdelivery obj2) {
	
	String id1 = obj1.getMbranch().getBranchid();
	String id2 = obj2.getMbranch().getBranchid();
	
	//ascending order
	return id1.compareTo(id2);
	
	//descending order
	//return id2.compareTo(id1);
	}
	
	};

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
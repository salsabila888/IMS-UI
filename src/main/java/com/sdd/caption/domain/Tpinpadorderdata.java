package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the tpinpadoderdata database table.
 * 
 */
@Entity
@Table(name = "tpinpadorderdata")
@NamedQuery(name = "Tpinpadorderdata.findAll", query = "SELECT t FROM Tpinpadorderdata t")
public class Tpinpadorderdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tpinpadorderdatapk;
	private String status;
	private String pinpadtype;
	private String tid;
	private String memo;
	private Torder torder;
	private Tpinpaditem tpinpaditem;

	public Tpinpadorderdata() {
	}

	@Id
	@SequenceGenerator(name = "TPINPADORDERDATA_TPINPADORDERDATAPK_GENERATOR", sequenceName = "TPINPADORDERDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TPINPADORDERDATA_TPINPADORDERDATAPK_GENERATOR")
	public Integer getTpinpadorderdatapk() {
		return this.tpinpadorderdatapk;
	}

	public void setTpinpadorderdatapk(Integer tpinpadorderdatapk) {
		this.tpinpadorderdatapk = tpinpadorderdatapk;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 20)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(length = 100)
	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getPinpadtype() {
		return pinpadtype;
	}

	public void setPinpadtype(String pinpadtype) {
		this.pinpadtype = pinpadtype;
	}

	// bi-directional many-to-one association to Tpinpadorder
	@ManyToOne
	@JoinColumn(name = "torderfk", nullable = false)
	public Torder getTorder() {
		return this.torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}

	// bi-directional many-to-one association to Tpinpaditem
	@ManyToOne
	@JoinColumn(name = "tpinpaditemfk", nullable = false)
	public Tpinpaditem getTpinpaditem() {
		return this.tpinpaditem;
	}

	public void setTpinpaditem(Tpinpaditem tpinpaditem) {
		this.tpinpaditem = tpinpaditem;
	}

	public static Comparator<Torderdata> fieldComparator = new Comparator<Torderdata>() {

		public int compare(Torderdata obj1, Torderdata obj2) {
			int c = 0;

			Date id1 = obj1.getOrderdate();
			Date id2 = obj2.getOrderdate();

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
}
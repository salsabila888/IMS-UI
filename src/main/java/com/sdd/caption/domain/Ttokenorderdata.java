package com.sdd.caption.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the ttokenorderdata database table.
 * 
 */
@Entity
@Table(name = "ttokenorderdata")
@NamedQuery(name="Ttokenorderdata.findAll", query="SELECT t FROM Ttokenorderdata t")
public class Ttokenorderdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ttokenorderdatapk;
	private String status;
	private Torder torder;
	private Ttokenitem ttokenitem;

	public Ttokenorderdata() {
	}


	@Id
	@SequenceGenerator(name = "TTOKENORDERDATA_TTOKENORDERDATAPK_GENERATOR", sequenceName = "TTOKENORDERDATA_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TTOKENORDERDATA_TTOKENORDERDATAPK_GENERATOR")
	public Integer getTtokenorderdatapk() {
		return this.ttokenorderdatapk;
	}

	public void setTtokenorderdatapk(Integer ttokenorderdatapk) {
		this.ttokenorderdatapk = ttokenorderdatapk;
	}


	@Type(type = "com.sdd.utils.usertype.TrimUpperCaseUserType")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to Ttokenorder
	@ManyToOne
	@JoinColumn(name="torderfk", nullable = false)
	public Torder getTorder() {
		return this.torder;
	}

	public void setTorder(Torder torder) {
		this.torder = torder;
	}


	//bi-directional many-to-one association to Ttokenitem
	@ManyToOne
	@JoinColumn(name="ttokenitemfk", nullable = false)
	public Ttokenitem getTtokenitem() {
		return this.ttokenitem;
	}

	public void setTtokenitem(Ttokenitem ttokenitem) {
		this.ttokenitem = ttokenitem;
	}
	
public static Comparator<Ttokenorderdata> fieldComparator = new Comparator<Ttokenorderdata>() {
		
		public int compare(Ttokenorderdata obj1, Ttokenorderdata obj2) {
			int c = 0;
			
			Date id1 = obj1.getTorder().getInserttime();
			Date id2 = obj2.getTorder().getInserttime();
			
			c = id1.compareTo(id2);
			if (c == 0)
				c = obj1.getTorder().getMproduct().getMproducttype().getProducttype().compareTo(obj2.getTorder().getMproduct().getMproducttype().getProducttype());
			if (c == 0)
				c = obj1.getTorder().getMproduct().getProductname().compareTo(obj2.getTorder().getMproduct().getProductname());
			
			//ascending order
			//return id1.compareTo(id2);
			return c;
		}
	
	};

}
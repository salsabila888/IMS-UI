package com.sdd.caption.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Vorderperso implements Serializable {
	
    private static final long serialVersionUID = 1;
    private Integer year;
    private Integer month;
    private String productorg;
    private Integer total;

    @Id
    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Id
    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Id
    public String getProductorg() {
        return this.productorg;
    }

    public void setProductorg(String productorg) {
        this.productorg = productorg;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

package com.example.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "price_tracking")
public class PriceTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price_before_update", nullable = false)
    private BigDecimal priceBeforeUpdate;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "modified_time", nullable = false)
    private Timestamp modifiedTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPriceBeforeUpdate() {
        return priceBeforeUpdate;
    }

    public void setPriceBeforeUpdate(BigDecimal priceBeforeUpdate) {
        this.priceBeforeUpdate = priceBeforeUpdate;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}

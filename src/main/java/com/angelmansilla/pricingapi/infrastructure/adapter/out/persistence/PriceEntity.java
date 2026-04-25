package com.angelmansilla.pricingapi.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brand_id", nullable = false)
    private Long brandId;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @Column(name = "price_list", nullable = false)
    private Long priceList;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Integer priority;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "curr", nullable = false)
    private String currency;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "last_update_by")
    private String lastUpdateBy;

    public Long getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getPriceList() {
        return priceList;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
package com.angelmansilla.pricingapi.infrastructure.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {
    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.brandId = :brandId
            AND p.productId = :productId
            AND p.startDate <= :applicationDate
            AND p.endDate >= :applicationDate
            """)
    List<PriceEntity> findApplicablePrices(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId);
}
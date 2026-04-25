package com.angelmansilla.pricingapi.infrastructure.adapter.out.persistence;

import com.angelmansilla.pricingapi.application.port.out.PriceRepositoryPort;
import com.angelmansilla.pricingapi.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final SpringDataPriceRepository springDataPriceRepository;

    public PriceRepositoryAdapter(SpringDataPriceRepository springDataPriceRepository) {
        this.springDataPriceRepository = springDataPriceRepository;
    }

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId) {
        return springDataPriceRepository.findApplicablePrices(applicationDate, productId, brandId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency());
    }
}
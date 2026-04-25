package com.angelmansilla.pricingapi.application.port.out;

import com.angelmansilla.pricingapi.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}
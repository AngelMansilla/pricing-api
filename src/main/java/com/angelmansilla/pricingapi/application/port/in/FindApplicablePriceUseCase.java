package com.angelmansilla.pricingapi.application.port.in;

import com.angelmansilla.pricingapi.domain.model.Price;
import java.time.LocalDateTime;

public interface FindApplicablePriceUseCase {
    Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
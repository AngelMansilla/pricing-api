package com.angelmansilla.pricingapi.application.service;

import com.angelmansilla.pricingapi.application.port.in.FindApplicablePriceUseCase;
import com.angelmansilla.pricingapi.application.port.out.PriceRepositoryPort;
import com.angelmansilla.pricingapi.domain.exception.PriceNotFoundException;
import com.angelmansilla.pricingapi.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import org.springframework.stereotype.Service;

@Service
public class FindApplicablePriceService implements FindApplicablePriceUseCase {
    private final PriceRepositoryPort priceRepositoryPort;

    public FindApplicablePriceService(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Price findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrices(applicationDate, productId, brandId)
                .stream()
                .max(Comparator.comparingInt(Price::priority))
                .orElseThrow(PriceNotFoundException::new);
    }
}
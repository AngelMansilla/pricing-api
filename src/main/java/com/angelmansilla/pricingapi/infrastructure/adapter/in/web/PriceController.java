package com.angelmansilla.pricingapi.infrastructure.adapter.in.web;

import com.angelmansilla.pricingapi.application.port.in.FindApplicablePriceUseCase;
import com.angelmansilla.pricingapi.domain.model.Price;
import com.angelmansilla.pricingapi.infrastructure.adapter.in.web.dto.PriceResponse;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final FindApplicablePriceUseCase findApplicablePriceUseCase;

    public PriceController(FindApplicablePriceUseCase findApplicablePriceUseCase) {
        this.findApplicablePriceUseCase = findApplicablePriceUseCase;
    }

    @GetMapping
    public PriceResponse findApplicablePrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId) {
        Price price = findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId);
        return toResponse(price);
    }

    private PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.startDate(),
                price.endDate(),
                price.price(),
                price.currency());
    }
}
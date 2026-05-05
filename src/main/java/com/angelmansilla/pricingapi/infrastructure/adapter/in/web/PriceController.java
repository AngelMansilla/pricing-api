package com.angelmansilla.pricingapi.infrastructure.adapter.in.web;

import com.angelmansilla.pricingapi.application.port.in.FindApplicablePriceUseCase;
import com.angelmansilla.pricingapi.domain.model.Price;
import com.angelmansilla.pricingapi.infrastructure.adapter.in.web.dto.PriceResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            @RequestParam(required = false) String applicationDate,
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String brandId) {
        LocalDateTime parsedApplicationDate = parseApplicationDate(applicationDate);
        Long parsedProductId = parseLong(productId, "productId");
        Long parsedBrandId = parseLong(brandId, "brandId");

        Price price = findApplicablePriceUseCase.findApplicablePrice(
                parsedApplicationDate,
                parsedProductId,
                parsedBrandId);
        return toResponse(price);
    }

    private LocalDateTime parseApplicationDate(String applicationDate) {
        if (applicationDate == null || applicationDate.isBlank()) {
            throw new IllegalArgumentException("applicationDate is required");
        }

        try {
            return LocalDateTime.parse(applicationDate, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "applicationDate must use ISO date-time format, for example 2020-06-14T10:00:00");
        }
    }

    private Long parseLong(String value, String parameterName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(parameterName + " is required");
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(parameterName + " must be a valid number");
        }
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

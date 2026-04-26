package com.angelmansilla.pricingapi.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import com.angelmansilla.pricingapi.application.port.out.PriceRepositoryPort;
import com.angelmansilla.pricingapi.domain.exception.PriceNotFoundException;
import com.angelmansilla.pricingapi.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindApplicablePriceServiceTest {
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.parse("2020-06-14T16:00:00");
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    @Mock
    private PriceRepositoryPort priceRepositoryPort;
    @InjectMocks
    private FindApplicablePriceService service;

    @Test
    void shouldReturnHighestPriorityPriceWhenMultiplePricesApply() {
        Price lowPriorityPrice = price(1L, 0, "35.50");
        Price highPriorityPrice = price(2L, 1, "25.45");
        when(priceRepositoryPort.findApplicablePrices(APPLICATION_DATE, PRODUCT_ID, BRAND_ID))
                .thenReturn(List.of(lowPriorityPrice, highPriorityPrice));
        Price result = service.findApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
        assertThat(result).isEqualTo(highPriorityPrice);
    }

    @Test
    void shouldThrowPriceNotFoundExceptionWhenNoPricesApply() {
        when(priceRepositoryPort.findApplicablePrices(APPLICATION_DATE, PRODUCT_ID, BRAND_ID))
                .thenReturn(List.of());
        assertThatThrownBy(() -> service.findApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage("No applicable price found for the given criteria");
    }

    @Test
    void shouldReturnOnlyPriceWhenSinglePriceApplies() {
        Price onlyPrice = price(1L, 0, "35.50");
        when(priceRepositoryPort.findApplicablePrices(APPLICATION_DATE, PRODUCT_ID, BRAND_ID))
                .thenReturn(List.of(onlyPrice));
        Price result = service.findApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
        assertThat(result).isEqualTo(onlyPrice);
    }

    private Price price(Long priceList, Integer priority, String amount) {
        return new Price(
                BRAND_ID,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                priceList,
                PRODUCT_ID,
                priority,
                new BigDecimal(amount),
                "EUR");
    }
}
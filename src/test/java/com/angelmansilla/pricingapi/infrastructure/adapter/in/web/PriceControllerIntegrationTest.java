package com.angelmansilla.pricingapi.infrastructure.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {
    private static final String PRODUCT_ID = "35455";
    private static final String BRAND_ID = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPriceListOneForJuneFourteenthAtTen() throws Exception {
        assertPrice("2020-06-14T10:00:00", 1, 35.50);
    }

    @Test
    void shouldReturnPriceListTwoForJuneFourteenthAtSixteen() throws Exception {
        assertPrice("2020-06-14T16:00:00", 2, 25.45);
    }

    @Test
    void shouldReturnPriceListOneForJuneFourteenthAtTwentyOne() throws Exception {
        assertPrice("2020-06-14T21:00:00", 1, 35.50);
    }

    @Test
    void shouldReturnPriceListThreeForJuneFifteenthAtTen() throws Exception {
        assertPrice("2020-06-15T10:00:00", 3, 30.50);
    }

    @Test
    void shouldReturnPriceListFourForJuneSixteenthAtTwentyOne() throws Exception {
        assertPrice("2020-06-16T21:00:00", 4, 38.95);
    }

    @Test
    void shouldReturnNotFoundWhenNoApplicablePriceExists() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("applicationDate", "2019-06-14T10:00:00")
                .param("productId", PRODUCT_ID)
                .param("brandId", BRAND_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No applicable price found for the given criteria"));
    }

    private void assertPrice(String applicationDate, int expectedPriceList, double expectedPrice) throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("applicationDate", applicationDate)
                .param("productId", PRODUCT_ID)
                .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
}
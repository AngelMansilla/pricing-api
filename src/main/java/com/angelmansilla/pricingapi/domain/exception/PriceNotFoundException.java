package com.angelmansilla.pricingapi.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {
        super("No applicable price found for the given criteria");
    }
}
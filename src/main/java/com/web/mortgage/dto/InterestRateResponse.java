package com.web.mortgage.dto;

import com.web.mortgage.domain.MortgageRate;

import java.math.BigDecimal;
import java.time.Instant;

public record InterestRateResponse (
        Integer maturityPeriod,
        BigDecimal interestRate,
        Instant lastUpdate) {
    public static  InterestRateResponse from (MortgageRate rate) {
        return new InterestRateResponse(rate.maturityPeriod(), rate.interestRate(),rate.lastUpdate());
    }
}
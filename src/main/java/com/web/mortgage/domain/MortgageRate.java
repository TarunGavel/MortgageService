package com.web.mortgage.domain;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @param maturityPeriod maturity in years
 * @param interestRate   annual interest rate in percent (3.10 = 3.10%)
 * @param lastUpdate     moment the rate was last updated
 */
public record MortgageRate (
        Integer maturityPeriod,
        BigDecimal interestRate,
        Instant lastUpdate) {}
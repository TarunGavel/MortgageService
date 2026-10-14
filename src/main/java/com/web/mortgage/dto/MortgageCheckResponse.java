package com.web.mortgage.dto;

import java.math.BigDecimal;

public record MortgageCheckResponse (
        Boolean feasible,
        BigDecimal monthlyCost
) {}
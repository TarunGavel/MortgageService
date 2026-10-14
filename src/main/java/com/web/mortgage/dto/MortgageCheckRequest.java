package com.web.mortgage.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MortgageCheckRequest (
        @NotNull @Positive @Digits(integer= 12, fraction = 2) BigDecimal income,
        @NotNull @Positive Integer maturityPeriod,
        @NotNull @Positive @Digits(integer = 12, fraction = 2) BigDecimal loanValue,
        @NotNull @Positive @Digits(integer = 12, fraction = 2) BigDecimal homeValue) {}
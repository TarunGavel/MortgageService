package com.web.mortgage.exceptionHandlers;

import java.util.List;

public class UnsupportedMaturityPeriodException extends RuntimeException {
    private final transient List<Integer> supportedPeriods;

    public UnsupportedMaturityPeriodException(Integer requestedPeriod, List<Integer> supportedPeriods) {
        super("Maturity period of " + requestedPeriod + " years is not supported, The Supported periods are : " + supportedPeriods);
        this.supportedPeriods = supportedPeriods;
    };

    public List<Integer> getSupportedPeriods() {
        return supportedPeriods;
    }
}

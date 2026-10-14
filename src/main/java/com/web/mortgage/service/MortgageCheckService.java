package com.web.mortgage.service;

import com.web.mortgage.domain.MortgageRate;
import com.web.mortgage.dto.MortgageCheckRequest;
import com.web.mortgage.dto.MortgageCheckResponse;
import com.web.mortgage.exceptionHandlers.UnsupportedMaturityPeriodException;
import com.web.mortgage.repository.InterestRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class MortgageCheckService {
    Logger logger = LoggerFactory.getLogger(MortgageCheckService.class);
    private static final BigDecimal MAX_INCOME_MULTIPLE = BigDecimal.valueOf(4);

    private final InterestRateRepository interestRateRepository;

    public MortgageCheckService(InterestRateRepository repository) {
        this.interestRateRepository = repository;
    }

    public MortgageCheckResponse check(MortgageCheckRequest request) {
        var mortgageRate = interestRateRepository.findByMaturityPeriod(request.maturityPeriod())
                .orElseThrow(() -> new UnsupportedMaturityPeriodException(
                        request.maturityPeriod(),
                        interestRateRepository.findAll().stream().map(MortgageRate::maturityPeriod).toList()));

        Boolean feasible = isFeasible(request);
        logger.info("Mortgage check done: maturityPeriod {} , feasible {}", mortgageRate.maturityPeriod(), feasible);
        BigDecimal monthlyCost = monthlyCost(request.loanValue(), mortgageRate.interestRate(), mortgageRate.maturityPeriod());

        return new MortgageCheckResponse(feasible, monthlyCost);
    }

    /**
     * Check the Loan Feasibility based on business rules
     * @param request MortgageCheckRequest request
     * @return boolean feasibility flag
     */
    private static Boolean isFeasible(MortgageCheckRequest request) {

        //Business Rule 1 - a mortgage should not exceed 4 times the income
        Boolean withinIncomeLimit = request.loanValue().compareTo(
                request.income().multiply(MAX_INCOME_MULTIPLE))<=0;

        //Business Rule 2 - a mortgage should not exceed the home value
        Boolean withinHomeValue = request.loanValue().compareTo(
                request.homeValue())<=0;
        return withinIncomeLimit && withinHomeValue;
    }

    /**
     * Monthly cost calculator
     * @param principal Loan amount
     * @param anuralRatePercentage Annual percentage rate of Interest
     * @param years Term Year
     * @return monthlyCost amount
     */
    private static BigDecimal monthlyCost(BigDecimal principal, BigDecimal anuralRatePercentage, Integer years ) {
        // Simple monthly loan payment calculation
        // M = P · r · (1+r)^n / ((1+r)^n − 1)
        final MathContext MC = MathContext.DECIMAL128;
        final BigDecimal MONTHLY_PERCENT_DIVISOR = BigDecimal.valueOf(1200); // 12 * 100
        if (years<=0) {
            throw new IllegalArgumentException("years must be greater than 0");
        }
        int months = years * 12;
        // if rates % is zero, discounted rate
        if (anuralRatePercentage.signum() == 0) {
            return principal.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        }
        BigDecimal r = anuralRatePercentage.divide(MONTHLY_PERCENT_DIVISOR, MC);
        BigDecimal interestGrowth = BigDecimal.ONE.add(r).pow(months, MC); // (1+r)^n

        return principal.multiply(r).multiply(interestGrowth)
                .divide(interestGrowth.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}
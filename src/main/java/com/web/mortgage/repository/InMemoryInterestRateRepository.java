package com.web.mortgage.repository;

import com.web.mortgage.domain.MortgageRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryInterestRateRepository implements InterestRateRepository {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(InMemoryInterestRateRepository.class);
    private final List<MortgageRate> rates;

    public InMemoryInterestRateRepository() {
        LOGGER.info("In Constructor in Memory Repository for in-memory interest rates");
        Instant now = Clock.systemUTC().instant().truncatedTo(ChronoUnit.SECONDS);
        this.rates = List.of(
                new MortgageRate(10, new BigDecimal("3.10"), now),
                new MortgageRate(15, new BigDecimal("3.50"), now),
                new MortgageRate(20, new BigDecimal("3.90"), now),
                new MortgageRate(25, new BigDecimal("4.30"), now),
                new MortgageRate(30, new BigDecimal("4.50"), now)
        );
    }
    @Override
    public List<MortgageRate> findAll() {
        LOGGER.info("In findAll in Memory Repository");
        return List.copyOf(this.rates);
    }

    @Override
    public Optional<MortgageRate> findByMaturityPeriod(Integer maturityPeriod) {
        LOGGER.info("In findByMaturityPeriod in Memory Repository");
        return rates.stream().filter(r -> Objects.equals(r.maturityPeriod(), maturityPeriod)).findFirst();
    }
}
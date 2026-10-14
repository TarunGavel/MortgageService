package com.web.mortgage.repository;

import com.web.mortgage.domain.MortgageRate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface InterestRateRepository {
    List<MortgageRate> findAll();
    Optional<MortgageRate> findByMaturityPeriod(Integer maturityPeriod);

}
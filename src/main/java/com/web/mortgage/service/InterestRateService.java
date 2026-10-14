package com.web.mortgage.service;

import com.web.mortgage.domain.MortgageRate;
import com.web.mortgage.repository.InterestRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestRateService {

    InterestRateRepository interestRateRepository;

    public InterestRateService(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }

    public List<MortgageRate> getCurrentRate() {
        return interestRateRepository.findAll();
    }
}
package com.web.mortgage.controller;

import com.web.mortgage.dto.InterestRateResponse;
import com.web.mortgage.service.InterestRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InterestRateController {

    InterestRateService interestRateService;

    InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }
    @GetMapping("/interest-rates")
    @Operation(summary = "List the current mortgage interest rates")
    public List<InterestRateResponse> getInterestRates() {
        return interestRateService.getCurrentRate().stream().map(InterestRateResponse::from).toList();
    }
}
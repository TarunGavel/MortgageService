package com.web.mortgage.controller;

import com.web.mortgage.dto.MortgageCheckRequest;
import com.web.mortgage.dto.MortgageCheckResponse;
import com.web.mortgage.service.MortgageCheckService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MortgageCheckController {

    private final MortgageCheckService mortgageCheckService;

    public MortgageCheckController(MortgageCheckService service) {
        this.mortgageCheckService = service;
    }

    @PostMapping("/mortgage-check")
    @Operation(summary = "Check whether a mortgage is feasible and calculate the monthly cost")
    public MortgageCheckResponse check(@Valid @RequestBody MortgageCheckRequest request) {
        return this.mortgageCheckService.check(request);
    }
}
package com.web.mortgage.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.web.mortgage.domain.MortgageRate;
import com.web.mortgage.service.InterestRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@WebMvcTest(InterestRateController.class)
public class InterestRateControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    InterestRateService interestRateService;

    @Test
    void returnRates() throws Exception {
        given(interestRateService.getCurrentRate()).willReturn(List.of(
                new MortgageRate(10, new BigDecimal("3.10"), Instant.parse("2026-10-13T00:00:00Z"))));
        mockMvc.perform(get("/api/interest-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].maturityPeriod").value(10))
                .andExpect(jsonPath("$[0].interestRate").value(3.10))
                .andExpect(jsonPath("$[0].lastUpdate").value("2026-10-13T00:00:00Z")
                );
    }
}
package com.web.mortgage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.web.mortgage.dto.MortgageCheckResponse;
import com.web.mortgage.service.MortgageCheckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@WebMvcTest(MortgageCheckController.class)
public class MortgageCheckControllerTest {

    private static final String CHECK_REQUEST =
            """
                {"income" : 100000 , "maturityPeriod" : 20, "loanValue" : 400000.00, "homeValue" : 400000.00}
                    """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MortgageCheckService mortgageCheckService;

    @Test
    void checkMortgageAndReturn() throws Exception {
        given(mortgageCheckService.check(any())).willReturn(new MortgageCheckResponse(true, new BigDecimal("2402.90")));

        mockMvc.perform(post("/api/mortgage-check").contentType(MediaType.APPLICATION_JSON).content(CHECK_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyCost").value(2402.90));

    }
}
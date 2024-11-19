package org.colendi.infra.credit.adapter.rest;

import org.colendi.infra.BaseIntegrationTest;
import org.colendi.infra.credit.adapter.rest.model.CreateCreditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FilterCreditControllerTest extends BaseIntegrationTest {

    @Test
    void shouldFilterCredit() throws Exception {

        CreateCreditRequest request = new CreateCreditRequest();
        request.setAmount(BigDecimal.TEN);
        request.setUserId(1L);
        request.setInstallmentCount(1);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creditId").isNumber())
                .andExpect(jsonPath("$.installments").isArray())
                .andExpect(jsonPath("$.installments[0].id").isNumber())
                .andExpect(jsonPath("$.installments[0].amount").value("10.0"))
                .andExpect(jsonPath("$.installments[0].dueDate").isString());


        mvc.perform(MockMvcRequestBuilders.get("/api/v1/credit/filter")
                        .param("status", "PENDING")
                        .param("startDate", "2021-01-01")
                        .param("endDate", LocalDate.now().plusDays(5).toString())
                        .header("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].creditId").isNumber())
                .andExpect(jsonPath("$[0].installmentDetails").isArray())
                .andExpect(jsonPath("$[0].installmentDetails[0].id").isNumber())
                .andExpect(jsonPath("$[0].installmentDetails[0].amount").value("10.0"))
                .andExpect(jsonPath("$[0].installmentDetails[0].dueDate").isString());

    }

}
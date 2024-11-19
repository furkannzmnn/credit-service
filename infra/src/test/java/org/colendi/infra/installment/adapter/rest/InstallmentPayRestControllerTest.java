package org.colendi.infra.installment.adapter.rest;

import org.colendi.infra.BaseIntegrationTest;
import org.colendi.infra.credit.adapter.rest.model.CreateCreditRequest;
import org.colendi.infra.installment.adapter.rest.model.InstallmentPayRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InstallmentPayRestControllerTest extends BaseIntegrationTest {

    @Test
    void shouldPayInstallment() throws Exception {

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

        InstallmentPayRequest installmentPayRequest = new InstallmentPayRequest();
        installmentPayRequest.setAmount(BigDecimal.ONE);
        installmentPayRequest.setUserId(1L);
        installmentPayRequest.setInstallmentId(1L);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/installment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(installmentPayRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_exception_when_installment_not_found() throws Exception {
        InstallmentPayRequest installmentPayRequest = new InstallmentPayRequest();
        installmentPayRequest.setAmount(BigDecimal.ONE);
        installmentPayRequest.setUserId(1L);
        installmentPayRequest.setInstallmentId(new Random().nextLong());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/installment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(installmentPayRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Credit information not found, please try again."));
    }

}
package org.colendi.domain.credit;

import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateCreditUseCaseHandlerTest {

    FakeCreditAdapter fakeCreditAdapter;
    CreateCreditUseCaseHandler createCreditUseCaseHandler;

    @BeforeEach
    void setUp() {
        fakeCreditAdapter = new FakeCreditAdapter();
        createCreditUseCaseHandler = new CreateCreditUseCaseHandler(fakeCreditAdapter);
    }


    @Test
    void shouldCreateCredit() {
        CreateCreditUseCase createCreditUseCase = new CreateCreditUseCase(1L, BigDecimal.TEN, 3);
        createCreditUseCaseHandler.handle(createCreditUseCase);

        List<Credit> credits = fakeCreditAdapter.retrieveByUserId(1L);
        credits.forEach(credit -> {
            assertEquals(1L, credit.getUserId());
            assertEquals(BigDecimal.TEN, credit.getAmount());
            assertEquals(3, credit.getInstallmentCount());
        });
    }

}
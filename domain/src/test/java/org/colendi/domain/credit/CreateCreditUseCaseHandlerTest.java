package org.colendi.domain.credit;

import org.colendi.domain.credit.usecase.CreateCreditUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
    }


}
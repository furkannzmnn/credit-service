package org.colendi.domain.credit;

import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;
import org.colendi.domain.credit.usecase.FilterCreditUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilterCreditUseCaseHandlerTest {

    FakeCreditAdapter fakeCreditAdapter;
    FilterCreditUseCaseHandler filterCreditUseCaseHandler;
    CreateCreditUseCaseHandler createCreditUseCaseHandler;

    @BeforeEach
    void setUp() {
        fakeCreditAdapter = new FakeCreditAdapter();
        filterCreditUseCaseHandler = new FilterCreditUseCaseHandler(fakeCreditAdapter);
        createCreditUseCaseHandler = new CreateCreditUseCaseHandler(fakeCreditAdapter);
    }

    @Test
    void handle() {
        createCreditUseCaseHandler.handle(new CreateCreditUseCase(1L, BigDecimal.TEN, 3));

        assertNotNull(filterCreditUseCaseHandler.handle(new FilterCreditUseCase(
                CreditStatus.PENDING,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(2),
                1L
        )));
    }

}
package org.colendi.domain.credit;

import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.query.ListCreditResponse;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;
import org.colendi.domain.credit.usecase.ListCreditUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCreditUseCaseHandlerTest {

    FakeCreditAdapter fakeCreditAdapter;
    ListCreditUseCaseHandler listCreditUseCaseHandler;
    CreateCreditUseCaseHandler createCreditUseCaseHandler;

    @BeforeEach
    void setUp() {
        fakeCreditAdapter = new FakeCreditAdapter();
        listCreditUseCaseHandler = new ListCreditUseCaseHandler(fakeCreditAdapter);
        createCreditUseCaseHandler = new CreateCreditUseCaseHandler(fakeCreditAdapter);
    }

    @Test
    void shouldListCredit() {

        CreateCreditUseCase createCreditUseCase = CreateCreditUseCase.builder()
                .userId(1L)
                .amount(BigDecimal.valueOf(1000))
                .installmentCount(3)
                .build();

        createCreditUseCaseHandler.handle(createCreditUseCase);


        ListCreditUseCase listCreditUseCase = ListCreditUseCase.builder().userId(1L).build();
        List<ListCreditResponse> response = listCreditUseCaseHandler.handle(listCreditUseCase);

        assertEquals(1, response.size());


        ListCreditResponse listCreditResponse = response.get(0);
        assertEquals(1, listCreditResponse.creditId());
        assertEquals(3, listCreditResponse.installmentDetails().size());


        assertEquals(LocalDate.now().plusDays(30).toString(), listCreditResponse.installmentDetails().get(0).getDueDate());
        assertEquals(LocalDate.now().plusDays(62).toString(), listCreditResponse.installmentDetails().get(1).getDueDate());
        assertEquals(BigDecimal.valueOf(333.33), listCreditResponse.installmentDetails().get(0).getAmount());
    }
}
package org.colendi.infra.credit.adapter.rest.model;

import lombok.Getter;
import lombok.Setter;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCreditRequest {

    private Long userId;
    private BigDecimal amount;
    private int installmentCount;

    public CreateCreditUseCase toUseCase() {
        return CreateCreditUseCase.builder()
                .userId(userId)
                .amount(amount)
                .installmentCount(installmentCount)
                .build();
    }
}

package org.fintech.infra.credit.adapter.rest.model;

import lombok.Getter;
import lombok.Setter;
import org.fintech.domain.credit.usecase.CreateCreditUseCase;

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

    public boolean isValid() {
        return userId != null && amount != null && installmentCount > 0 && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public void runValidation() {
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid request");
        }
    }
}

package org.fintech.infra.installment.adapter.rest.model;

import lombok.Getter;
import lombok.Setter;
import org.fintech.domain.installment.usecase.InstallmentPayUseCase;

import java.math.BigDecimal;

@Getter
@Setter
public class InstallmentPayRequest {
    private Long installmentId;
    private Long userId;
    private BigDecimal amount;

    public InstallmentPayUseCase toUseCase() {
        return InstallmentPayUseCase.builder()
                .installmentId(installmentId)
                .userId(userId)
                .amount(amount)
                .build();
    }

}

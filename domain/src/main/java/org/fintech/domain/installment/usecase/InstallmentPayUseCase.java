package org.fintech.domain.installment.usecase;

import lombok.Builder;
import org.fintech.domain.config.usecase.UseCase;

import java.math.BigDecimal;

@Builder
public record InstallmentPayUseCase(
        Long installmentId,
        BigDecimal amount,
        Long userId
) implements UseCase { }

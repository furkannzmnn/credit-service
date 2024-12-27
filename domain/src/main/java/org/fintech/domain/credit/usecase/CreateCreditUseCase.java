package org.fintech.domain.credit.usecase;

import lombok.Builder;
import org.fintech.domain.config.usecase.UseCase;

import java.math.BigDecimal;

@Builder
public record CreateCreditUseCase(
        Long userId,
        BigDecimal amount,
        Integer installmentCount
) implements UseCase {
}
package org.colendi.domain.credit.usecase;

import lombok.Builder;
import org.colendi.domain.config.usecase.UseCase;

import java.math.BigDecimal;

@Builder
public record CreateCreditUseCase(
        Long userId,
        BigDecimal amount,
        Integer installmentCount
) implements UseCase {
}
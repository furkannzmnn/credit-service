package org.fintech.domain.credit.usecase;

import lombok.Builder;
import org.fintech.domain.config.usecase.UseCase;

@Builder
public record ListCreditUseCase(Long userId) implements UseCase { }

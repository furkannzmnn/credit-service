package org.colendi.domain.credit.usecase;

import lombok.Builder;
import org.colendi.domain.config.usecase.UseCase;

@Builder
public record ListCreditUseCase(Long userId) implements UseCase { }

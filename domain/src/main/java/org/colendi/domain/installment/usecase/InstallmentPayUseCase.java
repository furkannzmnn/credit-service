package org.colendi.domain.installment.usecase;

import lombok.Builder;
import lombok.Getter;
import org.colendi.domain.UseCase;

import java.math.BigDecimal;

@Builder
@Getter
public class InstallmentPayUseCase implements UseCase {
    private Long installmentId;
    private BigDecimal amount;
    private Long userId;
}

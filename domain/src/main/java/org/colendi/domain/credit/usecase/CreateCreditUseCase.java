package org.colendi.domain.credit.usecase;

import lombok.Builder;
import lombok.Getter;
import org.colendi.domain.UseCase;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateCreditUseCase implements UseCase {
    private Long userId;
    private BigDecimal amount;
    private Integer installmentCount;
}

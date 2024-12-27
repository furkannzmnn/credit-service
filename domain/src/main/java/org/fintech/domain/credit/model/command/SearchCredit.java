package org.fintech.domain.credit.model.command;

import lombok.Builder;
import org.fintech.domain.credit.model.CreditStatus;

import java.time.LocalDate;

@Builder
public record SearchCredit(
        Long userId,
        CreditStatus status,
        LocalDate startDate,
        LocalDate endDate
) {
}

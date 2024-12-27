package org.fintech.domain.credit.usecase;

import lombok.Builder;
import org.fintech.domain.config.usecase.UseCase;
import org.fintech.domain.credit.model.CreditStatus;
import org.fintech.domain.credit.model.command.SearchCredit;

import java.time.LocalDate;

@Builder
public record FilterCreditUseCase(
        CreditStatus status,
        LocalDate startDate,
        LocalDate endDate,
        Long userId
) implements UseCase {

    public SearchCredit searchCredit() {
        return SearchCredit.builder()
                .userId(userId)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}

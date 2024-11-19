package org.colendi.domain.credit.usecase;

import lombok.Builder;
import org.colendi.domain.config.usecase.UseCase;
import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.domain.credit.model.command.SearchCredit;

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

package org.colendi.domain.credit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.UseCaseHandler;
import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.query.ListCreditResponse;
import org.colendi.domain.credit.port.CreditPort;
import org.colendi.domain.credit.usecase.ListCreditUseCase;

import java.util.List;

@DomainComponent
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ListCreditUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<List<ListCreditResponse>, ListCreditUseCase> {

    CreditPort creditPort;

    @Override
    public List<ListCreditResponse> handle(ListCreditUseCase useCase) {
        List<Credit> credits = creditPort.retrieveByUserId(useCase.userId());
        return credits.stream()
                .map(ListCreditResponse::fromCredit)
                .toList();
    }
}

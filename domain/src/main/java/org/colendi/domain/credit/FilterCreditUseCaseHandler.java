package org.colendi.domain.credit;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.UseCaseHandler;
import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.query.ListCreditResponse;
import org.colendi.domain.credit.port.CreditPort;
import org.colendi.domain.credit.usecase.FilterCreditUseCase;

import java.util.List;

@DomainComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FilterCreditUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<List<ListCreditResponse>, FilterCreditUseCase> {

    CreditPort creditPort;

    public FilterCreditUseCaseHandler(CreditPort creditPort) {
        this.creditPort = creditPort;
        register(FilterCreditUseCase.class, this);
    }

    @Override
    public List<ListCreditResponse> handle(FilterCreditUseCase useCase) {
        List<Credit> credits = creditPort.searchCredits(useCase.searchCredit());
        return credits.stream()
                .map(ListCreditResponse::fromCredit)
                .toList();
    }
}

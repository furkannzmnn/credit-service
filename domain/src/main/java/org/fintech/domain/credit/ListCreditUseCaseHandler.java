package org.fintech.domain.credit;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fintech.domain.config.usecase.DomainComponent;
import org.fintech.domain.config.usecase.ObservableUseCasePublisher;
import org.fintech.domain.config.usecase.UseCaseHandler;
import org.fintech.domain.credit.model.Credit;
import org.fintech.domain.credit.model.query.ListCreditResponse;
import org.fintech.domain.credit.port.CreditPort;
import org.fintech.domain.credit.usecase.ListCreditUseCase;

import java.util.List;

@DomainComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ListCreditUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<List<ListCreditResponse>, ListCreditUseCase> {

    CreditPort creditPort;

    public ListCreditUseCaseHandler(CreditPort creditPort) {
        this.creditPort = creditPort;
        register(ListCreditUseCase.class, this);
    }


    @Override
    public List<ListCreditResponse> handle(ListCreditUseCase useCase) {
        List<Credit> credits = creditPort.retrieveByUserId(useCase.userId());
        return credits.stream()
                .map(ListCreditResponse::fromCredit)
                .toList();
    }
}

package org.fintech.domain.credit;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fintech.domain.config.usecase.DomainComponent;
import org.fintech.domain.config.usecase.ObservableUseCasePublisher;
import org.fintech.domain.config.usecase.UseCaseHandler;
import org.fintech.domain.credit.model.Credit;
import org.fintech.domain.credit.model.CreditStatus;
import org.fintech.domain.credit.model.query.CreateCreditResponse;
import org.fintech.domain.credit.port.CreditPort;
import org.fintech.domain.credit.usecase.CreateCreditUseCase;

import java.time.LocalDate;
import java.util.ArrayList;

@DomainComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateCreditUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<CreateCreditResponse, CreateCreditUseCase> {

    CreditPort creditPort;

    public CreateCreditUseCaseHandler(CreditPort creditPort) {
        this.creditPort = creditPort;
        register(CreateCreditUseCase.class, this);
    }

    public CreateCreditResponse handle(CreateCreditUseCase createCreditUseCase) {
        Credit credit = Credit.builder()
                .userId(createCreditUseCase.userId())
                .amount(createCreditUseCase.amount())
                .installmentCount(createCreditUseCase.installmentCount())
                .createdAt(LocalDate.now())
                .status(CreditStatus.PENDING)
                .installments(new ArrayList<>())
                .build();

        credit.createInstallments();

        Credit createdCredit = creditPort.create(credit);

        return createdCredit.toCreateCreditResponse();

    }
}

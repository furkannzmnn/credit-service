package org.colendi.domain.credit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.UseCaseHandler;
import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.domain.credit.model.query.CreateCreditResponse;
import org.colendi.domain.credit.port.CreditPort;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;

import java.time.LocalDate;
import java.util.ArrayList;

@DomainComponent
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateCreditUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<CreateCreditResponse, CreateCreditUseCase> {

    CreditPort creditPort;

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

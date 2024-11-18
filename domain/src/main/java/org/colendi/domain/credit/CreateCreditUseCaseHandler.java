package org.colendi.domain.credit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.VoidUseCaseHandler;
import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.domain.credit.port.CreditPort;
import org.colendi.domain.credit.usecase.CreateCreditUseCase;

import java.time.LocalDate;

@DomainComponent
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateCreditUseCaseHandler extends ObservableUseCasePublisher implements VoidUseCaseHandler<CreateCreditUseCase> {

    CreditPort creditPort;

    public void handle(CreateCreditUseCase createCreditUseCase) {
        Credit credit = Credit.builder()
                .userId(createCreditUseCase.getUserId())
                .amount(createCreditUseCase.getAmount())
                .installmentCount(createCreditUseCase.getInstallmentCount())
                .createdAt(LocalDate.now())
                .status(CreditStatus.PENDING)
                .build();

        credit.createInstallments();

        creditPort.create(credit);

    }
}

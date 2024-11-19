package org.colendi.domain.installment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.exception.DomainException;
import org.colendi.domain.config.exception.ErrorCode;
import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.VoidUseCaseHandler;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.port.CachePort;
import org.colendi.domain.installment.port.InstallmentPort;
import org.colendi.domain.installment.usecase.InstallmentPayUseCase;

import java.math.BigDecimal;

@DomainComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InstallmentPayUseCaseHandler extends ObservableUseCasePublisher implements VoidUseCaseHandler<InstallmentPayUseCase> {

    InstallmentPort installmentPort;
    CachePort cachePort;

    public InstallmentPayUseCaseHandler(InstallmentPort installmentPort, CachePort cachePort) {
        this.installmentPort = installmentPort;
        this.cachePort = cachePort;
        register(InstallmentPayUseCase.class, this);
    }

    public void handle(InstallmentPayUseCase installmentPayUseCase) {
        String installmentId = installmentPayUseCase.installmentId().toString();

        if (cachePort.isLocked(installmentId)) {
            throw DomainException.builder()
                    .messageKey(ErrorCode.SYSTEM_BUSY.getMessageKey())
                    .build();
        }

        cachePort.lock(installmentId);

        try {
            Installment installment = installmentPort.retrieve(installmentPayUseCase.installmentId());
            BigDecimal amountToPay = installmentPayUseCase.amount();
            installment.pay(amountToPay);
            installmentPort.update(installment);
        } finally {
            cachePort.unlock(installmentId);
        }
    }
}

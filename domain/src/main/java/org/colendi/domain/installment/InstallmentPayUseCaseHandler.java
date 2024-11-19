package org.colendi.domain.installment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.colendi.domain.config.usecase.DomainComponent;
import org.colendi.domain.config.usecase.ObservableUseCasePublisher;
import org.colendi.domain.config.usecase.VoidUseCaseHandler;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.port.CachePort;
import org.colendi.domain.installment.port.InstallmentPort;
import org.colendi.domain.installment.usecase.InstallmentPayUseCase;

import java.math.BigDecimal;

@DomainComponent
@Slf4j
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
            log.warn("Installment is locked. installmentId: {}", installmentId);
            return;
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

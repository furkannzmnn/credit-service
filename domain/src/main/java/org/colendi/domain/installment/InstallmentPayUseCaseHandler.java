package org.colendi.domain.installment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.DomainComponent;
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
        cachePort.lock(installmentPayUseCase.getInstallmentId().toString());
        try {
            Installment installment = installmentPort.lookUp(installmentPayUseCase.getInstallmentId());
            BigDecimal amountToPay = installmentPayUseCase.getAmount();
            installment.pay(amountToPay);
            installmentPort.update(installment);
        } finally {
            cachePort.unlock(installmentPayUseCase.getInstallmentId().toString());
        }
    }


}

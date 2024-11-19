package org.colendi.infra.credit.adapter.scheduler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.port.InstallmentPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LateFeeScheduler {

    InstallmentPort installmentPort;


    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateLateFees() {
        List<Installment> overdueInstallments = installmentPort.retrieveOverdueInstallments();
        for (Installment installment : overdueInstallments) {
            BigDecimal lateFee = installment.calculateFee(installment.getAmount());
            installment.markOverdue(lateFee);
            installmentPort.update(installment);
        }
    }
}

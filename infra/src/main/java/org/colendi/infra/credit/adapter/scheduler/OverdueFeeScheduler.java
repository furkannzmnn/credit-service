package org.colendi.infra.credit.adapter.scheduler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.colendi.domain.installment.port.InstallmentPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OverdueFeeScheduler {

    InstallmentPort installmentPort;


    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateLateFees() {
        List<Installment> overdueInstallments = installmentPort.retrieveOverdueInstallments(LocalDate.now(), InstallmentStatus.PENDING);
        for (Installment installment : overdueInstallments) {
            BigDecimal lateFee = installment.calculateFee(installment.getAmount());
            installment.markOverdue(lateFee);
            installmentPort.update(installment);

            log.info("Installment ID: {}, Amount: {}, Overdue Days: {}, Late Fee: {}",
                    installment.getId(),
                    installment.getAmount(),
                    installment.overdueDays(),
                    lateFee);
        }
    }
}

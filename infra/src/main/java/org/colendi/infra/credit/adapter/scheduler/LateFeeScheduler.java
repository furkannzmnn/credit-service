package org.colendi.infra.credit.adapter.scheduler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.port.InstallmentPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.colendi.domain.installment.model.InstallmentStatus.OVERDUE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LateFeeScheduler {

    InstallmentPort installmentPort;


    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateLateFees() {
        List<Installment> overdueInstallments = installmentPort.retrieveOverdueInstallments();
        for (Installment installment : overdueInstallments) {
            long daysOverdue = ChronoUnit.DAYS.between(installment.getDueDate(), LocalDate.now());
            BigDecimal lateFee = calculateFee(installment.getAmount(), daysOverdue);
            installment.setLateFee(lateFee);
            installment.setStatus(OVERDUE);
            installmentPort.update(installment);
        }
    }

    private BigDecimal calculateFee(BigDecimal amount, long daysOverdue) {
        BigDecimal dailyRate = BigDecimal.valueOf(0.05);
        return amount
                .multiply(dailyRate)
                .multiply(BigDecimal.valueOf(daysOverdue))
                .divide(BigDecimal.valueOf(360), RoundingMode.HALF_UP);
    }
}

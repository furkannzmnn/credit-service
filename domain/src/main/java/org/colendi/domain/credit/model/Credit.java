package org.colendi.domain.credit.model;

import lombok.Builder;
import lombok.Data;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;

@Data
@Builder
public class Credit {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private int installmentCount;
    private CreditStatus status;
    private LocalDate createdAt;
    private List<Installment> installments;

    public void createInstallments() {
        LocalDateTime dueDate = ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDateTime();
        BigDecimal installmentAmount = this.amount.divide(
                BigDecimal.valueOf(this.installmentCount), RoundingMode.HALF_UP
        );

        for (int i = 0; i < this.installmentCount; i++) {
            dueDate = adjustDueDate(dueDate.plusDays(30));
            Installment installment = Installment.builder()
                    .dueDate(dueDate.toLocalDate())
                    .amount(installmentAmount)
                    .status(InstallmentStatus.PENDING)
                    .build();
            this.installments.add(installment);
        }
    }

    private LocalDateTime adjustDueDate(LocalDateTime date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.plusDays(2);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        }
        return date;
    }
}

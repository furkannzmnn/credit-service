package org.colendi.domain.installment.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.colendi.domain.config.exception.DomainException;
import org.colendi.domain.config.exception.ErrorCode;
import org.colendi.domain.config.usecase.AggregateRoot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Installment extends AggregateRoot {
    private Long id;
    private Long creditId;
    private LocalDate dueDate;
    private BigDecimal amount;
    private InstallmentStatus status;
    private BigDecimal paidAmount;
    private LocalDate paymentDate;
    private BigDecimal lateFee;

    public void pay(BigDecimal amount) {
        if (isAlreadyPaid()) {
            throw DomainException.builder()
                    .messageKey(ErrorCode.INSTALLMENT_ALREADY_PAID.getMessageKey())
                    .build();
        }

        this.paidAmount = this.paidAmount.add(amount);

        if (this.paidAmount.compareTo(this.amount) >= 0) {
            this.status = InstallmentStatus.PAID;
            this.paidAmount = this.amount;
            this.paymentDate = LocalDate.now();
        } else {
            this.status = InstallmentStatus.PARTIALLY_PAID;
        }
    }


    public BigDecimal calculateFee(BigDecimal amount) {
        BigDecimal dailyRate = BigDecimal.valueOf(0.05);
        return amount
                .multiply(dailyRate)
                .multiply(BigDecimal.valueOf(overdueDays()))
                .divide(BigDecimal.valueOf(360), RoundingMode.HALF_UP);
    }

    public long overdueDays() {
        return ChronoUnit.DAYS.between(getDueDate(), LocalDate.now());
    }

    public void markOverdue(BigDecimal lateFee) {
        this.lateFee = lateFee;
        this.status = InstallmentStatus.OVERDUE;
    }

    private boolean isAlreadyPaid() {
        return this.status == InstallmentStatus.PAID;
    }
}

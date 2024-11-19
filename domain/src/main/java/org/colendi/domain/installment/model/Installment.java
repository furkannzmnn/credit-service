package org.colendi.domain.installment.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private BigDecimal lateFee;// Gecikme ücreti

    public void pay(BigDecimal amount) {
        if (this.status == InstallmentStatus.PAID) {
            throw new IllegalStateException("Installment is already fully paid.");
        }

        this.paidAmount = this.paidAmount.add(amount);

        // 3. Taksitin tamamen ödenip ödenmediğini kontrol ediyrum burda
        if (this.paidAmount.compareTo(this.amount) >= 0) {
            this.status = InstallmentStatus.PAID;
            this.paidAmount = this.amount; // Ödenen miktarı taksit tutarına eşitle
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

    private long overdueDays() {
        return ChronoUnit.DAYS.between(getDueDate(), LocalDate.now());
    }

    public void markOverdue(BigDecimal lateFee) {
        this.lateFee = lateFee;
        this.status = InstallmentStatus.OVERDUE;
    }
}

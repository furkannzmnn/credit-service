package org.colendi.domain.installment.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Installment {
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
}

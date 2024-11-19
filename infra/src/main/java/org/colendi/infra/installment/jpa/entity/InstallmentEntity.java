package org.colendi.infra.installment.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long creditId;
    private LocalDate dueDate;
    private BigDecimal amount;
    private InstallmentStatus status;
    private BigDecimal paidAmount;
    private LocalDate paymentDate;
    private BigDecimal lateFee;


    public Installment toModel() {
        return Installment.builder()
                .id(id)
                .creditId(creditId)
                .dueDate(dueDate)
                .amount(amount)
                .status(status)
                .paidAmount(paidAmount)
                .paymentDate(paymentDate)
                .lateFee(lateFee)
                .build();
    }

    public static InstallmentEntity fromModel(Installment installment) {
        return InstallmentEntity.builder()
                .id(installment.getId())
                .creditId(installment.getCreditId())
                .dueDate(installment.getDueDate())
                .amount(installment.getAmount())
                .status(installment.getStatus())
                .paidAmount(installment.getPaidAmount())
                .paymentDate(installment.getPaymentDate())
                .lateFee(installment.getLateFee())
                .build();
    }
}

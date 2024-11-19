package org.colendi.infra.credit.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.infra.installment.jpa.entity.InstallmentEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private BigDecimal amount;
    private int installmentCount;
    private CreditStatus status;
    private LocalDate createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<InstallmentEntity> installments;

    public static CreditEntity from(Credit credit) {
        return CreditEntity.builder()
                .userId(credit.getUserId())
                .amount(credit.getAmount())
                .installmentCount(credit.getInstallmentCount())
                .status(credit.getStatus())
                .createdAt(credit.getCreatedAt())
                .build();
    }

    public Credit toModel() {
        return Credit.builder()
                .id(id)
                .userId(userId)
                .amount(amount)
                .installmentCount(installmentCount)
                .status(status)
                .createdAt(createdAt)
                .installments(installments.stream().map(InstallmentEntity::toModel).toList())
                .build();
    }
}

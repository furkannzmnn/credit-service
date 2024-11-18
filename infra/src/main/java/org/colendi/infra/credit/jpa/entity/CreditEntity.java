package org.colendi.infra.credit.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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

}

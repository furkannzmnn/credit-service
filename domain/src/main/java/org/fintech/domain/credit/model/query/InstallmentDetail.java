package org.fintech.domain.credit.model.query;

import lombok.Builder;
import lombok.Getter;
import org.fintech.domain.installment.model.Installment;

import java.math.BigDecimal;

@Builder
@Getter
public class InstallmentDetail {
    private Long id;
    private BigDecimal amount;
    private String dueDate;

    public static InstallmentDetail fromInstallment(Installment installment) {
        return InstallmentDetail.builder()
                .id(installment.getId())
                .amount(installment.getAmount())
                .dueDate(installment.getDueDate().toString())
                .build();
    }
}

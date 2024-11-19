package org.colendi.domain.credit.model.query;

import lombok.Builder;
import org.colendi.domain.credit.model.Credit;

import java.util.List;

@Builder
public record ListCreditResponse(
        Long creditId,
        List<InstallmentDetail> installmentDetails
) {
    public static ListCreditResponse fromCredit(Credit credit) {
        return ListCreditResponse.builder()
                .creditId(credit.getId())
                .installmentDetails(credit.getInstallments().stream()
                        .map(InstallmentDetail::fromInstallment)
                        .toList())
                .build();
    }
}

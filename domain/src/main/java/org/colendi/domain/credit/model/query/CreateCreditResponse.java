package org.colendi.domain.credit.model.query;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateCreditResponse {
    private Long creditId;
    private List<InstallmentDetail> installments;
}

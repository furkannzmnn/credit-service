package org.fintech.domain.installment.port;

import org.fintech.domain.installment.model.Installment;
import org.fintech.domain.installment.model.InstallmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentPort {

    Installment retrieve(Long installmentId);

    void update(Installment installment);

    List<Installment> retrieveOverdueInstallments(LocalDate time, InstallmentStatus status);

}

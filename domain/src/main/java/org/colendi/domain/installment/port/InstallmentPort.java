package org.colendi.domain.installment.port;

import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentPort {

    Installment retrieve(Long installmentId);

    void update(Installment installment);

    List<Installment> retrieveOverdueInstallments(LocalDate time, InstallmentStatus status);

}

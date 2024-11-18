package org.colendi.domain.installment.port;

import org.colendi.domain.installment.model.Installment;

import java.util.List;

public interface InstallmentPort {

    Installment retrieve(Long installmentId);

    void update(Installment installment);

    List<Installment> retrieveOverdueInstallments();

}

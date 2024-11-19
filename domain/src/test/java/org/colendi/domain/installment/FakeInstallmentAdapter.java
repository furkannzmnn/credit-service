package org.colendi.domain.installment;

import org.colendi.domain.config.exception.DomainException;
import org.colendi.domain.config.exception.ErrorCode;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.colendi.domain.installment.port.InstallmentPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeInstallmentAdapter implements InstallmentPort {

    private final List<Installment> installments = new ArrayList<>();
    private boolean retrieveCalled = false;


    {
        Installment installment = Installment.builder()
                .amount(BigDecimal.TEN)
                .status(InstallmentStatus.PENDING)
                .paidAmount(BigDecimal.ZERO)
                .dueDate(LocalDate.now().minusDays(1))
                .build();
        installment.setId(1L);
        installments.add(installment);
    }

    @Override
    public Installment retrieve(Long installmentId) {
        retrieveCalled = true;
        return installments.stream()
                .filter(installment -> installment.getId().equals(installmentId))
                .findFirst()
                .orElseThrow(() -> DomainException.builder()
                        .messageKey(ErrorCode.INSTALLMENT_NOT_FOUND.getMessageKey())
                        .build());
    }

    @Override
    public void update(Installment installment) {
        installments.removeIf(i -> i.getId().equals(installment.getId()));
        installments.add(installment);
    }

    @Override
    public List<Installment> retrieveOverdueInstallments(LocalDate time, InstallmentStatus status) {
        return List.of();
    }

    public boolean wasRetrieveCalled() {
        return retrieveCalled;
    }

    public void reset() {
        retrieveCalled = false;
    }
}

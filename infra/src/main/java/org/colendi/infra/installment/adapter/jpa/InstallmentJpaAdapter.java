package org.colendi.infra.installment.adapter.jpa;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.exception.DomainException;
import org.colendi.domain.config.exception.ErrorCode;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.colendi.domain.installment.port.InstallmentPort;
import org.colendi.infra.installment.jpa.InstallmentRepository;
import org.colendi.infra.installment.jpa.entity.InstallmentEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InstallmentJpaAdapter implements InstallmentPort {

    InstallmentRepository installmentRepository;


    @Override
    public Installment retrieve(Long installmentId) {
        return installmentRepository.findById(installmentId).orElseThrow(() -> DomainException.builder()
                        .messageKey(ErrorCode.INSTALLMENT_NOT_FOUND.getMessageKey())
                        .build())
                .toModel();
    }

    @Override
    public void update(Installment installment) {
        installmentRepository.save(InstallmentEntity.builder()
                .id(installment.getId())
                .creditId(installment.getCreditId())
                .dueDate(installment.getDueDate())
                .amount(installment.getAmount())
                .status(installment.getStatus())
                .paidAmount(installment.getPaidAmount())
                .paymentDate(installment.getPaymentDate())
                .lateFee(installment.getLateFee())
                .build());
    }

    @Override
    public List<Installment> retrieveOverdueInstallments() {
        return installmentRepository.findByDueDateBeforeAndStatus(LocalDate.now(), InstallmentStatus.PENDING)
                .stream()
                .map(InstallmentEntity::toModel)
                .toList();
    }
}

package org.fintech.infra.installment.jpa;

import org.fintech.domain.installment.model.InstallmentStatus;
import org.fintech.infra.installment.jpa.entity.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<InstallmentEntity, Long> {
    List<InstallmentEntity> findByDueDateBeforeAndStatus(LocalDate dueDate, InstallmentStatus status);
}

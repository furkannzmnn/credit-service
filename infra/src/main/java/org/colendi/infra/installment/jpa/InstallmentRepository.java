package org.colendi.infra.installment.jpa;

import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findByDueDateBeforeAndStatus(LocalDate dueDate, InstallmentStatus status);
}

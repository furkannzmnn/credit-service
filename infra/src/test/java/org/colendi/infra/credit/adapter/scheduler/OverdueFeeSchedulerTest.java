package org.colendi.infra.credit.adapter.scheduler;

import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OverdueFeeSchedulerTest {

    FakeInstallmentAdapter fakeInstallmentAdapter;
    OverdueFeeScheduler overdueFeeScheduler;

    @BeforeEach
    void setUp() {
        fakeInstallmentAdapter = new FakeInstallmentAdapter();
        overdueFeeScheduler = new OverdueFeeScheduler(fakeInstallmentAdapter);
        fakeInstallmentAdapter.getInstallments().addAll(List.of(
                Installment.builder()
                        .id(2L)
                        .amount(BigDecimal.TEN)
                        .status(InstallmentStatus.PENDING)
                        .paidAmount(BigDecimal.ZERO)
                        .dueDate(LocalDate.now().minusDays(12))
                        .build(),
                Installment.builder()
                        .id(3L)
                        .amount(BigDecimal.TEN)
                        .status(InstallmentStatus.PENDING)
                        .paidAmount(BigDecimal.ZERO)
                        .dueDate(LocalDate.now().plusDays(1))
                        .build()
        ));
    }


    @Test
    void shouldCalculateLateFeesForOverdueInstallments() {

        overdueFeeScheduler.calculateLateFees();

        List<Installment> updatedInstallments = fakeInstallmentAdapter.getInstallments();

        updatedInstallments.stream()
                .filter(installment -> installment.getId().equals(2L))
                .findFirst()
                .ifPresent(installment -> {
                    assertThat(installment.getLateFee()).isEqualTo(BigDecimal.valueOf(0.02));
                    assertThat(installment.getStatus()).isEqualTo(InstallmentStatus.OVERDUE);
                });
    }


}
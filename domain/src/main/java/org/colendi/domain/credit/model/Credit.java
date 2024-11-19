package org.colendi.domain.credit.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.colendi.domain.config.usecase.AggregateRoot;
import org.colendi.domain.credit.model.query.CreateCreditResponse;
import org.colendi.domain.credit.model.query.InstallmentDetail;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Credit extends AggregateRoot {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private int installmentCount;
    private CreditStatus status;
    private LocalDate createdAt;
    private List<Installment> installments;

    public void createInstallments() {
        LocalDateTime dueDate = getCurrentDate();
        BigDecimal installmentAmount = calculateInstallmentAmount();

        for (int i = 0; i < this.installmentCount; i++) {
            dueDate = getNextBusinessDay(dueDate.plusDays(30));
            Installment installment = createInstallment(dueDate, installmentAmount);
            this.installments.add(installment);
        }
    }


    private LocalDateTime getCurrentDate() {
        return ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDateTime();
    }

    private BigDecimal calculateInstallmentAmount() {
        return this.amount.divide(
                BigDecimal.valueOf(this.installmentCount), 2, RoundingMode.HALF_UP
        );
    }

    private Installment createInstallment(LocalDateTime dueDate, BigDecimal amount) {
        return Installment.builder()
                .dueDate(dueDate.toLocalDate())
                .amount(amount)
                .status(InstallmentStatus.PENDING)
                .build();
    }


    private LocalDateTime getNextBusinessDay(LocalDateTime date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.plusDays(2);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        }
        return date;
    }

    public CreateCreditResponse toCreateCreditResponse() {
        List<InstallmentDetail> installmentDetails = new ArrayList<>();
        this.installments.forEach(installment -> {
            installmentDetails.add(InstallmentDetail.fromInstallment(installment));
        });
        return CreateCreditResponse.builder()
                .creditId(this.id)
                .installments(installmentDetails)
                .build();
    }
}

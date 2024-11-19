package org.colendi.infra.credit.adapter.rest;

import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.domain.credit.model.CreditStatus;
import org.colendi.domain.credit.usecase.FilterCreditUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/credit")
public class FilterCreditController extends BeanAwareUseCasePublisher {

    @GetMapping("/filter")
    public ResponseEntity<?> filterCredit(
                                       @RequestHeader("userId") Long userId,
                                       @RequestParam(value = "status", required = false) CreditStatus status,
                                       @RequestParam(value = "startDate",required = false) String startDate,
                                       @RequestParam(value = "endDate", required = false) String endDate
    ) {
        FilterCreditUseCase filterCreditUseCase = FilterCreditUseCase.builder()
                .userId(userId)
                .status(status)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .build();

       var response =  publish(List.class, filterCreditUseCase);
       return ResponseEntity.ok(response);
    }
}

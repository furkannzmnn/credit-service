package org.colendi.infra.credit.adapter.rest;


import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.domain.credit.model.query.ListCreditResponse;
import org.colendi.domain.credit.usecase.ListCreditUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/credit")
public class ListCreditRestController extends BeanAwareUseCasePublisher {

    @GetMapping
    public ResponseEntity<List<ListCreditResponse>> listCredit(@RequestHeader("userId") Long userId) {

        List<ListCreditResponse> response = publish(List.class, ListCreditUseCase.builder()
                .userId(userId)
                .build());

        return ResponseEntity.ok(response);

    }
}

package org.colendi.infra.credit.adapter.rest;


import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.domain.credit.usecase.ListCreditUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/credit")

public class ListCreditRestController extends BeanAwareUseCasePublisher {

    @GetMapping
    public void listCredit(@RequestHeader("userId") Long userId) {
        publish(ListCreditUseCase.builder().userId(userId).build());
    }
}

package org.colendi.infra.credit.adapter.rest;

import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.infra.credit.adapter.rest.model.CreateCreditRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/credit")
public class CreateCreditRestController extends BeanAwareUseCasePublisher {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCredit(CreateCreditRequest request) {
        publish(request.toUseCase());
    }
}

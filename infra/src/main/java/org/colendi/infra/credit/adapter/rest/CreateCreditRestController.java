package org.colendi.infra.credit.adapter.rest;

import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.domain.credit.model.query.CreateCreditResponse;
import org.colendi.infra.credit.adapter.rest.model.CreateCreditRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/credit")
public class CreateCreditRestController extends BeanAwareUseCasePublisher {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCreditResponse createCredit(@RequestBody CreateCreditRequest request) {
        return publish(CreateCreditResponse.class, request.toUseCase());
    }
}

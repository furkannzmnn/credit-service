package org.fintech.infra.credit.adapter.rest;

import org.fintech.domain.config.usecase.BeanAwareUseCasePublisher;
import org.fintech.domain.credit.model.query.CreateCreditResponse;
import org.fintech.infra.credit.adapter.rest.model.CreateCreditRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/credit")
public class CreateCreditRestController extends BeanAwareUseCasePublisher {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCreditResponse createCredit(@RequestBody CreateCreditRequest request) {
        request.runValidation();
        return publish(CreateCreditResponse.class, request.toUseCase());
    }
}

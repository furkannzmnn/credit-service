package org.colendi.infra.installment.adapter.rest;

import org.colendi.domain.config.usecase.BeanAwareUseCasePublisher;
import org.colendi.infra.installment.adapter.rest.model.InstallmentPayRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/installment")
public class InstallmentPayRestController extends BeanAwareUseCasePublisher {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void pay(@RequestBody InstallmentPayRequest request) {
        publish(request.toUseCase());
    }

}

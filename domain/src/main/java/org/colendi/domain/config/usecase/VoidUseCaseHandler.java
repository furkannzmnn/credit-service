package org.colendi.domain.config.usecase;


import org.colendi.domain.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}

package org.fintech.domain.config.usecase;


public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}

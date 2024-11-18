package org.colendi.domain.config.usecase;


public interface UseCaseHandler<R, T extends UseCase> {

    R handle(T useCase);
}

package org.fintech.domain.config.usecase;


public interface UseCaseHandler<R, T extends UseCase> {

    R handle(T useCase);
}

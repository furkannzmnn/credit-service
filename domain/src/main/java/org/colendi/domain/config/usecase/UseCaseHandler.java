package org.colendi.domain.config.usecase;


import org.colendi.domain.UseCase;

public interface UseCaseHandler<R, T extends UseCase> {

    R handle(T useCase);
}

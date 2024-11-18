package org.colendi.domain.config.usecase;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@DomainComponent
public class BeanAwareUseCasePublisher implements UseCasePublisher {


    @Override
    @SuppressWarnings("unchecked")
    public <R, T extends UseCase> R publish(Class<R> returnClass, T useCase) {
        var useCaseHandler = (UseCaseHandler<R, T>) UseCaseHandlerRegistry.INSTANCE.detectUseCaseHandlerFrom(useCase.getClass());
        validateUseCaseHandlerDetection(useCase, useCaseHandler);
        return useCaseHandler.handle(useCase);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, T extends UseCase> void publish(T useCase) {
        var voidUseCaseHandler = (VoidUseCaseHandler<T>) UseCaseHandlerRegistry.INSTANCE.detectVoidUseCaseHandlerFrom(useCase.getClass());
        if (Objects.isNull(voidUseCaseHandler)) {
            var useCaseHandler = (UseCaseHandler<R, T>) UseCaseHandlerRegistry.INSTANCE.detectUseCaseHandlerFrom(useCase.getClass());
            validateUseCaseHandlerDetection(useCase, useCaseHandler);
            useCaseHandler.handle(useCase);
        } else {
            validateVoidUseCaseHandlerDetection(useCase, voidUseCaseHandler);
            voidUseCaseHandler.handle(useCase);
        }
    }

    @Override
    public <R, T extends UseCase> void publishAsync(T useCase) {
        CompletableFuture.runAsync(() -> publish(useCase)).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R publish(Class<R> returnClass) {
        var useCaseHandler = (NoUseCaseHandler<R>) UseCaseHandlerRegistry.INSTANCE.detectNoUseCaseHandlerFrom(returnClass);
        validateNoParamUseCaseHandlerDetection(useCaseHandler);
        return useCaseHandler.handle();
    }

    private <R, T extends UseCase> void validateUseCaseHandlerDetection(T useCase, UseCaseHandler<R, T> useCaseHandler) {
        if (Objects.isNull(useCaseHandler)) {
            throw new RuntimeException("paymentapi.useCaseHandler.notDetected");
        }
    }

    private <T extends UseCase> void validateVoidUseCaseHandlerDetection(T useCase, VoidUseCaseHandler<T> useCaseHandler) {
        if (Objects.isNull(useCaseHandler)) {
            throw new RuntimeException("paymentapi.useCaseHandler.notDetected");
        }
    }

    private <R> void validateNoParamUseCaseHandlerDetection(NoUseCaseHandler<R> useCaseHandler) {
        if (Objects.isNull(useCaseHandler)) {
            throw new RuntimeException("paymentapi.useCaseHandler.notDetected");
        }
    }
}

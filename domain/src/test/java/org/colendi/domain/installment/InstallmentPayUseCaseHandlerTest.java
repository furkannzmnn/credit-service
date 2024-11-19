package org.colendi.domain.installment;

import org.colendi.domain.config.exception.ErrorCode;
import org.colendi.domain.installment.model.Installment;
import org.colendi.domain.installment.model.InstallmentStatus;
import org.colendi.domain.installment.usecase.InstallmentPayUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InstallmentPayUseCaseHandlerTest {

    FakeInstallmentAdapter fakeInstallmentAdapter;
    FakeCacheAdapter cachePort;
    InstallmentPayUseCaseHandler installmentPayUseCaseHandler;

    @BeforeEach
    void setUp() {
        cachePort = new FakeCacheAdapter();
        fakeInstallmentAdapter = new FakeInstallmentAdapter();
        installmentPayUseCaseHandler = new InstallmentPayUseCaseHandler(fakeInstallmentAdapter, cachePort);
    }


    @Test
    void should_not_pay_installment_when_installment_is_locked() {
        cachePort.lock("1");

        InstallmentPayUseCase installmentPayUseCase = new InstallmentPayUseCase(1L, BigDecimal.TEN, 1L);
        installmentPayUseCaseHandler.handle(installmentPayUseCase);

        assertFalse(fakeInstallmentAdapter.wasRetrieveCalled());
        assertFalse(fakeInstallmentAdapter.wasRetrieveCalled());
    }

    @Test
    void should_pay_installment() {
        InstallmentPayUseCase installmentPayUseCase = new InstallmentPayUseCase(1L, BigDecimal.TEN, 1L);
        installmentPayUseCaseHandler.handle(installmentPayUseCase);

        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());
        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());
    }

    @Test
    void should_throw_exception_when_installment_not_found() {
        InstallmentPayUseCase installmentPayUseCase = new InstallmentPayUseCase(2L, BigDecimal.TEN, 1L);
        try {
            installmentPayUseCaseHandler.handle(installmentPayUseCase);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains(ErrorCode.INSTALLMENT_NOT_FOUND.getMessageKey()));
        }
    }

    @Test
    void should_throw_exception_when_installment_already_paid() {
        InstallmentPayUseCase installmentPayUseCase = new InstallmentPayUseCase(1L, BigDecimal.TEN, 1L);
        try {
            installmentPayUseCaseHandler.handle(installmentPayUseCase);
            installmentPayUseCaseHandler.handle(installmentPayUseCase);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains(ErrorCode.INSTALLMENT_ALREADY_PAID.getMessageKey()));
        }
    }

    @Test
    void should_pay_partially_installment() {
        InstallmentPayUseCase installmentPayUseCase = new InstallmentPayUseCase(1L, BigDecimal.valueOf(5), 1L);
        installmentPayUseCaseHandler.handle(installmentPayUseCase);

        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());
        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());

        Installment installmentPartial = fakeInstallmentAdapter.retrieve(1L);
        assertSame(installmentPartial.getStatus(), InstallmentStatus.PARTIALLY_PAID); ;


        InstallmentPayUseCase installmentPayUseCase2 = new InstallmentPayUseCase(1L, BigDecimal.valueOf(5), 1L);
        installmentPayUseCaseHandler.handle(installmentPayUseCase2);

        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());
        assertTrue(fakeInstallmentAdapter.wasRetrieveCalled());

        Installment installment = fakeInstallmentAdapter.retrieve(1L);
        assertSame(installment.getStatus(), InstallmentStatus.PAID); ;



    }

}
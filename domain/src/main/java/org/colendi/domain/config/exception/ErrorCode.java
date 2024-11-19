package org.colendi.domain.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INSTALLMENT_NOT_FOUND("installment.not.found"),
    SYSTEM_BUSY("system.busy"),
    INSTALLMENT_ALREADY_PAID("installment.already.paid");


    private final String messageKey;
    ErrorCode(String messageKey) {
        this.messageKey = messageKey;
    }

}

package org.colendi.domain.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INSTALLMENT_NOT_FOUND("installment.not.found"),
    SYSTEM_BUSY("system.busy"),;


    private final String messageKey;
    ErrorCode(String messageKey) {
        this.messageKey = messageKey;
    }
}

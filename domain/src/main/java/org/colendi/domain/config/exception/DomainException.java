package org.colendi.domain.config.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DomainException extends RuntimeException {
    private final String messageKey;

    public DomainException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }


}

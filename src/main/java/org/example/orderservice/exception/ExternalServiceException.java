package org.example.orderservice.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends ApplicationException {

    public ExternalServiceException(String message) {
        super(HttpStatus.BAD_GATEWAY, message);
    }
}

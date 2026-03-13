package org.example.orderservice.exception;

public class ApplicationException  extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
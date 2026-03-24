package org.example.orderservice.exception;

public record ApiErrorResponse(
        int status,
        String message
) {
}

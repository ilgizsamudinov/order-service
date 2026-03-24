package org.example.orderservice.dto;

public record OrderItemRequest(
        Long productId,
        Long orderId,
        Integer quantity
) {
}
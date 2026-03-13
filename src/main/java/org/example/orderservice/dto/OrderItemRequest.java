package org.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
        Long productId,
        Long orderId,
        Integer quantity
) {
}
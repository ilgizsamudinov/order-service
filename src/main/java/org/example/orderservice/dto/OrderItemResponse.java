package org.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity,
        BigDecimal amount
) {
}

package org.example.orderservice.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.orderservice.model.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderRequest(
        @NotNull Long userId,
        @NotNull OrderStatus orderStatus,
        @NotNull @PositiveOrZero BigDecimal totalAmount
) {
}

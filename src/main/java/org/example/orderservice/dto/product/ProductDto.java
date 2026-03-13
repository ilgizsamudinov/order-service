package org.example.orderservice.dto.product;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String title,
        String description,
        BigDecimal price) {
}

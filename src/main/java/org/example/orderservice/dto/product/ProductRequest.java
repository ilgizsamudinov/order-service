package org.example.orderservice.dto.product;

import java.math.BigDecimal;

public record ProductRequest(
        String title,
        String description,
        BigDecimal price,
        String sku,
        BigDecimal weight
) {
}

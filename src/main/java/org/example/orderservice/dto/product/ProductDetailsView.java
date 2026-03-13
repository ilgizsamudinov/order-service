package org.example.orderservice.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProductDetailsView {
    Long getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}

package org.example.orderservice.dto.order;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderDetailsResponse {
    Long getId();
    int getOrderStatus();
    BigDecimal getTotalAmount();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}

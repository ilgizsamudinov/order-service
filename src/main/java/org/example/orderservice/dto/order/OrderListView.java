package org.example.orderservice.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderListView {
    Long getId();
    LocalDateTime getCreatedAt();
    String getUsername();
    BigDecimal getTotalAmount();
}

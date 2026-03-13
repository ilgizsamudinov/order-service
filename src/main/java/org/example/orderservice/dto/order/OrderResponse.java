package org.example.orderservice.dto.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {

    private Long id;

    private int orderStatusId;

    private BigDecimal totalAmount;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private String username;


}

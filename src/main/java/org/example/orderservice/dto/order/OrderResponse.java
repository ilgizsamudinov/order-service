package org.example.orderservice.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {
    private Long id;
    private int orderStatusId;
    private LocalDateTime createdAt;
}

package org.example.orderservice.dto.product;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String sku;
    private BigDecimal weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

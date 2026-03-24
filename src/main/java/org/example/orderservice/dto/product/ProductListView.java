package org.example.orderservice.dto.product;

import java.math.BigDecimal;

public interface ProductListView {
    Long getId();
    String getTitle();
    BigDecimal getPrice();
    String getSku();
}

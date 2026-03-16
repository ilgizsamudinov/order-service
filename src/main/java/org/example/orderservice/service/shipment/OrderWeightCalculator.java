package org.example.orderservice.service.shipment;

import org.example.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderWeightCalculator {

    public BigDecimal calculate(Order order) {
        return order.getItems().stream()
                .map(item -> item.getProduct().getWeight()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
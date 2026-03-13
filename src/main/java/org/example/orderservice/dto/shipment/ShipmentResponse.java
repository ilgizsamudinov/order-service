package org.example.orderservice.dto.shipment;

import org.example.orderservice.model.ShipmentCarrier;
import org.example.orderservice.model.ShipmentStatus;

import java.time.LocalDateTime;

public record ShipmentResponse(
        Long id,
        Long orderId,
        String trackingNumber,
        ShipmentCarrier carrier,
        ShipmentStatus status,
        String shippingAddress,
        LocalDateTime shippedAt,
        LocalDateTime deliveredAt
) {
}

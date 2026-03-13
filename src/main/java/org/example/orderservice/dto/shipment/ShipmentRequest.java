package org.example.orderservice.dto.shipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.orderservice.model.ShipmentCarrier;

public record ShipmentRequest(
        @NotNull Long orderId,
        String trackingNumber,
        @NotNull ShipmentCarrier carrier,
        @NotBlank String shippingAddress
) {
}

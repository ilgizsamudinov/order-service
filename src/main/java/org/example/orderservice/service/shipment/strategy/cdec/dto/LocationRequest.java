package org.example.orderservice.service.shipment.strategy.cdec.dto;

import jakarta.validation.constraints.NotNull;

public record LocationRequest(
        @NotNull
        Integer code
) {
}

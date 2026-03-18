package org.example.orderservice.service.shipment.strategy.cdec.dto;

public record PackageRequest(
        Integer weight,
        Integer length,
        Integer width,
        Integer height
) {
}

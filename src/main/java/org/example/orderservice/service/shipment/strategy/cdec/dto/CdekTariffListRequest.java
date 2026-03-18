package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CdekTariffListRequest(
        @JsonProperty("from_location")
        LocationRequest fromLocation,
        @JsonProperty("to_location")
        LocationRequest toLocation,
        List<PackageRequest> packages
) {
}

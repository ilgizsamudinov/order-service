package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CdekTariffListWrapperResponse(
        @JsonProperty("tariff_codes")
        List<TariffListResponse> tariffCodes
) {
}

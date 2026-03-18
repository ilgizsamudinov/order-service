package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TariffListResponse(
        @JsonProperty("tariff_code")
        Integer tariffCode,
        @JsonProperty("tariff_name")
        String tariffName,
        @JsonProperty("tariff_description")
        String tariffDescription,
        @JsonProperty("delivery_sum")
        BigDecimal deliverySum,
        @JsonProperty("delivery_mode")
        Integer deliveryMode,
        @JsonProperty("period_min")
        Integer periodMin,
        @JsonProperty("period_max")
        Integer periodMax,
        @JsonProperty("calendar_min")
        Integer calendarMin,
        @JsonProperty("calendar_max")
        Integer calendarMax
) {
}

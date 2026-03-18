package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CdekTariffResponse(
        @JsonProperty("delivery_sum")
        BigDecimal deliverySum,
        @JsonProperty("period_min")
        Integer periodMin,
        @JsonProperty("period_max")
        Integer periodMax,
        @JsonProperty("calendar_min")
        Integer calendarMin,
        @JsonProperty("calendar_max")
        Integer calendarMax,
        @JsonProperty("weight_calc")
        Integer weightCalc,
        @JsonProperty("total_sum")
        BigDecimal totalSum,
        String currency
) {
}

package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CdekCityResponse(
        Integer code,

        @JsonProperty("city_uuid")
        String cityUuid,

        String city,

        @JsonProperty("kladr_code")
        String kladrCode,

        @JsonProperty("country_code")
        String countryCode,

        String country,
        String region,

        @JsonProperty("region_code")
        Integer regionCode,

        Double longitude,
        Double latitude,

        @JsonProperty("time_zone")
        String timeZone,

        @JsonProperty("payment_limit")
        Double paymentLimit
) {
}
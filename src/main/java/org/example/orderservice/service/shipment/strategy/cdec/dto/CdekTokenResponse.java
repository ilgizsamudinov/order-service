package org.example.orderservice.service.shipment.strategy.cdec.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CdekTokenResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Integer expiresIn,

        String scope,

        String jti
) {
}
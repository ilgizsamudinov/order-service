package org.example.orderservice.service.shipment.strategy.cdec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cdek")
public record CdekProperties(
        String baseUrl,
        String clientId,
        String clientSecret
) {
}

package org.example.orderservice.service.shipment.strategy.cdec.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CdekRestClientConfig {

    @Bean
    public RestClient cdekRestClient(RestClient.Builder builder, CdekProperties properties) {
        return builder
                .baseUrl(properties.baseUrl())
                .build();
    }
}
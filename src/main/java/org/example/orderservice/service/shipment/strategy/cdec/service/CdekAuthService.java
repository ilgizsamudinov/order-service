package org.example.orderservice.service.shipment.strategy.cdec.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.service.shipment.strategy.cdec.config.CdekProperties;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CdekAuthService {

    private final CdekProperties cdekProperties;
    private final RestClient cdekRestClient;

    private String cachedToken;
    private long expiresAt;

    public String getAccessToken() {

        if (cachedToken != null && System.currentTimeMillis() < expiresAt) {
            return cachedToken;
        }


        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", cdekProperties.clientId());
        formData.add("client_secret", cdekProperties.clientSecret());

        CdekTokenResponse response = cdekRestClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(CdekTokenResponse.class);

        if (response == null || response.accessToken() == null || response.accessToken().isBlank()) {
            throw new NotFoundException("CDEK did not return access_token");
        }

        this.cachedToken = response.accessToken();
        long expiresInSeconds = response.expiresIn();
        this.expiresAt = System.currentTimeMillis() + (expiresInSeconds - 60) * 1000;

        return cachedToken;
    }

}

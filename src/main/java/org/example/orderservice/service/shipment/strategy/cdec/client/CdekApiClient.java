package org.example.orderservice.service.shipment.strategy.cdec.client;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.ApplicationException;
import org.example.orderservice.exception.ExternalServiceException;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekCityResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffListRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffListWrapperResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffResponse;
import org.example.orderservice.service.shipment.strategy.cdec.service.CdekAuthService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CdekApiClient {

    private final RestClient cdekRestClient;
    private final CdekAuthService cdekAuthService;


    public List<CdekCityResponse> getCities(){
        String accessToken = cdekAuthService.getAccessToken();

        List<CdekCityResponse> response = cdekRestClient.get()
                .uri("/location/cities")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CdekCityResponse>>() {
                });

        if (response == null) {
            throw new NotFoundException("Cdek did not return cities");
        }

        return response;
    }

    public List<TariffListResponse> calculateTariffList(CdekTariffListRequest request) {
        String accessToken = cdekAuthService.getAccessToken();

        try {
            CdekTariffListWrapperResponse response = cdekRestClient.post()
                    .uri("/calculator/tarifflist")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .body(request)
                    .retrieve()
                    .body(CdekTariffListWrapperResponse.class);

            if (response == null || response.tariffCodes() == null) {
                throw new NotFoundException("CDEK did not return tariff list");
            }

            return response.tariffCodes();
        } catch (HttpClientErrorException exception) {
            String message = exception.getResponseBodyAsString();
            if (message == null || message.isBlank()) {
                message = exception.getStatusText();
            }
            throw new ApplicationException(HttpStatus.valueOf(exception.getStatusCode().value()), message);
        } catch (RestClientResponseException exception) {
            String message = exception.getResponseBodyAsString();
            if (message == null || message.isBlank()) {
                message = "CDEK request failed";
            }
            throw new ExternalServiceException(message);
        } catch (RestClientException exception) {
            throw new ExternalServiceException("CDEK is temporarily unavailable");
        }
    }

    public CdekTariffResponse calculateTariff(CdekTariffRequest request) {
        String accessToken = cdekAuthService.getAccessToken();

        try {
            CdekTariffResponse response = cdekRestClient.post()
                    .uri("/calculator/tariff")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .body(request)
                    .retrieve()
                    .body(CdekTariffResponse.class);

            if (response == null) {
                throw new NotFoundException("CDEK did not return tariff calculation");
            }

            return response;
        } catch (HttpClientErrorException exception) {
            String message = exception.getResponseBodyAsString();
            if (message == null || message.isBlank()) {
                message = exception.getStatusText();
            }
            throw new ApplicationException(HttpStatus.valueOf(exception.getStatusCode().value()), message);
        } catch (RestClientResponseException exception) {
            String message = exception.getResponseBodyAsString();
            if (message == null || message.isBlank()) {
                message = "CDEK request failed";
            }
            throw new ExternalServiceException(message);
        } catch (RestClientException exception) {
            throw new ExternalServiceException("CDEK is temporarily unavailable");
        }
    }
}

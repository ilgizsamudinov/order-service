package org.example.orderservice.service.shipment.strategy.cdec.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.service.shipment.strategy.cdec.client.CdekApiClient;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekCityResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffListRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CdekService {

    private final CdekApiClient cdekApiClient;


    public List<CdekCityResponse> getCities() {
        return cdekApiClient.getCities();
    }

    public List<TariffListResponse> calculateTariffList(CdekTariffListRequest request) {
        return cdekApiClient.calculateTariffList(request);
    }

    public CdekTariffResponse calculateTariff(CdekTariffRequest request) {
        return cdekApiClient.calculateTariff(request);
    }

}

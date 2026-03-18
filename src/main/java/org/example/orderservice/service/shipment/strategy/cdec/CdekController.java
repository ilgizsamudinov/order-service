package org.example.orderservice.service.shipment.strategy.cdec;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekCityResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffListRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekTariffResponse;
import org.example.orderservice.service.shipment.strategy.cdec.service.CdekService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CdekController {

    private final CdekService cdekService;


    @GetMapping("/cdek/cities")
    public List<CdekCityResponse> getCities() {
        return cdekService.getCities();
    }

    @PostMapping("/cdek/calculator/tarifflist")
    public List<TariffListResponse> calculateTariffList(@RequestBody CdekTariffListRequest request) {
        return cdekService.calculateTariffList(request);
    }

    @PostMapping("/cdek/calculator/tariff")
    public CdekTariffResponse calculateTariff(@RequestBody CdekTariffRequest request) {
        return cdekService.calculateTariff(request);
    }

}

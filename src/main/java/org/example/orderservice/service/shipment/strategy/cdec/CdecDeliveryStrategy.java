package org.example.orderservice.service.shipment.strategy.cdec;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.service.shipment.strategy.cdec.service.CdekService;
import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.service.shipment.DeliveryStrategy;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CdecDeliveryStrategy implements DeliveryStrategy {
    private final CdekService cdekService;


    @Override
    public CarrierType getCarrierType() {
        return CarrierType.CDEK;
    }


    @Override
    public DeliveryCalculationResponse calculate(BigDecimal orderWeight, TariffRequest tariffRequest) {

        Integer cdekWeight = toCdekWeight(orderWeight);

        CdekTariffRequest cdekTariffRequest = new CdekTariffRequest();
        cdekTariffRequest.setType(1);
        cdekTariffRequest.setLang("rus");
        cdekTariffRequest.setTariffCode(tariffRequest.getTariffCode());
        cdekTariffRequest.setFromLocation(tariffRequest.getFromLocation());
        cdekTariffRequest.setToLocation(tariffRequest.getToLocation());
        cdekTariffRequest.setPackages(List.of(
                new PackageRequest(cdekWeight, null, null, null)
        ));

        CdekTariffResponse cdekTariffResponse = cdekService.calculateTariff(cdekTariffRequest);

        if (cdekTariffResponse == null) {
            throw new NotFoundException("CDEK did not return tariff");
        }
        DeliveryCalculationResponse response = new DeliveryCalculationResponse();
        response.setPrice(cdekTariffResponse.deliverySum());
        response.setCarrierType(tariffRequest.getCarrierType());

        return response;
    }


    @Override
    public List<TariffListResponse> calculateTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest) {
        Integer cdekWeight = toCdekWeight(orderWeight);

        CdekTariffListRequest cdekTariffListRequest = new CdekTariffListRequest();
        cdekTariffListRequest.setFromLocation(deliveryRequest.getFromLocation());
        cdekTariffListRequest.setToLocation(deliveryRequest.getToLocation());
        cdekTariffListRequest.setPackages(List.of(
                new PackageRequest(cdekWeight, null, null, null)
        ));


        List<TariffListResponse> response = cdekService.calculateTariffList(cdekTariffListRequest);
        if (response == null || response.isEmpty()) {
            throw new NotFoundException("CDEK did not return tariff list");
        }
        return response;
    }





    private Integer toCdekWeight(BigDecimal orderWeightKg) {
        if (orderWeightKg == null) {
            throw new IllegalArgumentException("Order weight must not be null");
        }

        if (orderWeightKg.signum() <= 0) {
            throw new IllegalArgumentException("Order weight must be greater than zero");
        }

        return orderWeightKg
                .multiply(BigDecimal.valueOf(1000))
                .intValueExact();
    }
}

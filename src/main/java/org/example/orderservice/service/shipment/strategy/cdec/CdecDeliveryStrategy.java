package org.example.orderservice.service.shipment.strategy.cdec;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.service.shipment.strategy.cdec.service.CdekService;
import org.example.orderservice.model.CarrierType;
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
    public DeliveryCalculationResponse calculate(BigDecimal orderWeight, DeliveryTariff deliveryTariff) {
        if (orderWeight.compareTo(deliveryTariff.getBaseWeight()) <= 0) {
            return new DeliveryCalculationResponse(deliveryTariff.getBasePrice(), deliveryTariff.getCarrierType());
        }

        BigDecimal extraWeight = orderWeight.subtract(deliveryTariff.getBaseWeight());
        BigDecimal extraKg = extraWeight.setScale(0, RoundingMode.CEILING);


        return new DeliveryCalculationResponse(deliveryTariff.getBasePrice()
                .add(extraKg.multiply(deliveryTariff.getExtraPricePerKg())), deliveryTariff.getCarrierType());

    }


    @Override
    public List<TariffListResponse> calculateTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest) {
        CdekTariffListRequest cdekTariffListRequest = new CdekTariffListRequest(
                new LocationRequest(deliveryRequest.getFromLocation()),
                new LocationRequest(deliveryRequest.getToLocation()),
                deliveryRequest.getPackages()
        );

        List<TariffListResponse> response = cdekService.calculateTariffList(cdekTariffListRequest);
        if (response == null || response.isEmpty()) {
            throw new NotFoundException("CDEK did not return tariff list");
        }
        return response;
    }


    @Override
    public DeliveryCalculationResponse calculate2(BigDecimal orderWeight, TariffRequest tariffRequest) {

        CdekTariffRequest cdekTariffRequest = new CdekTariffRequest();
        cdekTariffRequest.setType(1);
        cdekTariffRequest.setLang("rus");
        cdekTariffRequest.setTariffCode(tariffRequest.getTariffCode());
        cdekTariffRequest.setFromLocation(tariffRequest.getFromLocation());
        cdekTariffRequest.setToLocation(tariffRequest.getToLocation());
        cdekTariffRequest.setPackages(tariffRequest.getPackages());

        CdekTariffResponse cdekTariffResponse = cdekService.calculateTariff(cdekTariffRequest);

        if (cdekTariffResponse == null) {
            throw new NotFoundException("CDEK did not return tariff");
        }
        DeliveryCalculationResponse response = new DeliveryCalculationResponse();
        response.setPrice(cdekTariffResponse.deliverySum());
        response.setCarrierType(tariffRequest.getCarrierType());


        return response;
    }
}

package org.example.orderservice.service.shipment.strategy.yldam;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.model.City;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.repository.DeliveryTariffRepository;
import org.example.orderservice.service.CityService;
import org.example.orderservice.service.shipment.DeliveryStrategy;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class YldamDeliveryStrategy implements DeliveryStrategy {

    private final CityService cityService;
    private final DeliveryTariffRepository deliveryTariffRepository;


    @Override
    public CarrierType getCarrierType() {
        return CarrierType.YLDAM_EXPRESS;
    }


    @Override
    public List<TariffListResponse> calculateTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest) {
        throw new UnsupportedOperationException("Tariff list is not supported for carrier=" + getCarrierType());
    }



    @Override
    public DeliveryCalculationResponse calculate(BigDecimal orderWeight, TariffRequest tariffRequest) {

        City fromLocationCity = cityService.getCityByCode(tariffRequest.getFromLocation().code());
        City toLocationCity = cityService.getCityByCode(tariffRequest.getToLocation().code());


        DeliveryTariff deliveryTariff = deliveryTariffRepository
                .findByCarrierType(
                        tariffRequest.getCarrierType()
                )
                .orElseThrow(() -> new NotFoundException(
                        "Tariff not found for carrier=" + tariffRequest.getCarrierType()

                ));


        if (orderWeight.compareTo(deliveryTariff.getBaseWeight()) <= 0) {
            return new DeliveryCalculationResponse(deliveryTariff.getBasePrice(), deliveryTariff.getCarrierType());
        }

        BigDecimal extraWeight = orderWeight.subtract(deliveryTariff.getBaseWeight());
        BigDecimal extraKg = extraWeight.setScale(0, RoundingMode.CEILING);


        return new DeliveryCalculationResponse(deliveryTariff.getBasePrice()
                .add(extraKg.multiply(deliveryTariff.getExtraPricePerKg())), deliveryTariff.getCarrierType());

    }

}

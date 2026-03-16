package org.example.orderservice.service.shipment.strategy.cdec;

import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.service.shipment.DeliveryPriceStrategy;
import org.example.orderservice.service.shipment.dto.DeliveryCalculationResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CdecDeliveryPriceStrategy implements DeliveryPriceStrategy {

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
}

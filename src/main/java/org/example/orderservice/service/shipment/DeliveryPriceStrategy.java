package org.example.orderservice.service.shipment;

import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.service.shipment.dto.DeliveryCalculationResponse;

import java.math.BigDecimal;

public interface DeliveryPriceStrategy {

    CarrierType getCarrierType();

    DeliveryCalculationResponse calculate(BigDecimal orderWeight, DeliveryTariff tariff);
}

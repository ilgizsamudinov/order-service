package org.example.orderservice.service.shipment;

import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.TariffRequest;

import java.math.BigDecimal;
import java.util.List;

public interface DeliveryStrategy {

    CarrierType getCarrierType();

    DeliveryCalculationResponse calculate(BigDecimal orderWeight, DeliveryTariff tariff);

    default List<TariffListResponse> calculateTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest) {
        throw new UnsupportedOperationException("Tariff list is not supported for carrier=" + getCarrierType());
    }


    default DeliveryCalculationResponse calculate2(BigDecimal orderWeight, TariffRequest tariffRequest) {
        throw new UnsupportedOperationException("Tariff calculation is not supported for carrier=" + getCarrierType());
    }


}

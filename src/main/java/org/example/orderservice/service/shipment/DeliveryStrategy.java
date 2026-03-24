package org.example.orderservice.service.shipment;

import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;

import java.math.BigDecimal;
import java.util.List;

public interface DeliveryStrategy {

    CarrierType getCarrierType();

    List<TariffListResponse> calculateTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest);

    DeliveryCalculationResponse calculate(BigDecimal orderWeight, TariffRequest tariffRequest);
}

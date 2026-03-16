package org.example.orderservice.service.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.orderservice.model.CarrierType;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class DeliveryCalculationRequest {
    private Long orderId;
    private CarrierType carrierType;
}

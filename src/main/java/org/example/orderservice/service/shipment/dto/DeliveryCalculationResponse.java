package org.example.orderservice.service.shipment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryCalculationResponse {

    private BigDecimal price;
    private CarrierType carrierType;
}

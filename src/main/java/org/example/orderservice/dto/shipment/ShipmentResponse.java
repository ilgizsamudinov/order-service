package org.example.orderservice.dto.shipment;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponse {

    private Long id;

    private Long orderId;

    private String carrierType;

    private BigDecimal totalWeight;

    private BigDecimal deliveryPrice;
}

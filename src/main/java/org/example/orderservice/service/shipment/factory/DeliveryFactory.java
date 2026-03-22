package org.example.orderservice.service.shipment.factory;

import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.service.shipment.DeliveryStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DeliveryFactory {

    private final Map<CarrierType, DeliveryStrategy> strategies;

    public DeliveryFactory(List<DeliveryStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        DeliveryStrategy::getCarrierType,
                        Function.identity()
                ));
    }


    public DeliveryStrategy getStrategy(CarrierType carrierType) {
        DeliveryStrategy strategy = strategies.get(carrierType);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported carrier type: " + carrierType);
        }

        return strategy;
    }
}

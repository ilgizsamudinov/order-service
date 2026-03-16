package org.example.orderservice.service.shipment.strategy.cdec;

import org.example.orderservice.model.CarrierType;
import org.example.orderservice.service.shipment.DeliveryPriceStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DeliveryFactory {

    private final Map<CarrierType, DeliveryPriceStrategy> strategies;

    public DeliveryFactory(List<DeliveryPriceStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        DeliveryPriceStrategy::getCarrierType,
                        Function.identity()
                ));
    }


    public DeliveryPriceStrategy getStrategy(CarrierType carrierType) {
        DeliveryPriceStrategy strategy = strategies.get(carrierType);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported carrier type: " + carrierType);
        }

        return strategy;
    }
}
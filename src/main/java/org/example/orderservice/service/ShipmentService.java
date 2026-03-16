package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.factory.DeliveryPriceStrategyFactory;
import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.repository.DeliveryTariffRepository;
import org.example.orderservice.repository.ShipmentRepository;
import org.example.orderservice.service.shipment.DeliveryPriceStrategy;
import org.example.orderservice.service.shipment.OrderWeightCalculator;
import org.example.orderservice.service.shipment.dto.DeliveryCalculationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final DeliveryTariffRepository deliveryTariffRepository;
    private final DeliveryPriceStrategyFactory strategyFactory;
    private final OrderService orderService;
    private final OrderWeightCalculator orderWeightCalculator;

    public Shipment createShipment(ShipmentRequest shipmentRequest) {

        Shipment shipment = new Shipment();
        Order order = orderService.getOrderById(shipmentRequest.getOrderId());
        BigDecimal orderWeight = orderWeightCalculator.calculate(order);

        shipment.setCarrierType(shipmentRequest.getCarrierType());
        shipment.setOriginAddress(shipmentRequest.getOriginAddress());
        shipment.setDestinationAddress(shipmentRequest.getDestinationAddress());
        shipment.setOrder(order);



        DeliveryCalculationResponse deliveryCalculationResponse = calculatePrice(order.getId(), shipmentRequest.getCarrierType());

        shipment.setTotalWeight(orderWeight);
        shipment.setDeliveryPrice(deliveryCalculationResponse.getPrice());

        return shipmentRepository.save(shipment);
    }







    public DeliveryCalculationResponse calculatePrice(Long orderId, CarrierType carrierType) {

        Order order = orderService.getOrderById(orderId);
        BigDecimal orderWeight = orderWeightCalculator.calculate(order);
        DeliveryTariff deliveryTariff = deliveryTariffRepository
                .findByCarrierType(
                        carrierType
                )
                .orElseThrow(() -> new NotFoundException(
                        "Tariff not found for carrier=" + carrierType
                ));

        DeliveryPriceStrategy strategy = strategyFactory.getStrategy(carrierType);

        return strategy.calculate(orderWeight, deliveryTariff);
    }
}

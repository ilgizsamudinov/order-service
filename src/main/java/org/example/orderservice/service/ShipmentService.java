package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.example.orderservice.exception.ConflictException;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.repository.ShipmentRepository;
import org.example.orderservice.service.shipment.DeliveryStrategy;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.service.shipment.factory.DeliveryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final DeliveryFactory strategyFactory;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Transactional
    public Shipment createShipment(ShipmentRequest shipmentRequest) {
        Order order = orderService.getOrderById(shipmentRequest.getOrderId());
        if (shipmentRepository.existsByOrder_Id(order.getId())) {
            throw new ConflictException("Shipment already exists for order=" + order.getId());
        }

        BigDecimal orderWeight = orderItemService.getOrderWeight(order.getId());
        if (orderWeight == null || orderWeight.signum() <= 0) {
            throw new ConflictException("Cannot create shipment for empty order");
        }

        DeliveryStrategy strategy = strategyFactory.getStrategy(shipmentRequest.getCarrierType());
        DeliveryCalculationResponse deliveryCalculationResponse = strategy.calculate(
                orderWeight,
                new TariffRequest(
                        shipmentRequest.getCarrierType(),
                        shipmentRequest.getTariffCode(),
                        shipmentRequest.getFromLocation(),
                        shipmentRequest.getToLocation()
                )
        );

        Shipment shipment = Shipment.builder()
                .order(order)
                .carrierType(deliveryCalculationResponse.getCarrierType())
                .fromLocation(String.valueOf(shipmentRequest.getFromLocation().code()))
                .toLocation(String.valueOf(shipmentRequest.getToLocation().code()))
                .totalWeight(orderWeight)
                .deliveryPrice(deliveryCalculationResponse.getPrice())
                .build();

        return shipmentRepository.save(shipment);
    }


    public List<TariffListResponse> calculateCdekTariffList(Long orderId, DeliveryRequest deliveryRequest) {
        BigDecimal orderWeight = orderItemService.getOrderWeight(orderId);
        if (orderWeight == null || orderWeight.signum() <= 0) {
            throw new ConflictException("Cannot create shipment for empty order");
        }
        DeliveryStrategy strategy = strategyFactory.getStrategy(deliveryRequest.getCarrierType());
        return strategy.calculateTariffList(orderWeight, deliveryRequest);
    }


    public DeliveryCalculationResponse calculatePrice(Long orderId, TariffRequest tariffRequest) {
        BigDecimal orderWeight = orderItemService.getOrderWeight(orderId);
        if (orderWeight == null || orderWeight.signum() <= 0) {
            throw new ConflictException("Cannot create shipment for empty order");
        }
        DeliveryStrategy strategy = strategyFactory.getStrategy(tariffRequest.getCarrierType());
        return strategy.calculate(orderWeight, tariffRequest);
    }

}

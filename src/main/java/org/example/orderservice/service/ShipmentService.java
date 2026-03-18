package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.repository.DeliveryTariffRepository;
import org.example.orderservice.repository.ShipmentRepository;
import org.example.orderservice.service.shipment.DeliveryStrategy;
import org.example.orderservice.service.shipment.OrderWeightCalculator;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.example.orderservice.service.shipment.factory.DeliveryFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final DeliveryTariffRepository deliveryTariffRepository;
    private final DeliveryFactory strategyFactory;
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

        DeliveryStrategy strategy = strategyFactory.getStrategy(carrierType);

        return strategy.calculate(orderWeight, deliveryTariff);
    }

    public List<TariffListResponse> calculateCdekTariffList(BigDecimal orderWeight, DeliveryRequest deliveryRequest) {
        DeliveryStrategy strategy = strategyFactory.getStrategy(CarrierType.CDEK);
        return strategy.calculateTariffList(orderWeight, deliveryRequest);
    }


    public DeliveryCalculationResponse calculatePrice2(Long orderId, TariffRequest tariffRequest) {

        Order order = orderService.getOrderById(orderId);
        BigDecimal orderWeight = orderWeightCalculator.calculate(order);


        DeliveryStrategy strategy = strategyFactory.getStrategy(tariffRequest.getCarrierType());


        return strategy.calculate2(orderWeight, tariffRequest);
    }



}

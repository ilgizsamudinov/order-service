package org.example.orderservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.service.shipment.strategy.cdec.dto.TariffListResponse;
import org.example.orderservice.mapper.ShipmentMapper;
import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.service.ShipmentService;
import org.example.orderservice.dto.shipment.DeliveryCalculationResponse;
import org.example.orderservice.dto.shipment.DeliveryRequest;
import org.example.orderservice.dto.shipment.TariffRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;


    @PostMapping
    public ResponseEntity<ShipmentResponse>  createShipment(@Valid @RequestBody ShipmentRequest shipmentRequest){
        Shipment shipment = shipmentService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentMapper.toResponse(shipment));
    }


    @PostMapping("/cdek/{orderId}/tarifflist")
    public ResponseEntity<List<TariffListResponse>> calculateCdekTariffList(
            @PathVariable Long orderId,
            @Valid @RequestBody DeliveryRequest deliveryRequest
    ) {
        return ResponseEntity.ok(
                shipmentService.calculateCdekTariffList(orderId, deliveryRequest)
        );
    }


    @PostMapping("/calculate/{orderId}")
    public ResponseEntity<DeliveryCalculationResponse> calculate(
            @PathVariable Long orderId,
            @Valid @RequestBody TariffRequest tariffRequest
    ) {
        return ResponseEntity.ok(
                shipmentService.calculatePrice(orderId, tariffRequest)
        );
    }
}

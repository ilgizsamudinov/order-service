package org.example.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.mapper.ShipmentMapper;
import org.example.orderservice.model.CarrierType;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.service.ShipmentService;
import org.example.orderservice.service.shipment.dto.DeliveryCalculationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;


    @PostMapping
    public ResponseEntity<ShipmentResponse>  createShipment(@RequestBody ShipmentRequest shipmentRequest){
        Shipment shipment = shipmentService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentMapper.toResponse(shipment));
    }


    @GetMapping("/calculate-price/{orderId}")
    public ResponseEntity<DeliveryCalculationResponse> calculateDeliveryPrice(
            @PathVariable Long orderId,
            @RequestParam CarrierType carrierType
    ) {
        return ResponseEntity.ok(
                shipmentService.calculatePrice(orderId, carrierType)
        );
    }
}

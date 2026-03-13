package org.example.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.mapper.ShipmentMapper;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;

    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(@Valid @RequestBody ShipmentRequest shipmentRequest) {
        Shipment shipment = shipmentMapper.toEntity(shipmentRequest);
        Shipment createdShipment = shipmentService.createShipment(shipment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shipmentMapper.toResponse(createdShipment));
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponse> getShipment(@PathVariable Long shipmentId) {
        Shipment shipment = shipmentService.getShipment(shipmentId);
        return ResponseEntity.ok(shipmentMapper.toResponse(shipment));
    }
}

package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.ConflictException;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.model.Shipment;
import org.example.orderservice.model.ShipmentStatus;
import org.example.orderservice.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipmentService  {

    private final ShipmentRepository shipmentRepository;
    private final OrderService orderService;

    @Transactional
    public Shipment createShipment(Shipment shipment) {
        Long orderId = shipment.getOrderId();
        orderService.getOrderById(orderId);

        if (shipmentRepository.existsByOrderId(orderId)) {
            throw new ConflictException("Shipment for this order already exists");
        }

        shipment.setStatus(ShipmentStatus.CREATED);
        return shipmentRepository.save(shipment);
    }

    @Transactional(readOnly = true)
    public Shipment getShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new NotFoundException("Shipment not found"));
    }
}

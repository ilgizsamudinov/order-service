package org.example.orderservice.mapper;

import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.model.Shipment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper  {
    ShipmentResponse toResponse(Shipment shipment);
}

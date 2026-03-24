package org.example.orderservice.mapper;

import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper  {
    @Mapping(target = "orderId", source = "order.id")
    ShipmentResponse toResponse(Shipment shipment);
}

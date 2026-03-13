package org.example.orderservice.mapper;

import org.example.orderservice.dto.shipment.ShipmentRequest;
import org.example.orderservice.dto.shipment.ShipmentResponse;
import org.example.orderservice.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "shippedAt", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    Shipment toEntity(ShipmentRequest shipmentRequest);

    ShipmentResponse toResponse(Shipment shipment);
}

package org.example.orderservice.dto.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.service.shipment.strategy.cdec.dto.PackageRequest;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    Integer fromLocation;
    Integer toLocation;
    List<PackageRequest> packages;
}

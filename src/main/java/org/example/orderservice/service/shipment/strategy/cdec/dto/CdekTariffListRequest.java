package org.example.orderservice.service.shipment.strategy.cdec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CdekTariffListRequest {
        @JsonProperty("from_location")
        LocationRequest fromLocation;
        @JsonProperty("to_location")
        LocationRequest toLocation;
        List<PackageRequest> packages;
}

package org.example.orderservice.dto.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.service.shipment.strategy.cdec.dto.LocationRequest;
import org.example.orderservice.service.shipment.strategy.cdec.dto.PackageRequest;
import org.example.orderservice.model.CarrierType;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffRequest{
    CarrierType carrierType;
    @JsonProperty("tariff_code")
    Integer tariffCode;
    @JsonProperty("from_location")
    LocationRequest fromLocation;
    @JsonProperty("to_location")
    LocationRequest toLocation;
    List<PackageRequest> packages;
}

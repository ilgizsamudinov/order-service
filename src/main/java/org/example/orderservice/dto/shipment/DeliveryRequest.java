package org.example.orderservice.dto.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.service.shipment.strategy.cdec.dto.LocationRequest;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    @NotNull
    CarrierType carrierType;

    @Valid
    @NotNull
    @JsonProperty("from_location")
    LocationRequest fromLocation;

    @Valid
    @NotNull
    @JsonProperty("to_location")
    LocationRequest toLocation;
}

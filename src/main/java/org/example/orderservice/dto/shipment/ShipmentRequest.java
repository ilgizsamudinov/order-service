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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipmentRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private CarrierType carrierType;

    @JsonProperty("tariff_code")
    private Integer tariffCode;

    @Valid
    @NotNull
    @JsonProperty("from_location")
    private LocationRequest fromLocation;

    @Valid
    @NotNull
    @JsonProperty("to_location")
    private LocationRequest toLocation;
}

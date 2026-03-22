package org.example.orderservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.model.persistence.converter.CarrierTypeConverter;
import org.example.orderservice.model.enums.CarrierType;

import java.math.BigDecimal;

@Entity
@Table(name = "delivery_tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CarrierTypeConverter.class)
    @Column(nullable = false, name = "carrier_type")
    private CarrierType carrierType;


    @Column(nullable = false, name = "base_weight", precision = 10, scale = 3)
    private BigDecimal baseWeight;

    @Column(nullable = false, name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false, name = "extra_per_kg", precision = 10, scale = 2)
    private BigDecimal extraPricePerKg;
}
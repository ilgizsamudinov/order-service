package org.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.orderservice.model.persistence.converter.CarrierTypeConverter;
import org.example.orderservice.model.enums.CarrierType;

import java.math.BigDecimal;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Convert(converter = CarrierTypeConverter.class)
    @Column(nullable = false)
    private CarrierType carrierType;


    @Column(nullable = false)
    private String fromLocation;

    @Column(nullable = false)
    private String toLocation;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal totalWeight;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal deliveryPrice;
}

package org.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String originAddress;

    @Column(nullable = false)
    private String destinationAddress;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal totalWeight;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal deliveryPrice;
}

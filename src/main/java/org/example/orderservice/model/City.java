package org.example.orderservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private Integer code;

    @Column(name = "city_uuid", nullable = false, unique = true)
    private String cityUuid;

    @Column(nullable = false)
    private String city;

    @Column(name = "kladr_code")
    private String kladrCode;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String country;

    private String region;

    @Column(name = "region_code")
    private Integer regionCode;

    private Double longitude;
    private Double latitude;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "payment_limit")
    private Double paymentLimit;
}
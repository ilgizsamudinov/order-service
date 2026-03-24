package org.example.orderservice.repository;

import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryTariffRepository extends JpaRepository<DeliveryTariff, Long> {
    Optional<DeliveryTariff> findByCarrierType(CarrierType carrierType);


    @Query(value = """
                SELECT *
                FROM delivery_tariffs t
                WHERE t.carrier_type = :carrierType
                  AND :distance BETWEEN t.min_distance_km AND t.max_distance_km
                LIMIT 1
            """, nativeQuery = true)
    Optional<DeliveryTariff> findByCarrierAndDistance(
            @Param("carrierType") Integer carrierType,
            @Param("distance") Double distance
    );

}

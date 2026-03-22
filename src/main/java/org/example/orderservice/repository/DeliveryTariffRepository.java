package org.example.orderservice.repository;

import org.example.orderservice.model.enums.CarrierType;
import org.example.orderservice.model.DeliveryTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryTariffRepository extends JpaRepository<DeliveryTariff, Long> {
    Optional<DeliveryTariff> findByCarrierType(CarrierType carrierType);
}

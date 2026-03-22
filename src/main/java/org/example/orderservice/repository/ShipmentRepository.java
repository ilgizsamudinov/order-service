package org.example.orderservice.repository;

import org.example.orderservice.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    boolean existsByOrder_Id(Long orderId);
}

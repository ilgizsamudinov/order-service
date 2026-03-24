package org.example.orderservice.repository;

import org.example.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = """
            select
                coalesce(sum(p.weight * oi.quantity), 0)
            from order_items oi
            join products p on p.id = oi.product_id
            where oi.order_id = :orderId
            """, nativeQuery = true)
    BigDecimal getOrderWeightById(@Param("orderId") Long orderId);
}

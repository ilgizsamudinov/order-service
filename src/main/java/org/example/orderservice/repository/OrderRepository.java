package org.example.orderservice.repository;

import org.example.orderservice.dto.order.OrderDetailsResponse;
import org.example.orderservice.dto.order.OrderListView;
import org.example.orderservice.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = """
            select
                o.id as id,
                o.order_status as orderStatus,
                o.total_amount as totalAmount,
                o.created_at as createdAt,
                o.updated_at as updatedAt
            from orders o
            where o.id = :id
            
            """, nativeQuery = true)
    Optional<OrderDetailsResponse> findOrderDetailsById(Long id);

    @Query(value = """
            select
                o.id as id,
                o.created_at as createdAt,
                u.username as username,
                coalesce(order_totals.total_amount, 0) as totalAmount
            from orders o
            join users u on u.id = o.user_id
            left join (
                select
                    oi.order_id as order_id,
                    sum(oi.quantity * p.price) as total_amount
                from order_items oi
                join products p on p.id = oi.product_id
                group by oi.order_id
            ) order_totals on order_totals.order_id = o.id
            order by o.created_at desc
            """,
            countQuery = """
            select count(*)
            from orders
            """,
            nativeQuery = true)
    Page<OrderListView> findAllOrders(Pageable pageable);
}



package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.order.OrderDetailsResponse;
import org.example.orderservice.dto.order.OrderListView;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.User;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final Clock clock;


    @Transactional
    public Order createOrder(Long userId) {
        LocalDateTime now = LocalDateTime.now(clock);
        User user = userService.getUserById(userId);
        Order order = Order.builder()
                .orderStatusId(1)
                .createdAt(now)
                .updatedAt(now)
                .user(user)
                .build();
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }


    @Transactional(readOnly = true)
    public OrderDetailsResponse getOrderDetailsById(Long orderId){
        return orderRepository.findOrderDetailsById(orderId)
                .orElseThrow(()-> new NotFoundException("Order details not found"));
    }


    @Transactional(readOnly = true)
    public Page<OrderListView> getAllOrders(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return orderRepository
                .findAllOrders(pageable);
    }
}

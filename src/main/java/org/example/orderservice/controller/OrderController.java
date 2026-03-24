package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.order.OrderDetailsResponse;
import org.example.orderservice.dto.order.OrderListView;
import org.example.orderservice.dto.order.OrderResponse;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @PostMapping("/{userId}/user")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long userId) {
        Order createdOrder = orderService.createOrder(userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderMapper.toResponse(createdOrder));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetailsById(orderId));
    }

    @GetMapping
    public ResponseEntity<Page<OrderListView>> getAllOrders(
            @RequestParam int page,
            @RequestParam int size)
    {
        return ResponseEntity.ok(orderService.getAllOrders(page, size));
    }

}

package org.example.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderItemRequest;
import org.example.orderservice.dto.OrderItemResponse;
import org.example.orderservice.mapper.OrderItemMapper;
import org.example.orderservice.model.OrderItem;
import org.example.orderservice.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrderItem(@Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItemRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderItemMapper.toResponse(createdOrderItem));
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getOrderItem(@PathVariable Long orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItem(orderItemId);
        return ResponseEntity.ok(orderItemMapper.toResponse(orderItem));
    }
}

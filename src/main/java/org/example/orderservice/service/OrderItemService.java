package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderItemRequest;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.model.Order;
import org.example.orderservice.model.OrderItem;
import org.example.orderservice.model.Product;
import org.example.orderservice.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        Order order = orderService.getOrderById(orderItemRequest.orderId());
        Product product = productService.getProductById(orderItemRequest.productId());

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .quantity(orderItemRequest.quantity())
                .build();

        BigDecimal amount = orderItem.calculateAmount();
        orderItem.setAmount(amount);
        order.addItem(orderItem);
        return orderItemRepository.save(orderItem);
    }


    public OrderItem getOrderItem(Long orderItem) {
        return orderItemRepository.findById(orderItem).orElseThrow(() -> new NotFoundException("Order item not found"));
    }


}

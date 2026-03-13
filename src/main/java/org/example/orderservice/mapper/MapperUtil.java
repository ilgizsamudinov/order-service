package org.example.orderservice.mapper;


import org.example.orderservice.dto.order.OrderResponse;
import org.example.orderservice.dto.order.OrderListView;
import org.springframework.stereotype.Service;

@Service
public class MapperUtil {
    public OrderResponse orderResponse(OrderListView view) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(view.getId());
        orderResponse.setCreatedAt(view.getCreatedAt());
        orderResponse.setUsername(view.getUsername());
        orderResponse.setTotalAmount(view.getTotalAmount());
        return orderResponse;
    }
}

package org.example.orderservice.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OrderStatus {

    NEW(1),
    PENDING_PAYMENT(2),
    PROCESSING(3),
    SHIPPED(4),
    DELIVERED(5),
    CANCELLED(6),
    REFUNDED(7);

    private static final Map<Integer, OrderStatus> BY_ID = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(OrderStatus::getId, Function.identity()));

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrderStatus fromId(Integer id) {
        if (id == null) {
            return null;
        }

        OrderStatus status = BY_ID.get(id);
        if (status == null) {
            throw new IllegalArgumentException("Unknown order status id: " + id);
        }
        return status;
    }
}

package com.oscar.microservice.architecture;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final Order order = new Order();

    public String createOrder() {
        return order.create();
    }

    public String findOrderHistory() {
        return order.history();
    }

    public String modifyOrder(Long orderId, String details) {
        return order.modify(orderId, details);
    }

    public String deleteOrder(Long orderId) {
        return order.delete(orderId);
    }
}

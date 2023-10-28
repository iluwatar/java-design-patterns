package com.oscar.microservice.architecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Order Service and Components
@Service
public class OrderService {
    @Autowired
    private OrderManagement orderManagement;

    public String createOrder() {
        return orderManagement.createOrder();
    }

    public String findOrderHistory() {
        return orderManagement.findOrderHistory();
    }

    public String modifyOrder(Long orderId, String details) {
        return orderManagement.modifyOrder(orderId, details);
    }

    public String deleteOrder(Long orderId) {
        return orderManagement.deleteOrder(orderId);
    }
}

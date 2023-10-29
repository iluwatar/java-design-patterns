package com.iluwatar.microservice.architecture;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Map<Long, String> orders = new HashMap<>();
    private long currentId = 0;

    public String create() {
        currentId++;
        orders.put(currentId, "Sample Order Details for ID: " + currentId + "details\n");
        return "Order Created with ID: " + currentId;
    }

    public String history() {
        return orders.toString();
    }

    public String modify(Long orderId, String details) {
        if (orders.containsKey(orderId)) {
            orders.put(orderId, details);
            return "Modified order with ID: " + orderId;
        }
        return "Order not found";
    }

    public String delete(Long orderId) {
        if (orders.containsKey(orderId)) {
            orders.remove(orderId);
            return "Deleted order with ID: " + orderId;
        }
        return "Order not found";
    }
}

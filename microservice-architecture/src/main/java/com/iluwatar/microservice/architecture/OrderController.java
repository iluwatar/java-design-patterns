package com.iluwatar.microservice.architecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Order Service and Components
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public String createOrder() {
        return orderService.createOrder();
    }

    @GetMapping("/history")
    public String findOrderHistory() {
        return orderService.findOrderHistory();
    }

    @PutMapping("/modify/{orderId}")
    public String modifyOrder(@PathVariable Long orderId, @RequestParam String details) {
        return orderService.modifyOrder(orderId, details);
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}

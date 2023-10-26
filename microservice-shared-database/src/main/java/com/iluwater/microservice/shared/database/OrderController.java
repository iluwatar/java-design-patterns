package com.iluwater.microservice.shared.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/getTotal/{customerId}")
    public Optional<String[]> findOrderTotalByCustomerId(@PathVariable int customerId) {
        try {
            return orderService.getOrderTotalByCustomerId(customerId);
        } catch (Exception e) {
            return Optional.of(new String[]{"Error getting total orders of " + customerId + " : " + e.getMessage()});
        }

    }

    @PostMapping("/create/{customerId}")
    public ResponseEntity<String> createOrder(@PathVariable int customerId, @RequestParam double amount) {
        try {
            String orderID = orderService.makeOrder(customerId, amount);
            return ResponseEntity.ok("Order created successfully with ID: " + orderID);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }
}


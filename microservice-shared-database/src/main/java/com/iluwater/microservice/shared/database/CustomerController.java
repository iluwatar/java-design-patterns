package com.iluwater.microservice.shared.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/get/{customerId}")
    public ResponseEntity<String[]> getCustomer(@PathVariable int customerId) {
        try {
            Optional<String[]> customer = customerService.getCustomerById(customerId);
            return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new String[]{"Error getting customer: " + e.getMessage()});
        }

    }

    @PutMapping("/creditLimit/{customerId}")
    public ResponseEntity<String> updateCreditLimit(@PathVariable int customerId, @RequestParam double newCreditLimit) {
        try {
            customerService.updateCreditLimit(customerId, newCreditLimit);
            return ResponseEntity.ok("Credit limit updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating credit limit.");
        }
    }

    @PostMapping("/create/{creditLimit}")
    public ResponseEntity<String> createCustomer(@PathVariable double creditLimit) {
        try {
            String customerID = customerService.newCustomer(creditLimit);
            return ResponseEntity.ok("Customer created successfully with UUID: " + customerID);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating customer: " + e.getMessage());
        }
    }
}


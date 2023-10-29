package com.iluwater.microservices.shared.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for handling customer-related endpoints.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  @Autowired
  private ICustomerService customerService;

  /**
   * Endpoint to retrieve a specific customer's details by their ID.
   *
   * @param customerId The ID of the customer to retrieve.
   * @return A ResponseEntity containing the customer's details or an error message.
   */
  @GetMapping("/get/{customerId}")
  public ResponseEntity<String[]> getCustomer(@PathVariable int customerId) {
    try {
      Optional<String[]> customer = customerService.getCustomerById(customerId);
      return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new String[]{"Error getting customer: " + e.getMessage()});
    }

  }

  /**
   * Endpoint to update the credit limit of a specific customer.
   *
   * @param customerId     The ID of the customer to update.
   * @param newCreditLimit The new credit limit value.
   * @return A ResponseEntity indicating the success or failure of the operation.
   */
  @PutMapping("/creditLimit/{customerId}")
  public ResponseEntity<String> updateCreditLimit(@PathVariable int customerId, @RequestParam double newCreditLimit) {
    try {
      customerService.updateCreditLimit(customerId, newCreditLimit);
      return ResponseEntity.ok("Credit limit updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating credit limit.");
    }
  }

  /**
   * Endpoint to create a new customer with a specified credit limit.
   *
   * @param creditLimit The credit limit for the new customer.
   * @return A ResponseEntity containing the ID of the newly created customer or an error message.
   */
  @PostMapping("/create/{creditLimit}")
  public ResponseEntity<String> createCustomer(@PathVariable double creditLimit) {
    try {
      String customerID = customerService.newCustomer(creditLimit);
      return ResponseEntity.ok("Customer created successfully with ID: " + customerID);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error creating customer: " + e.getMessage());
    }
  }
}

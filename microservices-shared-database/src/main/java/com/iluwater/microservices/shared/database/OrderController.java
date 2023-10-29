package com.iluwater.microservices.shared.database;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for handling order-related endpoints.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderServiceInterface orderService;

  /**
   * Endpoint to retrieve the total orders of a specific customer.
   *
   * @param customerId The ID of the customer whose orders are to be fetched.
   * @return An Optional containing a list of orders in String array format or an error message if not found.
   */
  @GetMapping("/getTotal/{customerId}")
  public Optional<String[]> findOrderTotalByCustomerId(@PathVariable int customerId) {
    try {
      return orderService.getOrderTotalByCustomerId(customerId);
    } catch (Exception e) {
      // Return an error message wrapped in an Optional
      return Optional.of(new String[]{"Error getting total orders of " + customerId + " : " + e.getMessage()});
    }
  }

  /**
   * Endpoint to create an order for a specific customer.
   *
   * @param customerId The ID of the customer making the order.
   * @param amount     The amount of the order.
   * @return A ResponseEntity containing a success message if the order was created or an error message if not.
   */
  @PostMapping("/create/{customerId}")
  public ResponseEntity<String> createOrder(@PathVariable int customerId, @RequestParam double amount) {
    try {
      String orderId = orderService.makeOrder(customerId, amount);
      // Return a success message with the created order ID
      return ResponseEntity.ok("Order created successfully with ID: " + orderId);
    } catch (Exception e) {
      // Return a bad request response with an error message
      return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
    }
  }
}

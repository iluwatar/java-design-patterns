/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.monolithic;

import com.iluwatar.monolithic.controller.OrderController;
import com.iluwatar.monolithic.controller.ProductController;
import com.iluwatar.monolithic.controller.UserController;
import com.iluwatar.monolithic.model.Product;
import com.iluwatar.monolithic.model.User;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Monolithic E-commerce application.
 * ------------------------------------------------------------------------
 * Monolithic architecture is a software design pattern where all components
 * of the application (presentation, business logic, and data access layers)
 * are part of a single unified codebase and deployable unit.
 * ------------------------------------------------------------------------
 * This example implements a monolithic architecture by integrating
 * user management, product management, and order placement within
 * the same application, sharing common resources and a single database.
 */

@SpringBootApplication
public class EcommerceApp implements CommandLineRunner {

  private static final Logger log = LogManager.getLogger(EcommerceApp.class);
  private final UserController userService;
  private final ProductController productService;
  private final OrderController orderService;
  /**
  * Initilizing controllers as services.
  * */
  public EcommerceApp(UserController userService, ProductController productService, OrderController orderService) {
    this.userService = userService;
    this.productService = productService;
    this.orderService = orderService;
  }
  /**
  * The main entry point for the Monolithic E-commerce application.
  * Initializes the Spring Boot application and starts the embedded server.
  */
  public static void main(String... args) {
    SpringApplication.run(EcommerceApp.class, args);
  }

  @Override
  public void run(String... args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    log.info("Welcome to the Monolithic E-commerce CLI!");
    while (true) {
      log.info("\nChoose an option:");
      log.info("1. Register User");
      log.info("2. Add Product");
      log.info("3. Place Order");
      log.info("4. Exit");
      log.info("Enter your choice: ");

      int userInput = scanner.nextInt();
      scanner.nextLine();

      switch (userInput) {
        case 1 -> registerUser(scanner);
        case 2 -> addProduct(scanner);
        case 3 -> placeOrder(scanner);
        case 4 -> {
          log.info("Exiting the application. Goodbye!");
          return;
        }
        default -> log.info("Invalid choice! Please try again.");
      }
    }
  }
  /**
   * Handles User Registration through user CLI inputs.
   * */
  protected void registerUser(Scanner scanner) {
    log.info("Enter user details:");
    log.info("Name: ");
    String name = scanner.nextLine();
    log.info("Email: ");
    String email = scanner.nextLine();
    log.info("Password: ");
    String password = scanner.nextLine();

    User user = new User(null, name, email, password);
    userService.registerUser(user);
    log.info("User registered successfully!");
  }
  /**
   * Handles the addition of products.
   * */
  protected void addProduct(Scanner scanner) {
    log.info("Enter product details:");
    log.info("Name: ");
    String name = scanner.nextLine();
    log.info("Description: ");
    String description = scanner.nextLine();
    log.info("Price: ");
    double price = scanner.nextDouble();
    log.info("Stock: ");
    int stock = scanner.nextInt();
    Product product = new Product(null, name, description, price, stock);
    scanner.nextLine();
    productService.addProduct(product);
    log.info("Product added successfully!");
  }
  /**
   * Handles Order Placement through user CLI inputs.
   */
  protected void placeOrder(Scanner scanner) {
    log.info("Enter order details:");
    log.info("User ID: ");
    long userId = scanner.nextLong();
    log.info("Product ID: ");
    long productId = scanner.nextLong();
    log.info("Quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();

    try {
      orderService.placeOrder(userId, productId, quantity);
      log.info("Order placed successfully!");
    } catch (Exception e) {
      log.info("Error placing order: {}", e.getMessage());
    }
  }
}

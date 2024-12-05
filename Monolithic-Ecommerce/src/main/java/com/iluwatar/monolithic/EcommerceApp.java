package com.iluwatar.monolithic;

import com.iluwatar.monolithic.controller.OrderCon;
import com.iluwatar.monolithic.controller.ProductCon;
import com.iluwatar.monolithic.controller.UserCon;
import com.iluwatar.monolithic.model.Products;
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
 */
@SpringBootApplication
public class EcommerceApp implements CommandLineRunner {

  private static final Logger log = LogManager.getLogger(EcommerceApp.class);
  private final UserCon userService;
  private final ProductCon productService;
  private final OrderCon orderService;
  /**
  * Initilizing controllers as services.
  * */
  public EcommerceApp(UserCon userService, ProductCon productService, OrderCon orderService) {
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
    Products product = new Products(null, name, description, price, stock);
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

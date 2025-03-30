package com.iluwatar.cleanArchitecture;

public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    ProductRepository productRepository = new InMemoryProductRepository();
    CartRepository cartRepository = new InMemoryCartRepository();
    OrderRepository orderRepository = new InMemoryOrderRepository();

    // Initialize Use Case
    ShoppingCartService shoppingCartUseCase =
        new ShoppingCartService(productRepository, cartRepository, orderRepository);

    // Initialize Controllers
    CartController cartController = new CartController(shoppingCartUseCase);
    OrderController orderController = new OrderController(shoppingCartUseCase);

    // Simulating User Operations
    String userId = "user123";
    cartController.addItemToCart(userId, "1", 1); // Add Laptop to cart
    cartController.addItemToCart(userId, "2", 2); // Add 2 Smartphones to cart

    System.out.println("Total: $" + cartController.calculateTotal(userId));

    Order order = orderController.checkout(userId);
    System.out.println(
        "Order placed! Order ID: " + order.getOrderId() + ", Total: $" + order.getTotalPrice());
  }
}

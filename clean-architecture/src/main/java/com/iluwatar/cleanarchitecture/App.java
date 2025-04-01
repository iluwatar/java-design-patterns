package com.iluwatar.cleanarchitecture;

import lombok.extern.slf4j.Slf4j;

/**
 * Clean Architecture ensures separation of concerns by organizing code into layers, making it scalable and maintainable.
 *
 * In the example there are  Entities (Core Models) – Product, Cart, Order handle business logic.
 * Use Cases (Application Logic) – ShoppingCartService manages operations like adding items and checkout.
 * Interfaces & Adapters – Repositories (CartRepository, OrderRepository) abstract data handling, while controllers (CartController, OrderController) manage interactions.
 */
@Slf4j
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

    ShoppingCartService shoppingCartUseCase =
        new ShoppingCartService(productRepository, cartRepository, orderRepository);

    CartController cartController = new CartController(shoppingCartUseCase);
    OrderController orderController = new OrderController(shoppingCartUseCase);

    String userId = "user123";
    cartController.addItemToCart(userId, "1", 1);
    cartController.addItemToCart(userId, "2", 2);

    LOGGER.info("Total: ${}" + cartController.calculateTotal(userId));

    Order order = orderController.checkout(userId);
    LOGGER.info("Order placed! Order ID: {}, Total: ${}", order.getOrderId(), order.getTotalPrice());
  }
}

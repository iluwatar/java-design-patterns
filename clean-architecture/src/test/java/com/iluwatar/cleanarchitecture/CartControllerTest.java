package com.iluwatar.cleanarchitecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CartControllerTest {

  private ShoppingCartService shoppingCartUseCase;
  private CartController cartController;

  @BeforeEach
  public void setUp() {
    ProductRepository productRepository = new InMemoryProductRepository();
    CartRepository cartRepository = new InMemoryCartRepository();
    OrderRepository orderRepository = new InMemoryOrderRepository();
    shoppingCartUseCase = new ShoppingCartService(productRepository, cartRepository, orderRepository);
    cartController = new CartController(shoppingCartUseCase);
  }

  @Test
  void testRemoveItemFromCart() {
    cartController.addItemToCart("user123", "1", 1);
    cartController.addItemToCart("user123", "2", 2);

    assertEquals(2000.0, cartController.calculateTotal("user123"));

    cartController.removeItemFromCart("user123", "1");

    assertEquals(1000.0, cartController.calculateTotal("user123"));
  }

  @Test
  void testRemoveNonExistentItem() {
    cartController.addItemToCart("user123", "2", 2);
    cartController.removeItemFromCart("user123", "999");

    assertEquals(1000.0, cartController.calculateTotal("user123"));
  }
}
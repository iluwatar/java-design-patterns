package com.iluwatar.cleanArchitecture;

import java.util.List;

public class ShoppingCartService {
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;

  public ShoppingCartService(ProductRepository productRepository,
                                 CartRepository cartRepository,
                                 OrderRepository orderRepository) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.orderRepository = orderRepository;
  }

  public void addItemToCart(String userId, String productId, int quantity) {
    Product product = productRepository.getProductById(productId);
    if (product != null) {
      cartRepository.addItemToCart(userId, product, quantity);
    }
  }
  public void removeItemFromCart(String userId, String productId) {
    cartRepository.removeItemFromCart(userId, productId);
  }

  public double calculateTotal(String userId) {
    return cartRepository.calculateTotal(userId);
  }

  public Order checkout(String userId) {
    List<Cart> items = cartRepository.getItemsInCart(userId);
    String orderId = "ORDER-" + System.currentTimeMillis(); // simple order ID generation
    Order order = new Order(orderId, items);
    orderRepository.saveOrder(order);
    cartRepository.clearCart(userId);
    return order;
  }
}

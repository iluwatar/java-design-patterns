package com.iluwatar.cleanArchitecture;

import java.util.List;

public interface CartRepository {
  void addItemToCart(String userId, Product product, int quantity);
  void removeItemFromCart(String userId, String productId);
  List<Cart> getItemsInCart(String userId);
  double calculateTotal(String userId);
  void clearCart(String userId);
}

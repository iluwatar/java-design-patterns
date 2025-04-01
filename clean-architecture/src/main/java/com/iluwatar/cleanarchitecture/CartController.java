package com.iluwatar.cleanarchitecture;

public class CartController {
  private final ShoppingCartService shoppingCartUseCase;

  public CartController(ShoppingCartService shoppingCartUseCase) {
    this.shoppingCartUseCase = shoppingCartUseCase;
  }

  public void addItemToCart(String userId, String productId, int quantity) {
    shoppingCartUseCase.addItemToCart(userId, productId, quantity);
  }


  public void removeItemFromCart(String userId, String productId) {
    shoppingCartUseCase.removeItemFromCart(userId, productId);
  }

  public double calculateTotal(String userId) {
    return shoppingCartUseCase.calculateTotal(userId);
  }
}

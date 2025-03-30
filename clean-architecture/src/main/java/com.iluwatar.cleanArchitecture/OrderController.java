package com.iluwatar.cleanArchitecture;

public class OrderController{
  private final ShoppingCartService shoppingCartUseCase;

  public OrderController(ShoppingCartService shoppingCartUseCase) {
    this.shoppingCartUseCase = shoppingCartUseCase;
  }

  public Order checkout(String userId) {
    return shoppingCartUseCase.checkout(userId);
  }
}

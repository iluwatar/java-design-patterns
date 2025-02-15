package com.iluwatar.clientsideuicomposition;

import java.util.Map;

/**
 * CartFrontend is a concrete implementation of FrontendComponent
 * that simulates fetching shopping cart data based on the user.
 */
public class CartFrontend extends FrontendComponent {

  /**
   * Fetches the current state of the shopping cart based on dynamic parameters
   * like user ID.
   *
   * @param params parameters that influence the cart data, e.g., "userId"
   * @return a string representing the items in the shopping cart for a given
   *     user
   */
  @Override
  protected String getData(Map<String, String> params) {
    String userId = params.getOrDefault("userId", "anonymous");
    return "Shopping Cart for user '" + userId + "': [Item 1, Item 2]";
  }
}

package com.iluwatar.messaging.exception;

public class RestaurantNotFoundException extends Exception{

  public RestaurantNotFoundException(long restaurantId) {
    super(restaurantId + "Is Not found");
  }
}


package com.iluwatar.publish.subscribe.model;

public enum WeatherContent {
  earthquake("earthquake tsunami warning"),
  flood("flood start evacuation"),
  tornado("tornado use storm cellars");

  private final String message;

  WeatherContent(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

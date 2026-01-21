package com.iluwatar.factory.method.example;

public class CarFactory {

  public static Car createCar(String type) {
    if (type.equalsIgnoreCase("sports")) {
      return new SportsCar();
    } else if (type.equalsIgnoreCase("suv")) {
      return new SuvCar();
    } else {
      throw new IllegalArgumentException("Unknown car type: " + type);
    }
  }
}

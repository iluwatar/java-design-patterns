package com.iluwatar.factory;

/**
 * Factory of cars.
 */
public class CarsFactory {
  
  /**
   * Factory method takes as parameter a car type and initiate the appropriate class.
   */
  public static Car getCar(CarType type) {
    return type.getConstructor().get();
  }
}

package com.iluwatar.simplefactory;

/**
 * Factory of cars.
 */
public class CarSimpleFactory {
  
  /**
   * Enumeration for different types of cars.
   */
  static enum CarType {
    FORD, FERRARI
  }
  
  /**
   * Factory method takes as parameter a car type and initiate the appropriate class.
   */
  public static Car getCar(CarType type) {
    switch (type) {
      case FORD: return new Ford();
      case FERRARI: return new Ferrari();
      default: throw new IllegalArgumentException("Model not supported.");
    }
  }
}

package com.iluwatar.factory;

import java.util.function.Supplier;

public enum CarType {
  
  /**
   * Enumeration for different types of cars.
   */
  FORD(Ford::new), 
  FERRARI(Ferrari::new);
  
  private final Supplier<Car> constructor; 
  
  CarType(Supplier<Car> constructor) {
    this.constructor = constructor;
  }
  
  public Supplier<Car> getConstructor() {
    return this.constructor;
  }
}

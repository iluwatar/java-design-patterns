package com.iluwatar.table.inheritance;

/**
 * Represents a truck, a type of vehicle with a specific load capacity.
 */
public class Truck extends Vehicle {
  private double loadCapacity;

  /**
   * Constructs a Truck object with the given parameters.
   *
   * @param year         the year of manufacture
   * @param make         the make of the truck
   * @param model        the model of the truck
   * @param loadCapacity the load capacity of the truck
   * @param id           the unique ID of the truck
   */
  public Truck(int year, String make, String model, double loadCapacity, int id) {
    super(year, make, model, id);
    if (loadCapacity <= 0) {
      throw new IllegalArgumentException("Load capacity must be positive.");
    }
    this.loadCapacity = loadCapacity;
  }

  /**
   * Sets the load capacity of the truck.
   *
   * @param capacity the new load capacity
   */
  public void setLoadCapacity(double capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Load capacity must be positive.");
    }
    this.loadCapacity = capacity;
  }

  /**
   * Retrieves the load capacity of the truck.
   *
   * @return the load capacity
   */
  public double getLoadCapacity() {
    return loadCapacity;
  }

  /**
   * Returns a string representation of the truck.
   *
   * @return a string with the truck's details
   */
  @Override
  public String toString() {
    return "Truck{"
        + "id=" + getId()
        + ", make='" + getMake() + '\''
        + ", model='" + getModel() + '\''
        + ", year=" + getYear()
        + ", payloadCapacity=" + getLoadCapacity()
        + '}';
  }
}


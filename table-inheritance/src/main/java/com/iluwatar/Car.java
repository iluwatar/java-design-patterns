package com.iluwatar;

/**
 * Represents a car with a specific number of doors.
 */
public class Car extends Vehicle {
  private int numDoors;

  /**
   * Constructs a Car object.
   *
   * @param year     the manufacturing year
   * @param make     the make of the car
   * @param model    the model of the car
   * @param numDoors the number of doors
   * @param id       the unique identifier for the car
   */
  public Car(int year, String make, String model, int numDoors, int id) {
    super(year, make, model, id);
    this.numDoors = numDoors;
  }

  /**
   * Sets the number of doors for the car.
   *
   * @param doors the number of doors
   */
  public void setNumDoors(int doors) {
    this.numDoors = doors;
  }

  /**
   * Gets the number of doors for the car.
   *
   * @return the number of doors
   */
  public int getNumDoors() {
    return numDoors;
  }

  @Override
  public String toString() {
    return "Car{"
        + "id=" + getId()
        + ", make='" + getMake() + '\''
        + ", model='" + getModel() + '\''
        + ", year=" + getYear()
        + ", numberOfDoors=" + getNumDoors()
        + '}';
  }
}


package com.iluwatar.table.inheritance;
import lombok.Getter;
/**
 * Represents a car with a specific number of doors.
 */

@Getter
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
    if (numDoors <= 0) {
      throw new IllegalArgumentException("Number of doors must be positive.");
    }
    this.numDoors = numDoors;
  }

  /**
   * Sets the number of doors for the car.
   *
   * @param doors the number of doors
   */
  public void setNumDoors(int doors) {
    if (doors <= 0) {
      throw new IllegalArgumentException("Number of doors must be positive.");
    }
    this.numDoors = doors;
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

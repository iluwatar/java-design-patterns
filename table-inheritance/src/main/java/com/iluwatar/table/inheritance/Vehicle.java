package com.iluwatar.table.inheritance;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a generic vehicle with basic attributes like make, model, year, and ID.
 */

@Setter
@Getter
public class Vehicle {

  private String make;
  private String model;
  private int year;
  private int id;

  /**
   * Constructs a Vehicle object with the given parameters.
   *
   * @param year  the year of manufacture
   * @param make  the make of the vehicle
   * @param model the model of the vehicle
   * @param id    the unique ID of the vehicle
   */
  public Vehicle(int year, String make, String model, int id) {
    this.make = make;
    this.model = model;
    this.year = year;
    this.id = id;
  }

  /**
   * Returns a string representation of the vehicle.
   *
   * @return a string with the vehicle's details
   */
  @Override
  public String toString() {
    return "Vehicle{"
        + "id=" + id
        + ", make='" + make + '\''
        + ", model='" + model + '\''
        + ", year=" + year
        + '}';
  }
}

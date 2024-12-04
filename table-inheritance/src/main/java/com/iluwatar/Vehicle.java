package com.iluwatar;

/**
 * Represents a generic vehicle with basic attributes like make, model, year, and ID.
 */
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
    this.make = make; // Added spaces around '='
    this.model = model;
    this.year = year;
    this.id = id;
  }

  /**
   * Sets the make of the vehicle.
   *
   * @param make the make to set
   */
  public void setMake(String make) {
    this.make = make; // Added spaces around '='
  }

  /**
   * Gets the make of the vehicle.
   *
   * @return the make
   */
  public String getMake() {
    return make;
  }

  /**
   * Sets the model of the vehicle.
   *
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model; // Added spaces around '='
  }

  /**
   * Gets the model of the vehicle.
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets the year of manufacture for the vehicle.
   *
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year; // Added spaces around '='
  }

  /**
   * Gets the year of manufacture for the vehicle.
   *
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Gets the unique ID of the vehicle.
   *
   * @return the ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique ID of the vehicle.
   *
   * @param id the ID to set
   */
  public void setId(int id) {
    this.id = id; // Added spaces around '='
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

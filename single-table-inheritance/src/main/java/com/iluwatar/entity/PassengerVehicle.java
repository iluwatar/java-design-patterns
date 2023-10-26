package com.iluwatar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An abstract class that extends the Vehicle class
 * and provides properties for the Passenger type of Vehicles.
 *
 * @see Vehicle
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PassengerVehicle extends Vehicle {

  private int noOfPassengers;

  protected PassengerVehicle(String manufacturer, String model, int noOfPassengers) {
    super(manufacturer, model);
    this.noOfPassengers = noOfPassengers;
  }

  @Override
  public String toString() {
    return super.toString();
  }

}

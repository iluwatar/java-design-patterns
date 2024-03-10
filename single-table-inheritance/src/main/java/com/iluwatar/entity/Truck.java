package com.iluwatar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class that extends the PassengerVehicle class
 * and provides the concrete inheritance implementation of the Car.
 *
 * @see TransportVehicle TransportVehicle
 * @see Vehicle Vehicle
 */
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "TRUCK")
public class Truck extends TransportVehicle {

  private int towingCapacity;

  public Truck(String manufacturer, String model, int loadCapacity, int towingCapacity) {
    super(manufacturer, model, loadCapacity);
    this.towingCapacity = towingCapacity;
  }

  // Overridden the toString method to specify the Vehicle object
  @Override
  public String toString() {
    return "Truck{ "
            + super.toString()
            + ", "
            + "towingCapacity="
            + towingCapacity
            + '}';
  }
}

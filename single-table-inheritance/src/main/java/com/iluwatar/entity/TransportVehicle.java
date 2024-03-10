package com.iluwatar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An abstract class that extends the Vehicle class
 * and provides properties for the Transport type of Vehicles.
 *
 * @see Vehicle
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class TransportVehicle extends Vehicle {

  private int loadCapacity;

  protected TransportVehicle(String manufacturer, String model, int loadCapacity) {
    super(manufacturer, model);
    this.loadCapacity = loadCapacity;
  }

}

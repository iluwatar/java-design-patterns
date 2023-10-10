package com.iluwatar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A class that extends the PassengerVehicle class
 * and provides the concrete inheritance implementation of the Car.
 *
 * @see PassengerVehicle PassengerVehicle
 * @see Vehicle Vehicle
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "CAR")
public class Car extends PassengerVehicle {

  private int engineCapacity;

  public Car(String manufacturer, String model, int noOfPassengers, int engineCapacity) {
    super(manufacturer, model, noOfPassengers);
    this.engineCapacity = engineCapacity;
  }

  // Overridden the toString method to specify the Vehicle object
  @Override
  public String toString() {
    return "Car{"
            + super.toString()
            + '}';
  }

}

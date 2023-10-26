package com.iluwatar.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An abstract class that is the root of the Vehicle Inheritance hierarchy
 * and basic provides properties for all the vehicles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VEHICLE_TYPE")
public abstract class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int vehicleId;

  private String manufacturer;

  private String model;

  protected Vehicle(String manufacturer, String model) {
    this.manufacturer = manufacturer;
    this.model = model;
  }

  @Override
  public String toString() {
    return "Vehicle{"
            + "vehicleId="
            + vehicleId
            + ", manufacturer='"
            + manufacturer
            + '\''
            + ", model='"
            + model
            + '}';
  }

}

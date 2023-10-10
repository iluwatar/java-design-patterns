package com.iluwatar.abstractEntity;

import lombok.*;

import javax.persistence.*;

/**
 * An abstract class that is the root of the Vehicle Inheritance hierarchy
 * and basic provides properties for all the vehicles
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

    public Vehicle(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model +
                '}';
    }

}

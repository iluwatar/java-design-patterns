package com.iluwatar.entity;

import com.iluwatar.abstractEntity.PassengerVehicle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A class that extends the PassengerVehicle class
 * and provides the concrete inheritance implementation of the Car
 *
 * @see com.iluwatar.abstractEntity.PassengerVehicle PassengerVehicle
 * @see com.iluwatar.abstractEntity.Vehicle Vehicle
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
        return "Car{" +
                super.toString() +
                '}';
    }

}

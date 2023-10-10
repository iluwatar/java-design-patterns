package com.iluwatar.entity;

import com.iluwatar.abstractEntity.TransportVehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A class that extends the PassengerVehicle class
 * and provides the concrete inheritance implementation of the Car
 *
 * @see com.iluwatar.abstractEntity.TransportVehicle TransportVehicle
 * @see com.iluwatar.abstractEntity.Vehicle Vehicle
 */
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "TRUCK")
public class Truck extends TransportVehicle {

    public int towingCapacity;

    public Truck(String manufacturer, String model, int loadCapacity, int towingCapacity) {
        super(manufacturer, model, loadCapacity);
        this.towingCapacity = towingCapacity;
    }

    // Overridden the toString method to specify the Vehicle object
    @Override
    public String toString() {
        return "Truck{ " +
                super.toString() + ", " +
                "towingCapacity=" + towingCapacity +
                '}';
    }
}

package com.iluwatar.abstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PassengerVehicle extends Vehicle {

    private int noOfPassengers;

    public PassengerVehicle(String manufacturer, String model, int noOfPassengers) {
        super(manufacturer, model);
        this.noOfPassengers = noOfPassengers;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

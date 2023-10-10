package com.iluwatar.abstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class TransportVehicle extends Vehicle {

    private int loadCapacity;

    public TransportVehicle(String manufacturer, String model, int loadCapacity) {
        super(manufacturer, model);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

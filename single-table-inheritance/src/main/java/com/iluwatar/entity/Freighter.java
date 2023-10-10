package com.iluwatar.entity;

import com.iluwatar.abstractEntity.TransportVehicle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "FREIGHTER")
public class Freighter extends TransportVehicle {

    private double flightLength;

    public Freighter(String manufacturer, String model, int countOfSeats, int loadCapacity, double flightLength) {
        super(manufacturer, model, loadCapacity);
        this.flightLength = flightLength;
    }

    @Override
    public String toString() {
        return "Freighter{ " +
                super.toString() + " ," +
                "flightLength=" + flightLength +
                '}';
    }

}

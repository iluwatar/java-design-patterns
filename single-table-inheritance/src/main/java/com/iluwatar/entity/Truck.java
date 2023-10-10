package com.iluwatar.entity;

import com.iluwatar.abstractEntity.TransportVehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "TRUCK")
public class Truck extends TransportVehicle {

    public int towingCapacity;

    public Truck(String manufacturer, String model, int loadCapacity, int towingCapacity) {
        super(manufacturer, model, loadCapacity);
        this.towingCapacity = towingCapacity;
    }


    @Override
    public String toString() {
        return "Truck{ " +
                super.toString() + ", " +
                "towingCapacity=" + towingCapacity +
                '}';
    }
}

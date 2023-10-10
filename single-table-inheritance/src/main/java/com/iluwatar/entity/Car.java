package com.iluwatar.entity;

import com.iluwatar.abstractEntity.PassengerVehicle;
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
@DiscriminatorValue(value = "CAR")
public class Car extends PassengerVehicle {

    private int engineCapacity;

    public Car(String manufacturer, String model, int noOfPassengers, int engineCapacity) {
        super(manufacturer, model, noOfPassengers);
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String toString() {
        return "Car{" +
                super.toString() +
                '}';
    }

}

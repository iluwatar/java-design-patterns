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
@DiscriminatorValue(value = "TRAIN")
public class Train extends PassengerVehicle {

    private int noOfCarriages;

    public Train(String manufacturer, String model, int noOfPassengers, int noOfCarriages) {
        super(manufacturer, model, noOfPassengers);
        this.noOfCarriages = noOfCarriages;
    }

    @Override
    public String toString() {
        return "Train{" +
                super.toString() +
                '}';
    }

}

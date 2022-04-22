package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class PassengerVehicle.
 * The annotation @Entity is used to tell Persistence
 * that this is a concrete class
 */
@Entity
@DiscriminatorValue(value = "Car")
public class Car extends PassengerVehicle {
    /**class private attribute.
     */
    private int engineCapacity;

    /**get method for private attribute.
     * @return int
     */
    public int getEngineCapacity() {
        return engineCapacity;
    }
    /**set method for private attribute.
     * @param val set value
     */
    public void setEngineCapacity(final int val) {
        this.engineCapacity = val;
    }

}

package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.MappedSuperclass;

/**abstract class of the abstract class Vehicle.
 * The annotation @MappedSuperclass is used to tell Persistence
 * that this is an abstract class
 */
@MappedSuperclass
public abstract class PassengerVehicle extends Vehicle {
    /**class private attribute.
     */
    private int noOfPassengers;
    /**get method for private attribute.
     * @return int
     */
    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    /**set method for private attribute.
     * @param val set value
     */
    public void setNoOfPassengers(final int val) {
        this.noOfPassengers = val;
    }

}

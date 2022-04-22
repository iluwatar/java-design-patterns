package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.MappedSuperclass;

/**abstract class of the abstract class Vehicle.
 * The annotation @MappedSuperclass is used to tell Persistence
 * that this is an abstract class
 */
@MappedSuperclass
public abstract class TransportationVehicle extends Vehicle {

    /**class private attribute.
     */
    private int loadCapacity;
    /**get method for private attribute.
     * @return int
     */
    public int getLoadCapacity() {
        return loadCapacity;
    }

    /**set method for private attribute.
     * @param val set value
     */
    public void setLoadCapacity(final int val) {
        this.loadCapacity = val;
    }

}

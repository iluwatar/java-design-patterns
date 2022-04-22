package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class PassengerVehicle.
 * The annotation @Entity is used to tell Persistence
 * that this is a concrete class
 */
@Entity
@DiscriminatorValue(value = "Train")
public class Train extends PassengerVehicle {
    /**class private attribute.
     */
    private int noOfCarriages;
    /**get method for private attribute.
     * @return int
     */
    public int getNoOfCarriages() {
        return noOfCarriages;
    }
    /**set method for private attribute.
     * @param val set value
     */
    public void setNoOfCarriages(final int val) {
        this.noOfCarriages = val;
    }

}

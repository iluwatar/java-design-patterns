package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class TransportationVehicle.
 * The annotation @Entity is used to tell Persistence
 * that this is a concrete class
 */
@Entity
@DiscriminatorValue(value = "Freighter")
public class Freighter extends TransportationVehicle {
    /**class private attribute.
     */
    private int lengthOfPlane;

    /**get method for private attribute.
     * @return int
     */
    public int getLengthOfPlane() {
        return lengthOfPlane;
    }
    /**set method for private attribute.
     * @param val set value
     */
    public void setLengthOfPlane(final int val) {
        this.lengthOfPlane = val;
    }

}

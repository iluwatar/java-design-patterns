package com.iluwatar.InheritanceMapper.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class Player.
 */
@Entity
@DiscriminatorValue(value = "Cricketer")
public class Cricketer extends Player {
    /**private field.
     */
    private double battlingAverage;

    /**Setter method.
     * @param val value
     */
    public void setBattlingAverage(final double val) {
        this.battlingAverage = val;
    }
    /**Getter method.
     * @return double
     */
    public double getBattlingAverage() {
        return battlingAverage;
    }
}

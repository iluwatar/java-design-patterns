package com.iluwatar.InheritanceMapper.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class Player.
 */
@Entity
@DiscriminatorValue(value = "Bowler")
public class Bowler extends Cricketer {
    /**private field.
     */
    private double bowlingAverage;

    /**Getter method.
     * @return double
     */
    public double getBowlingAverage() {
        return bowlingAverage;
    }
    /**Setter method.
     * @param val value
     */
    public void setBowlingAverage(final double val) {
        this.bowlingAverage = val;
    }
}

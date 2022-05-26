package com.iluwatar.InheritanceMapper.ClassObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**Concrete class of the abstract class Player.
 */
@Entity
@DiscriminatorValue(value = "Footballer")
public class Footballer extends Player {
    /**private field.
     */
    private String club;

    /**Setter method.
     * @param val value
     */
    public void setClub(final String val) {
        this.club = val;
    }
    /**Getter method.
     * @return String
     */
    public String getClub() {
        return club;
    }
}

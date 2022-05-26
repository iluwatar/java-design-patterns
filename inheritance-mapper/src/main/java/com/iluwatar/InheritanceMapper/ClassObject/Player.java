package com.iluwatar.InheritanceMapper.ClassObject;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.TableGenerator;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**Abstract Player class.
 */
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "Player")
@DiscriminatorColumn(name = "PLAYER_TYPE")
public abstract class Player {
    /**primary key for database.
     *
     */
    @TableGenerator(name = "PLAYER_GEN", table = "ID_GEN",
            pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PLAYER_GEN")
    private int id;
    /**private field.
     *
     */
    private String name;
    /**Getter method.
     * @return int
     */
    public int getId() {
        return id;
    }

    /**Setter method.
     * @param val value
     */
    public void setName(final String val) {
        this.name = val;
    }
    /**Getter method.
     * @return String
     */
    public String getName() {
        return name;
    }
}

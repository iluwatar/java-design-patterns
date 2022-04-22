package com.iluwatar.SingleTableInheritance.ClassObject;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**Main abstract class in the inheritance heirachy.
 *
 * The annotation @Table denoted the table name to map to.
 *
 * The annotation @DiscriminatorColumn denoted the column
 * that identify the class.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "VEHICLE")
@DiscriminatorColumn(name = "VEHICLE_TYPE")
public abstract class Vehicle {
    /**table generator for vehicle id.
     */
    @TableGenerator(name = "VEHICLE_GEN", table = "ID_GEN",
            pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "VEHICLE_GEN")

    /**class private attribute.
     */
    private int idVehicle;
    /**class private attribute.
     */
    private String manufacturer;

    /**get method for private attribute.
     * @return int
     */
    public int getIdVehicle() {
        return idVehicle;
    }


    /**set method for private attribute.
     * @param val set value
     */
    public void setIdVehicle(final int val) {
        this.idVehicle = val;
    }

    /**get method for private attribute.
     * @return String
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**set method for private attribute.
     * @param val set value
     */
    public void setManufacturer(final String val) {
        this.manufacturer = val;
    }

}

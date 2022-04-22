package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

import java.util.List;

/**
 * an abstract vehicle mapper to be inherited by concrete Vehicle mapper.
 *
 * @param <T> concrete Vehicle class
 */
public abstract class AbstractVehicleMapper<T> extends Mapper {
    /**
     * Save method for database update.
     *
     * @param v      Vehicle class object to save
     * @param update whelther to save as an update or insert
     * @return saved Vehicle
     */
    public Vehicle save(final Vehicle v, final boolean update) {
        super.save(v, update);
        return v;
    }

    /**
     * method for loading Vehicle object.
     *
     * @param rows a list of Vehicle from database
     * @return loaded Vehicle
     */
    public Vehicle load(final List<Vehicle> rows) {
        return super.load(rows);
    }

    /**
     * abstract update method for concrete mapper to override.
     *
     * @param v Vehicle
     * @return Vehicle
     */
    public abstract T update(Vehicle v);

    /**
     * abstract insert method for concrete mapper to override.
     *
     * @param v Vehicle
     * @return Vehicle
     */
    public abstract T insert(Vehicle v);

    /**
     * abstract find method for concrete mapper to override.
     *
     * @param id Vehicle id
     * @return Vehicle
     */
    public abstract T find(int id);


}


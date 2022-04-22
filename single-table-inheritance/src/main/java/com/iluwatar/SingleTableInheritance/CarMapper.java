package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Car;
import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

import java.util.List;

/**
 * Concrete Mapper for Car class.
 */
public class CarMapper extends AbstractVehicleMapper<Car> {

    /**
     * find method for Car class.
     *
     * @param id Vehicle id
     * @return Car
     */
    public Car find(final int id) {
        List<Vehicle> rows = abstractFind(id);
        return load(rows);
    }

    /**
     * override load method.
     *
     * @param rows a list of Vehicle from database
     * @return Car
     */
    public Car load(final List<Vehicle> rows) {
        Vehicle v = super.load(rows);
        if (v instanceof Car) {
            return (Car) v;
        } else {
            return null;
        }
    }

    /**
     * override Save method for database update.
     *
     * @param v      Vehicle class object to save
     * @param update whelther to save as an update or insert
     * @return Car
     */
    public Car save(final Vehicle v, final boolean update) {
        return (Car) super.save(v, update);
    }

    /**
     * update method for Car class.
     *
     * @param v Vehicle
     * @return Car
     */
    public Car update(final Vehicle v) {
        Car c = (Car) v;
        save(c, true);
        return c;
    }

    /**
     * insert method for Car class.
     *
     * @param v Vehicle
     * @return Car
     */
    public Car insert(final Vehicle v) {
        Car c = (Car) v;
        save(c, false);
        return c;
    }
}

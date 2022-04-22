package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Train;
import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

import java.util.List;

/**
 * Concrete Mapper for Train class.
 */
public class TrainMapper extends AbstractVehicleMapper<Train> {

    /**
     * find method for Train class.
     *
     * @param id Vehicle id
     * @return Train
     */
    public Train find(final int id) {
        List<Vehicle> rows = abstractFind(id);
        return load(rows);
    }

    /**
     * override load method.
     *
     * @param rows a list of Vehicle from database
     * @return Train
     */
    public Train load(final List<Vehicle> rows) {
        Vehicle v = super.load(rows);
        if (v instanceof Train) {
            return (Train) v;
        } else {
            return null;
        }
    }

    /**
     * override Save method for database update.
     *
     * @param v      Vehicle class object to save
     * @param update whelther to save as an update or insert
     * @return Train
     */
    public Train save(final Vehicle v, final boolean update) {
        return (Train) super.save(v, update);
    }

    /**
     * update method for Train class.
     *
     * @param v Vehicle
     * @return Train
     */
    public Train update(final Vehicle v) {
        save(v, true);
        return (Train) v;
    }

    /**
     * insert method for Car class.
     *
     * @param v Vehicle
     * @return Train
     */
    public Train insert(final Vehicle v) {
        save(v, false);
        return (Train) v;
    }
}

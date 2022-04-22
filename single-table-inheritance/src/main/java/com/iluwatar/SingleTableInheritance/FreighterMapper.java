package com.iluwatar.SingleTableInheritance;


import com.iluwatar.SingleTableInheritance.ClassObject.Freighter;
import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

import java.util.List;

/**
 * Concrete Mapper for Freighter class.
 */
public class FreighterMapper extends AbstractVehicleMapper<Freighter> {
    /**
     * find method for Freighter class.
     *
     * @param id Vehicle id
     * @return Freighter
     */
    public Freighter find(final int id) {
        List<Vehicle> rows = abstractFind(id);
        return load(rows);
    }

    /**
     * override load method.
     *
     * @param rows a list of Vehicle from database
     * @return Freighter
     */
    public Freighter load(final List<Vehicle> rows) {
        Vehicle v = super.load(rows);
        if (v instanceof Freighter) {
            return (Freighter) v;
        } else {
            return null;
        }
    }


    /**
     * override Save method for database update.
     *
     * @param v      Vehicle class object to save
     * @param update whelther to save as an update or insert
     * @return Freighter
     */
    public Freighter save(final Vehicle v, final boolean update) {
        return (Freighter) super.save(v, update);
    }

    /**
     * update method for Car class.
     *
     * @param v Vehicle
     * @return Freighter
     */
    public Freighter update(final Vehicle v) {
        save(v, true);
        return (Freighter) v;
    }

    /**
     * insert method for Car class.
     *
     * @param v Vehicle
     * @return Freighter
     */
    public Freighter insert(final Vehicle v) {
        save(v, false);
        return (Freighter) v;
    }
}

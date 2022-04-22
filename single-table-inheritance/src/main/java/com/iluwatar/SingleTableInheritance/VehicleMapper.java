package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Car;
import com.iluwatar.SingleTableInheritance.ClassObject.Freighter;
import com.iluwatar.SingleTableInheritance.ClassObject.Train;
import com.iluwatar.SingleTableInheritance.ClassObject.Vehicle;

/**
 * Main Vehicle Mapper that encapsulate all other Mappers.
 */
public class VehicleMapper extends Mapper {
    /**
     * instance of CarMapper.
     */
    private final CarMapper carMapper = new CarMapper();
    /**
     * instance of TrainMapper.
     */
    private final TrainMapper trainMapper = new TrainMapper();
    /**
     * instance of FreighterMapper.
     */
    private final FreighterMapper freighterMapper = new FreighterMapper();

    /**
     * method that return a mapper for a vehicle subclass.
     * @param v Vehicle
     * @return Mapper
     */
    private AbstractVehicleMapper<? extends Vehicle> mapperFor(final Vehicle v) {
        if (v instanceof Car) {
            return carMapper;
        } else if (v instanceof Train) {
            return trainMapper;
        } else if (v instanceof Freighter) {
            return freighterMapper;
        } else {
            return null;
        }
    }

    /**
     * update method to be called by client.
     * @param v Vehicle
     * @return Vehicle
     */
    Vehicle update(final Vehicle v) {
        Vehicle found = mapperFor(v).find(v.getIdVehicle());
        if (found != null) {
            mapperFor(v).update(v);
            return v;
        }
        return null;
    }
    /**
     * insert method to be called by client.
     * @param v Vehicle
     * @return Vehicle
     */
    Vehicle insert(final Vehicle v) {
        mapperFor(v).insert(v);
        return v;
    }

    /**
     * delete method to be called by client.
     * @param v Vehicle
     */
    void delete(final Vehicle v) {
        mapperFor(v).delete(v);
    }

    /**
     * find method to be called by client.
     * @param id Vehicle id
     * @return Vehicle
     */
    Vehicle find(final int id) {
        Vehicle result;
        result = carMapper.find(id);
        if (result == null) {
            result = freighterMapper.find(id);
        }
        if (result == null) {
            result = trainMapper.find(id);
        }
        return result;
    }
}

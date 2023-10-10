package com.iluwatar.service;

import com.iluwatar.abstractEntity.Vehicle;
import com.iluwatar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * A service class that is used to provide the business logic
 * for the Vehicle class and connect to the database to
 * perform the CRUD operations on the root Vehicle class
 *
 * @see com.iluwatar.abstractEntity.Vehicle
 */
@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    /**
     * A method to save all the vehicles to the database
     *
     * @param vehicle Vehicle bbject
     *
     * @see com.iluwatar.abstractEntity.Vehicle
     */
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * A method to get a specific vehicle from vehicle id
     *
     * @param vehicleId Vehicle Id
     *
     * @see com.iluwatar.abstractEntity.Vehicle
     */
    public Vehicle getVehicle(int vehicleId){
        return vehicleRepository.findById(vehicleId).orElse(null);
    }

    /**
     * A method to get all the vehicles saved in the database
     *
     * @see com.iluwatar.abstractEntity.Vehicle
     */
    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    /**
     * A method to update a vehicle in the database
     *
     * @param vehicle Vehicle object
     *
     * @see com.iluwatar.abstractEntity.Vehicle
     */
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * A method to save all the vehicles to the database
     *
     * @param vehicle Vehicle object
     *
     * @see com.iluwatar.abstractEntity.Vehicle
     */
    public void deleteVehicle(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

}

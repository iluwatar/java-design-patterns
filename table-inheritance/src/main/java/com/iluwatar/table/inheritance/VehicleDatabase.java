package com.iluwatar.table.inheritance;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Manages the storage and retrieval of Vehicle objects, including Cars and Trucks.
 */
public class VehicleDatabase {

  final Logger logger = Logger.getLogger(VehicleDatabase.class.getName());

  private Map<Integer, Vehicle> vehicleTable = new HashMap<>();
  private Map<Integer, Car> carTable = new HashMap<>();
  private Map<Integer, Truck> truckTable = new HashMap<>();

  /**
   * Saves a vehicle to the database. If the vehicle is a Car or Truck, it is added to the respective table.
   *
   * @param vehicle the vehicle to save
   */
  public void saveVehicle(Vehicle vehicle) {
    vehicleTable.put(vehicle.getId(), vehicle);
    if (vehicle instanceof Car) {
      carTable.put(vehicle.getId(), (Car) vehicle);
    } else if (vehicle instanceof Truck) {
      truckTable.put(vehicle.getId(), (Truck) vehicle);
    }
  }

  /**
   * Retrieves a vehicle by its ID.
   *
   * @param id the ID of the vehicle
   * @return the vehicle with the given ID, or null if not found
   */
  public Vehicle getVehicle(int id) {
    return vehicleTable.get(id);
  }

  /**
   * Retrieves a car by its ID.
   *
   * @param id the ID of the car
   * @return the car with the given ID, or null if not found
   */
  public Car getCar(int id) {
    return carTable.get(id);
  }

  /**
   * Retrieves a truck by its ID.
   *
   * @param id the ID of the truck
   * @return the truck with the given ID, or null if not found
   */
  public Truck getTruck(int id) {
    return truckTable.get(id);
  }

  /**
   * Prints all vehicles in the database.
   */
  public void printAllVehicles() {
    for (Vehicle vehicle : vehicleTable.values()) {
      logger.info(vehicle.toString());
    }
  }
}


package com.iluwatar;

import java.util.HashMap;
import java.util.Map;

public class Vehicle_Database {
  private Map<Integer, Vehicle> vehicleTable = new HashMap<>();
  private Map<Integer, Car> carTable = new HashMap<>();
  private Map<Integer, Truck> truckTable = new HashMap<>();

  public void saveVehicle(Vehicle vehicle){
    vehicleTable.put(vehicle.getId(), vehicle);
    if(vehicle instanceof Car)
      carTable.put(vehicle.getId(), (Car)vehicle);
    else if (vehicle instanceof Truck)
      truckTable.put(vehicle.getId(), (Truck)vehicle);
  }

  public Vehicle getVehicle(int ID) {
    return vehicleTable.get(ID);
  }

  public Car getCar(int id) {
    return carTable.get(id);
  }

  public Truck getTruck(int id) {
    return truckTable.get(id);
  }

  public void printAllVehicles() {
    for (Vehicle vehicle : vehicleTable.values()) {
      System.out.println(vehicle);
    }
  }
}

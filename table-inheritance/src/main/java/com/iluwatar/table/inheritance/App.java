package com.iluwatar.table.inheritance;

/**
 * The main entry point of the application demonstrating the use of vehicles.
 */
public class App {
  /**
   * The main method to demonstrate adding and retrieving vehicles from the database.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    VehicleDatabase database = new VehicleDatabase();

    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    Truck truck = new Truck(2018, "Ford", "F-150", 60, 2);

    database.saveVehicle(car);
    database.saveVehicle(truck);

    database.printAllVehicles();

    Vehicle vehicle = database.getVehicle(car.getId());
    System.out.println("Retrieved Vehicle: " + vehicle);

    Car retrievedCar = database.getCar(car.getId());
    System.out.println("Retrieved Car: " + retrievedCar);

    Truck retrievedTruck = database.getTruck(truck.getId());
    System.out.println("Retrieved Truck: " + retrievedTruck);
  }
}

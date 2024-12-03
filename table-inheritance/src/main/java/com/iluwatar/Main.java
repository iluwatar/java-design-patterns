package com.iluwatar;

public class Main {
  public static void main(String[] args) {
    Vehicle_Database database = new Vehicle_Database();

    Car car = new Car(2020, "Toyota", "Corolla", 4, 1);
    Truck truck = new Truck(2018, "Ford", "F-150", 60,2);

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
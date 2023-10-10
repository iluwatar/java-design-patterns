package com.iluwatar;

import com.iluwatar.entity.Truck;
import com.iluwatar.abstractEntity.Vehicle;
import com.iluwatar.entity.Car;
import com.iluwatar.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * It's the main class where the whole Spring Boot Program Runs.
 *
 *
 */
@SpringBootApplication
@AllArgsConstructor
public class SingleTableInheritance implements CommandLineRunner {

    private final VehicleService vehicleService;

    public static void main(String[] args) {
        SpringApplication.run(SingleTableInheritance.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println();

        // Saving Car to DB as a Vehicle
        Vehicle vehicle1 = new Car("Tesla", "Model S", 4, 825);
        Vehicle car1 = vehicleService.saveVehicle(vehicle1);
        System.out.format("Vehicle 1 saved : %s\n" , car1);

        // Saving Truck to DB as a Vehicle
        Vehicle vehicle2 = new Truck("Ford", "F-150", 3325, 14000);
        Vehicle truck1 = vehicleService.saveVehicle(vehicle2);
        System.out.format("Vehicle 2 saved : %s\n" , truck1);

        System.out.println();

        // Fetching the Car from DB
        Car savedCar1 = (Car) vehicleService.getVehicle(vehicle1.getVehicleId());
        System.out.format("Fetching Car1 from DB : %s\n" , savedCar1);

        // Fetching the Truck from DB
        Truck savedTruck1 = (Truck) vehicleService.getVehicle(vehicle2.getVehicleId());
        System.out.format("Fetching Truck1 from DB : %s\n" , savedTruck1);

        System.out.println();

        // Fetching the Vehicles present in the DB
        List<Vehicle> allVehiclesFromDB = vehicleService.getAllVehicles();
        System.out.println("Fetching all vehicles from DB :");
        allVehiclesFromDB.forEach(System.out::println);

        System.out.println();

    }
}
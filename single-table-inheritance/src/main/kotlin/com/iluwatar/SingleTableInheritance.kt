/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar

// ABOUTME: Spring Boot application demonstrating the Single Table Inheritance JPA pattern.
// ABOUTME: Maps an inheritance hierarchy (Vehicle -> Car/Train/Truck/Freighter) to one database table.

import com.iluwatar.entity.Car
import com.iluwatar.entity.Truck
import com.iluwatar.service.VehicleService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

private val logger = KotlinLogging.logger {}

/**
 * Single Table Inheritance pattern:
 * It maps each instance of class in an inheritance tree into a single table.
 *
 * In this project, to specify Single Table Inheritance to Hibernate we annotate the main Vehicle
 * root class with @Inheritance(strategy = InheritanceType.SINGLE_TABLE). This creates a single
 * root **Vehicle** table in the database with columns for all fields of its subclasses
 * (Car, Freighter, Train, Truck).
 *
 * Additionally, a separate **"VEHICLE_TYPE"** discriminator column is added to the Vehicle table
 * to save the type of subclass object being stored. This value is specified by the
 * @DiscriminatorValue annotation for each subclass in Hibernate.
 *
 * This is the main Spring Boot Application class. It implements CommandLineRunner to run
 * statements at application startup.
 */
@SpringBootApplication
class SingleTableInheritance(private val vehicleService: VehicleService) : CommandLineRunner {

    override fun run(vararg args: String) {
        logger.info { "Saving Vehicles :- " }

        // Saving Car to DB as a Vehicle
        val vehicle1 = Car("Tesla", "Model S", 4, 825)
        val car1 = vehicleService.saveVehicle(vehicle1)
        logger.info { "Vehicle 1 saved : $car1" }

        // Saving Truck to DB as a Vehicle
        val vehicle2 = Truck("Ford", "F-150", 3325, 14000)
        val truck1 = vehicleService.saveVehicle(vehicle2)
        logger.info { "Vehicle 2 saved : $truck1\n" }

        logger.info { "Fetching Vehicles :- " }

        // Fetching the Car from DB
        val savedCar1 = vehicleService.getVehicle(vehicle1.vehicleId) as Car
        logger.info { "Fetching Car1 from DB : $savedCar1" }

        // Fetching the Truck from DB
        val savedTruck1 = vehicleService.getVehicle(vehicle2.vehicleId) as Truck
        logger.info { "Fetching Truck1 from DB : $savedTruck1\n" }

        logger.info { "Fetching All Vehicles :- " }

        // Fetching all Vehicles from the DB
        val allVehiclesFromDb = vehicleService.getAllVehicles()
        allVehiclesFromDb.forEach { logger.info { it.toString() } }
    }
}

/**
 * The entry point of the Spring Boot Application.
 *
 * @param args program runtime arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(SingleTableInheritance::class.java, *args)
}

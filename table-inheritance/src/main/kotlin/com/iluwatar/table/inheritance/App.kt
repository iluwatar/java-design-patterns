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

// ABOUTME: Main entry point demonstrating the Table Inheritance pattern with vehicles.
// ABOUTME: Shows how Car and Truck objects are stored in separate tables linked by shared keys.
package com.iluwatar.table.inheritance

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The main entry point of the application demonstrating the use of vehicles.
 *
 * The Table Inheritance pattern models a class hierarchy in a relational database by creating
 * separate tables for each class in the hierarchy. These tables share a common primary key, which
 * in subclass tables also serves as a foreign key referencing the primary key of the base class
 * table. This linkage maintains relationships and effectively represents the inheritance structure.
 * This pattern enables the organization of complex data models, particularly when subclasses have
 * unique properties that must be stored in distinct tables.
 */
fun main() {
    val database = VehicleDatabase()

    val car = Car(2020, "Toyota", "Corolla", 4, 1)
    val truck = Truck(2018, "Ford", "F-150", 60.0, 2)

    database.saveVehicle(car)
    database.saveVehicle(truck)

    database.printAllVehicles()

    val vehicle = database.getVehicle(car.id)
    val retrievedCar = database.getCar(car.id)
    val retrievedTruck = database.getTruck(truck.id)

    logger.info { "Retrieved Vehicle: $vehicle" }
    logger.info { "Retrieved Car: $retrievedCar" }
    logger.info { "Retrieved Truck: $retrievedTruck" }
}

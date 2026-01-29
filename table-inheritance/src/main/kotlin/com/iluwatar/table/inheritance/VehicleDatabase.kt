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

// ABOUTME: Manages the storage and retrieval of Vehicle objects, including Cars and Trucks.
// ABOUTME: Simulates a database with separate tables for each vehicle type (Table Inheritance).
package com.iluwatar.table.inheritance

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Manages the storage and retrieval of Vehicle objects, including Cars and Trucks.
 */
class VehicleDatabase {

    private val vehicleTable = mutableMapOf<Int, Vehicle>()
    private val carTable = mutableMapOf<Int, Car>()
    private val truckTable = mutableMapOf<Int, Truck>()

    /**
     * Saves a vehicle to the database. If the vehicle is a Car or Truck, it is added to the
     * respective table.
     *
     * @param vehicle the vehicle to save
     */
    fun saveVehicle(vehicle: Vehicle) {
        vehicleTable[vehicle.id] = vehicle
        when (vehicle) {
            is Car -> carTable[vehicle.id] = vehicle
            is Truck -> truckTable[vehicle.id] = vehicle
        }
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id the ID of the vehicle
     * @return the vehicle with the given ID, or null if not found
     */
    fun getVehicle(id: Int): Vehicle? = vehicleTable[id]

    /**
     * Retrieves a car by its ID.
     *
     * @param id the ID of the car
     * @return the car with the given ID, or null if not found
     */
    fun getCar(id: Int): Car? = carTable[id]

    /**
     * Retrieves a truck by its ID.
     *
     * @param id the ID of the truck
     * @return the truck with the given ID, or null if not found
     */
    fun getTruck(id: Int): Truck? = truckTable[id]

    /**
     * Prints all vehicles in the database.
     */
    fun printAllVehicles() {
        vehicleTable.values.forEach { vehicle ->
            logger.info { vehicle.toString() }
        }
    }
}

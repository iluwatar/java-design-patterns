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
package com.iluwatar.service

// ABOUTME: Service layer for Vehicle CRUD operations in the Single Table Inheritance pattern.
// ABOUTME: Provides business logic methods connecting to the VehicleRepository for database operations.

import com.iluwatar.entity.Vehicle
import com.iluwatar.repository.VehicleRepository
import org.springframework.stereotype.Service

/**
 * A service class that provides business logic for the Vehicle class and connects to
 * the database to perform CRUD operations on the root Vehicle class.
 *
 * @see Vehicle
 */
@Service
class VehicleService(private val vehicleRepository: VehicleRepository) {

    /**
     * Saves a vehicle to the database.
     *
     * @param vehicle Vehicle object
     * @return the saved Vehicle
     * @see Vehicle
     */
    fun saveVehicle(vehicle: Vehicle): Vehicle = vehicleRepository.save(vehicle)

    /**
     * Gets a specific vehicle by vehicle id.
     *
     * @param vehicleId Vehicle Id
     * @return the Vehicle or null if not found
     * @see Vehicle
     */
    fun getVehicle(vehicleId: Int): Vehicle? = vehicleRepository.findById(vehicleId).orElse(null)

    /**
     * Gets all vehicles saved in the database.
     *
     * @return list of all Vehicles
     * @see Vehicle
     */
    fun getAllVehicles(): List<Vehicle> = vehicleRepository.findAll()

    /**
     * Updates a vehicle in the database.
     *
     * @param vehicle Vehicle object
     * @return the updated Vehicle
     * @see Vehicle
     */
    fun updateVehicle(vehicle: Vehicle): Vehicle = vehicleRepository.save(vehicle)

    /**
     * Deletes a vehicle from the database.
     *
     * @param vehicle Vehicle object
     * @see Vehicle
     */
    fun deleteVehicle(vehicle: Vehicle) = vehicleRepository.delete(vehicle)
}

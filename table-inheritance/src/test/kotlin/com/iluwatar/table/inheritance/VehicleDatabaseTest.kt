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

// ABOUTME: Unit tests for the VehicleDatabase class.
// ABOUTME: Tests saving, retrieving, and printing vehicles of different types.
package com.iluwatar.table.inheritance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Unit tests for the [VehicleDatabase] class. Tests saving, retrieving, and printing vehicles
 * of different types.
 */
class VehicleDatabaseTest {

    private lateinit var vehicleDatabase: VehicleDatabase

    /**
     * Sets up a new instance of [VehicleDatabase] before each test.
     */
    @BeforeEach
    fun setUp() {
        vehicleDatabase = VehicleDatabase()
    }

    /**
     * Tests saving a [Car] to the database and retrieving it.
     */
    @Test
    fun `should save and retrieve car`() {
        val car = Car(2020, "Toyota", "Corolla", 4, 1)
        vehicleDatabase.saveVehicle(car)

        val retrievedVehicle = vehicleDatabase.getVehicle(car.id)
        assertNotNull(retrievedVehicle)
        assertEquals(car.id, retrievedVehicle?.id)
        assertEquals(car.make, retrievedVehicle?.make)
        assertEquals(car.model, retrievedVehicle?.model)
        assertEquals(car.year, retrievedVehicle?.year)

        val retrievedCar = vehicleDatabase.getCar(car.id)
        assertNotNull(retrievedCar)
        assertEquals(car.numDoors, retrievedCar?.numDoors)
    }

    /**
     * Tests saving a [Truck] to the database and retrieving it.
     */
    @Test
    fun `should save and retrieve truck`() {
        val truck = Truck(2018, "Ford", "F-150", 60.0, 2)
        vehicleDatabase.saveVehicle(truck)

        val retrievedVehicle = vehicleDatabase.getVehicle(truck.id)
        assertNotNull(retrievedVehicle)
        assertEquals(truck.id, retrievedVehicle?.id)
        assertEquals(truck.make, retrievedVehicle?.make)
        assertEquals(truck.model, retrievedVehicle?.model)
        assertEquals(truck.year, retrievedVehicle?.year)

        val retrievedTruck = vehicleDatabase.getTruck(truck.id)
        assertNotNull(retrievedTruck)
        assertEquals(truck.loadCapacity, retrievedTruck?.loadCapacity)
    }

    /**
     * Tests saving multiple vehicles to the database and printing them.
     */
    @Test
    fun `should print all vehicles`() {
        val car = Car(2020, "Toyota", "Corolla", 4, 1)
        val truck = Truck(2018, "Ford", "F-150", 60.0, 2)
        vehicleDatabase.saveVehicle(car)
        vehicleDatabase.saveVehicle(truck)

        vehicleDatabase.printAllVehicles()

        val retrievedCar = vehicleDatabase.getVehicle(car.id)
        val retrievedTruck = vehicleDatabase.getVehicle(truck.id)

        assertNotNull(retrievedCar)
        assertNotNull(retrievedTruck)
    }

    /**
     * Tests the constructor of [Car] with valid values.
     */
    @Test
    fun `car constructor should work with valid values`() {
        val car = Car(2020, "Toyota", "Corolla", 4, 1)
        assertEquals(2020, car.year)
        assertEquals("Toyota", car.make)
        assertEquals("Corolla", car.model)
        assertEquals(4, car.numDoors)
        assertEquals(1, car.id)
    }

    /**
     * Tests the constructor of [Car] with invalid number of doors (negative value).
     */
    @Test
    fun `car constructor should throw exception for invalid num doors`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Car(2020, "Toyota", "Corolla", -4, 1)
        }
        assertEquals("Number of doors must be positive.", exception.message)
    }

    /**
     * Tests the constructor of [Car] with zero doors.
     */
    @Test
    fun `car constructor should throw exception for zero doors`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Car(2020, "Toyota", "Corolla", 0, 1)
        }
        assertEquals("Number of doors must be positive.", exception.message)
    }

    /**
     * Tests the constructor of [Truck] with invalid load capacity (negative value).
     */
    @Test
    fun `truck constructor should throw exception for invalid load capacity`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Truck(2018, "Ford", "F-150", -60.0, 2)
        }
        assertEquals("Load capacity must be positive.", exception.message)
    }

    /**
     * Tests the constructor of [Truck] with zero load capacity.
     */
    @Test
    fun `truck constructor should throw exception for zero load capacity`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Truck(2018, "Ford", "F-150", 0.0, 2)
        }
        assertEquals("Load capacity must be positive.", exception.message)
    }

    /**
     * Tests setting invalid number of doors in [Car] using setter (negative value).
     */
    @Test
    fun `car should throw exception for setting invalid num doors`() {
        val car = Car(2020, "Toyota", "Corolla", 4, 1)
        val exception = assertThrows(IllegalArgumentException::class.java) {
            car.numDoors = -2
        }
        assertEquals("Number of doors must be positive.", exception.message)
    }

    /**
     * Tests setting invalid load capacity in [Truck] using setter (negative value).
     */
    @Test
    fun `truck should throw exception for setting invalid load capacity`() {
        val truck = Truck(2018, "Ford", "F-150", 60.0, 2)
        val exception = assertThrows(IllegalArgumentException::class.java) {
            truck.loadCapacity = -10.0
        }
        assertEquals("Load capacity must be positive.", exception.message)
    }
}

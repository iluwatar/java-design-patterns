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
package com.iluwatar.repository

// ABOUTME: Integration tests for VehicleRepository verifying JPA Single Table Inheritance.
// ABOUTME: Tests that Car, Train, Truck, and Freighter entities persist correctly to a single table.

import com.iluwatar.entity.Car
import com.iluwatar.entity.Freighter
import com.iluwatar.entity.Train
import com.iluwatar.entity.Truck
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

/** Integration tests for VehicleRepository. */
@DataJpaTest
@ContextConfiguration(classes = [TestConfiguration::class])
class VehicleRepositoryIntegrationTest {

    @Autowired
    private lateinit var vehicleRepository: VehicleRepository

    @Test
    fun testSaveAndRetrieveCar() {
        val car = Car("Tesla", "Model S", 4, 825)
        val savedCar = vehicleRepository.save(car)

        assertNotNull(savedCar.vehicleId)
        assertTrue(savedCar.vehicleId > 0)

        val retrievedCar = vehicleRepository.findById(savedCar.vehicleId).orElse(null)
        assertNotNull(retrievedCar)
        assertTrue(retrievedCar is Car)
        assertEquals("Tesla", retrievedCar.manufacturer)
        assertEquals(825, (retrievedCar as Car).engineCapacity)
    }

    @Test
    fun testSaveAndRetrieveTrain() {
        val train = Train("Siemens", "ICE", 500, 12)
        val savedTrain = vehicleRepository.save(train)

        assertNotNull(savedTrain.vehicleId)

        val retrievedTrain = vehicleRepository.findById(savedTrain.vehicleId).orElse(null)
        assertNotNull(retrievedTrain)
        assertTrue(retrievedTrain is Train)
        assertEquals("Siemens", retrievedTrain.manufacturer)
        assertEquals(12, (retrievedTrain as Train).noOfCarriages)
    }

    @Test
    fun testSaveAndRetrieveTruck() {
        val truck = Truck("Ford", "F-150", 3325, 14000)
        val savedTruck = vehicleRepository.save(truck)

        assertNotNull(savedTruck.vehicleId)

        val retrievedTruck = vehicleRepository.findById(savedTruck.vehicleId).orElse(null)
        assertNotNull(retrievedTruck)
        assertTrue(retrievedTruck is Truck)
        assertEquals("Ford", retrievedTruck.manufacturer)
        assertEquals(14000, (retrievedTruck as Truck).towingCapacity)
    }

    @Test
    fun testSaveAndRetrieveFreighter() {
        val freighter = Freighter("Boeing", "747F", 120000, 8000.5)
        val savedFreighter = vehicleRepository.save(freighter)

        assertNotNull(savedFreighter.vehicleId)

        val retrievedFreighter = vehicleRepository.findById(savedFreighter.vehicleId).orElse(null)
        assertNotNull(retrievedFreighter)
        assertTrue(retrievedFreighter is Freighter)
        assertEquals("Boeing", retrievedFreighter.manufacturer)
        assertEquals(8000.5, (retrievedFreighter as Freighter).flightLength)
    }

    @Test
    fun testFindAllVehiclesWithDifferentTypes() {
        vehicleRepository.save(Car("Tesla", "Model S", 4, 825))
        vehicleRepository.save(Truck("Ford", "F-150", 3325, 14000))
        vehicleRepository.save(Train("Siemens", "ICE", 500, 12))
        vehicleRepository.save(Freighter("Boeing", "747F", 120000, 8000.5))

        val allVehicles = vehicleRepository.findAll()

        assertEquals(4, allVehicles.size)
        assertTrue(allVehicles.any { it is Car })
        assertTrue(allVehicles.any { it is Truck })
        assertTrue(allVehicles.any { it is Train })
        assertTrue(allVehicles.any { it is Freighter })
    }

    @Test
    fun testDeleteVehicle() {
        val car = Car("Tesla", "Model S", 4, 825)
        val savedCar = vehicleRepository.save(car)
        val id = savedCar.vehicleId

        vehicleRepository.delete(savedCar)

        val deletedCar = vehicleRepository.findById(id).orElse(null)
        assertEquals(null, deletedCar)
    }

    @Test
    fun testUpdateVehicle() {
        val car = Car("Tesla", "Model S", 4, 825)
        val savedCar = vehicleRepository.save(car)

        savedCar.model = "Model X"
        savedCar.engineCapacity = 900
        vehicleRepository.save(savedCar)

        val updatedCar = vehicleRepository.findById(savedCar.vehicleId).orElse(null)
        assertNotNull(updatedCar)
        assertEquals("Model X", updatedCar?.model)
        assertEquals(900, (updatedCar as Car).engineCapacity)
    }
}

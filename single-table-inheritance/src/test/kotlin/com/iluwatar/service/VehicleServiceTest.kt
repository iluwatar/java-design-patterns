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

// ABOUTME: Unit tests for VehicleService using MockK to mock the repository layer.
// ABOUTME: Tests all CRUD operations: save, get, getAll, update, and delete vehicles.

import com.iluwatar.entity.Car
import com.iluwatar.entity.Truck
import com.iluwatar.entity.Vehicle
import com.iluwatar.repository.VehicleRepository
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional

/** Unit tests for VehicleService. */
class VehicleServiceTest {

    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var vehicleService: VehicleService

    @BeforeEach
    fun setUp() {
        vehicleRepository = mockk()
        vehicleService = VehicleService(vehicleRepository)
    }

    @Test
    fun testSaveVehicle() {
        val car = Car("Tesla", "Model S", 4, 825)
        val savedCar = Car("Tesla", "Model S", 4, 825).apply { vehicleId = 1 }
        val vehicleSlot = slot<Vehicle>()

        every { vehicleRepository.save(capture(vehicleSlot)) } answers { savedCar }

        val result = vehicleService.saveVehicle(car)

        assertEquals(1, result.vehicleId)
        assertEquals("Tesla", result.manufacturer)
        assertEquals(car, vehicleSlot.captured)
        verify { vehicleRepository.save(car) }
    }

    @Test
    fun testSaveVehicleTruck() {
        val truck = Truck("Ford", "F-150", 3325, 14000)
        val savedTruck = Truck("Ford", "F-150", 3325, 14000).apply { vehicleId = 2 }
        val vehicleSlot = slot<Vehicle>()

        every { vehicleRepository.save(capture(vehicleSlot)) } answers { savedTruck }

        val result = vehicleService.saveVehicle(truck)

        assertEquals(2, result.vehicleId)
        assertEquals("Ford", result.manufacturer)
        assertEquals(truck, vehicleSlot.captured)
        verify { vehicleRepository.save(truck) }
    }

    @Test
    fun testGetVehicleFound() {
        val car = Car("Tesla", "Model S", 4, 825).apply { vehicleId = 1 }

        every { vehicleRepository.findById(1) } returns Optional.of(car)

        val result = vehicleService.getVehicle(1)

        assertEquals(car, result)
        verify { vehicleRepository.findById(1) }
    }

    @Test
    fun testGetVehicleNotFound() {
        every { vehicleRepository.findById(999) } returns Optional.empty()

        val result = vehicleService.getVehicle(999)

        assertNull(result)
        verify { vehicleRepository.findById(999) }
    }

    @Test
    fun testGetAllVehicles() {
        val car = Car("Tesla", "Model S", 4, 825).apply { vehicleId = 1 }
        val truck = Truck("Ford", "F-150", 3325, 14000).apply { vehicleId = 2 }
        val vehicles: List<Vehicle> = listOf(car, truck)

        every { vehicleRepository.findAll() } returns vehicles

        val result = vehicleService.getAllVehicles()

        assertEquals(2, result.size)
        assertEquals(car, result[0])
        assertEquals(truck, result[1])
        verify { vehicleRepository.findAll() }
    }

    @Test
    fun testGetAllVehiclesEmpty() {
        every { vehicleRepository.findAll() } returns emptyList()

        val result = vehicleService.getAllVehicles()

        assertEquals(0, result.size)
        verify { vehicleRepository.findAll() }
    }

    @Test
    fun testUpdateVehicle() {
        val car = Car("Tesla", "Model S", 4, 825).apply { vehicleId = 1 }
        val updatedCar = Car("Tesla", "Model X", 5, 900).apply { vehicleId = 1 }
        val vehicleSlot = slot<Vehicle>()

        every { vehicleRepository.save(capture(vehicleSlot)) } answers { updatedCar }

        val result = vehicleService.updateVehicle(car)

        assertEquals("Model X", result.model)
        assertEquals(car, vehicleSlot.captured)
        verify { vehicleRepository.save(car) }
    }

    @Test
    fun testDeleteVehicle() {
        val car = Car("Tesla", "Model S", 4, 825).apply { vehicleId = 1 }

        justRun { vehicleRepository.delete(car) }

        vehicleService.deleteVehicle(car)

        verify { vehicleRepository.delete(car) }
    }
}

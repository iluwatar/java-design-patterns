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
package com.iluwatar.entity

// ABOUTME: Unit tests for all Vehicle entity classes in the Single Table Inheritance hierarchy.
// ABOUTME: Verifies entity creation, property access, equals/hashCode, and toString behavior.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Tests for Vehicle entity classes. */
class VehicleEntityTest {

    @Test
    fun testCarCreation() {
        val car = Car("Tesla", "Model S", 4, 825)
        assertEquals("Tesla", car.manufacturer)
        assertEquals("Model S", car.model)
        assertEquals(4, car.noOfPassengers)
        assertEquals(825, car.engineCapacity)
    }

    @Test
    fun testCarDefaultConstructor() {
        val car = Car()
        assertEquals("", car.manufacturer)
        assertEquals("", car.model)
        assertEquals(0, car.noOfPassengers)
        assertEquals(0, car.engineCapacity)
    }

    @Test
    fun testCarToString() {
        val car = Car("Tesla", "Model S", 4, 825)
        val str = car.toString()
        assertTrue(str.contains("Car"))
        assertTrue(str.contains("Tesla"))
        assertTrue(str.contains("Model S"))
    }

    @Test
    fun testCarEquality() {
        val car1 = Car("Tesla", "Model S", 4, 825)
        val car2 = Car("Tesla", "Model S", 4, 825)
        val car3 = Car("BMW", "i8", 2, 600)
        assertEquals(car1, car2)
        assertEquals(car1.hashCode(), car2.hashCode())
        assertNotEquals(car1, car3)
    }

    @Test
    fun testTrainCreation() {
        val train = Train("Siemens", "ICE", 500, 12)
        assertEquals("Siemens", train.manufacturer)
        assertEquals("ICE", train.model)
        assertEquals(500, train.noOfPassengers)
        assertEquals(12, train.noOfCarriages)
    }

    @Test
    fun testTrainDefaultConstructor() {
        val train = Train()
        assertEquals("", train.manufacturer)
        assertEquals("", train.model)
        assertEquals(0, train.noOfPassengers)
        assertEquals(0, train.noOfCarriages)
    }

    @Test
    fun testTrainToString() {
        val train = Train("Siemens", "ICE", 500, 12)
        val str = train.toString()
        assertTrue(str.contains("Train"))
        assertTrue(str.contains("Siemens"))
    }

    @Test
    fun testTrainEquality() {
        val train1 = Train("Siemens", "ICE", 500, 12)
        val train2 = Train("Siemens", "ICE", 500, 12)
        val train3 = Train("Alstom", "TGV", 600, 10)
        assertEquals(train1, train2)
        assertEquals(train1.hashCode(), train2.hashCode())
        assertNotEquals(train1, train3)
    }

    @Test
    fun testTruckCreation() {
        val truck = Truck("Ford", "F-150", 3325, 14000)
        assertEquals("Ford", truck.manufacturer)
        assertEquals("F-150", truck.model)
        assertEquals(3325, truck.loadCapacity)
        assertEquals(14000, truck.towingCapacity)
    }

    @Test
    fun testTruckDefaultConstructor() {
        val truck = Truck()
        assertEquals("", truck.manufacturer)
        assertEquals("", truck.model)
        assertEquals(0, truck.loadCapacity)
        assertEquals(0, truck.towingCapacity)
    }

    @Test
    fun testTruckToString() {
        val truck = Truck("Ford", "F-150", 3325, 14000)
        val str = truck.toString()
        assertTrue(str.contains("Truck"))
        assertTrue(str.contains("Ford"))
        assertTrue(str.contains("towingCapacity"))
    }

    @Test
    fun testTruckEquality() {
        val truck1 = Truck("Ford", "F-150", 3325, 14000)
        val truck2 = Truck("Ford", "F-150", 3325, 14000)
        val truck3 = Truck("Chevrolet", "Silverado", 3000, 12000)
        assertEquals(truck1, truck2)
        assertEquals(truck1.hashCode(), truck2.hashCode())
        assertNotEquals(truck1, truck3)
    }

    @Test
    fun testFreighterCreation() {
        val freighter = Freighter("Boeing", "747F", 120000, 8000.5)
        assertEquals("Boeing", freighter.manufacturer)
        assertEquals("747F", freighter.model)
        assertEquals(120000, freighter.loadCapacity)
        assertEquals(8000.5, freighter.flightLength)
    }

    @Test
    fun testFreighterDefaultConstructor() {
        val freighter = Freighter()
        assertEquals("", freighter.manufacturer)
        assertEquals("", freighter.model)
        assertEquals(0, freighter.loadCapacity)
        assertEquals(0.0, freighter.flightLength)
    }

    @Test
    fun testFreighterToString() {
        val freighter = Freighter("Boeing", "747F", 120000, 8000.5)
        val str = freighter.toString()
        assertTrue(str.contains("Freighter"))
        assertTrue(str.contains("Boeing"))
        assertTrue(str.contains("flightLength"))
    }

    @Test
    fun testFreighterEquality() {
        val freighter1 = Freighter("Boeing", "747F", 120000, 8000.5)
        val freighter2 = Freighter("Boeing", "747F", 120000, 8000.5)
        val freighter3 = Freighter("Airbus", "A330F", 100000, 7000.0)
        assertEquals(freighter1, freighter2)
        assertEquals(freighter1.hashCode(), freighter2.hashCode())
        assertNotEquals(freighter1, freighter3)
    }

    @Test
    fun testVehicleIdSetting() {
        val car = Car("Tesla", "Model S", 4, 825)
        car.vehicleId = 100
        assertEquals(100, car.vehicleId)
    }

    @Test
    fun testDifferentVehicleTypesNotEqual() {
        val car = Car("Tesla", "Model S", 4, 825)
        val truck = Truck("Ford", "F-150", 3325, 14000)
        assertNotEquals(car, truck)
    }
}

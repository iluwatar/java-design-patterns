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

// ABOUTME: Represents a car with a specific number of doors, extending Vehicle.
// ABOUTME: Demonstrates Table Inheritance pattern with car-specific attributes stored separately.
package com.iluwatar.table.inheritance

/**
 * Represents a car with a specific number of doors.
 *
 * @property year the manufacturing year
 * @property make the make of the car
 * @property model the model of the car
 * @property numDoors the number of doors
 * @property id the unique identifier for the car
 */
class Car(
    year: Int,
    make: String,
    model: String,
    numDoors: Int,
    id: Int
) : Vehicle(year, make, model, id) {

    var numDoors: Int = numDoors
        set(value) {
            require(value > 0) { "Number of doors must be positive." }
            field = value
        }

    init {
        require(numDoors > 0) { "Number of doors must be positive." }
    }

    override fun toString(): String =
        "Car{id=$id, make='$make', model='$model', year=$year, numberOfDoors=$numDoors}"
}

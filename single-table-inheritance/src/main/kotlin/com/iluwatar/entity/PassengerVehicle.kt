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

// ABOUTME: Abstract intermediate class for passenger-carrying vehicles in the Single Table Inheritance hierarchy.
// ABOUTME: Adds passenger capacity property; extended by Car and Train entities.

/**
 * An abstract class that extends the Vehicle class and provides properties for the Passenger type
 * of Vehicles.
 *
 * @see Vehicle
 */
abstract class PassengerVehicle(
    manufacturer: String = "",
    model: String = "",
    var noOfPassengers: Int = 0
) : Vehicle(manufacturer, model) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PassengerVehicle) return false
        if (!super.equals(other)) return false
        return noOfPassengers == other.noOfPassengers
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + noOfPassengers
        return result
    }
}

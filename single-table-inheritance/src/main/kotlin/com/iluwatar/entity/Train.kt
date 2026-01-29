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

// ABOUTME: Concrete JPA entity representing a train in the Single Table Inheritance pattern.
// ABOUTME: Extends PassengerVehicle with carriage count; stored in the Vehicle table with discriminator "TRAIN".

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

/**
 * A class that extends the PassengerVehicle class and provides the concrete inheritance
 * implementation of the Train.
 *
 * @see PassengerVehicle
 * @see Vehicle
 */
@Entity
@DiscriminatorValue(value = "TRAIN")
class Train(
    manufacturer: String = "",
    model: String = "",
    noOfPassengers: Int = 0,
    var noOfCarriages: Int = 0
) : PassengerVehicle(manufacturer, model, noOfPassengers) {

    override fun toString(): String = "Train{${super.toString()}}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Train) return false
        if (!super.equals(other)) return false
        return noOfCarriages == other.noOfCarriages
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + noOfCarriages
        return result
    }
}

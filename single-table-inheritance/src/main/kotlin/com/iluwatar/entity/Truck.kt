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

// ABOUTME: Concrete JPA entity representing a truck in the Single Table Inheritance pattern.
// ABOUTME: Extends TransportVehicle with towing capacity; stored in the Vehicle table with discriminator "TRUCK".

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

/**
 * A class that extends the TransportVehicle class and provides the concrete inheritance
 * implementation of the Truck.
 *
 * @see TransportVehicle
 * @see Vehicle
 */
@Entity
@DiscriminatorValue(value = "TRUCK")
class Truck(
    manufacturer: String = "",
    model: String = "",
    loadCapacity: Int = 0,
    var towingCapacity: Int = 0
) : TransportVehicle(manufacturer, model, loadCapacity) {

    override fun toString(): String = "Truck{ ${super.toString()}, towingCapacity=$towingCapacity}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Truck) return false
        if (!super.equals(other)) return false
        return towingCapacity == other.towingCapacity
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + towingCapacity
        return result
    }
}

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

// ABOUTME: Abstract root class for the Vehicle inheritance hierarchy using JPA Single Table Inheritance.
// ABOUTME: Maps all subclasses (Car, Train, Truck, Freighter) to a single database table with a discriminator column.

import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType

/**
 * An abstract class that is the root of the Vehicle Inheritance hierarchy and provides
 * basic properties for all vehicles.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VEHICLE_TYPE")
abstract class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var vehicleId: Int = 0,
    var manufacturer: String = "",
    var model: String = ""
) {
    internal constructor(manufacturer: String, model: String) : this(0, manufacturer, model)

    override fun toString(): String =
        "Vehicle{vehicleId=$vehicleId, manufacturer='$manufacturer', model='$model}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vehicle) return false
        return vehicleId == other.vehicleId &&
            manufacturer == other.manufacturer &&
            model == other.model
    }

    override fun hashCode(): Int {
        var result = vehicleId
        result = 31 * result + manufacturer.hashCode()
        result = 31 * result + model.hashCode()
        return result
    }
}

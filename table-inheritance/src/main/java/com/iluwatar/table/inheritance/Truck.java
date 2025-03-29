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
package com.iluwatar.table.inheritance;

import lombok.Getter;

/** Represents a truck, a type of vehicle with a specific load capacity. */
@Getter
public class Truck extends Vehicle {
  private double loadCapacity;

  /**
   * Constructs a Truck object with the given parameters.
   *
   * @param year the year of manufacture
   * @param make the make of the truck
   * @param model the model of the truck
   * @param loadCapacity the load capacity of the truck
   * @param id the unique ID of the truck
   */
  public Truck(int year, String make, String model, double loadCapacity, int id) {
    super(year, make, model, id);
    if (loadCapacity <= 0) {
      throw new IllegalArgumentException("Load capacity must be positive.");
    }
    this.loadCapacity = loadCapacity;
  }

  /**
   * Sets the load capacity of the truck.
   *
   * @param capacity the new load capacity
   */
  public void setLoadCapacity(double capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Load capacity must be positive.");
    }
    this.loadCapacity = capacity;
  }

  /**
   * Returns a string representation of the truck.
   *
   * @return a string with the truck's details
   */
  @Override
  public String toString() {
    return "Truck{"
        + "id="
        + getId()
        + ", make='"
        + getMake()
        + '\''
        + ", model='"
        + getModel()
        + '\''
        + ", year="
        + getYear()
        + ", payloadCapacity="
        + getLoadCapacity()
        + '}';
  }
}

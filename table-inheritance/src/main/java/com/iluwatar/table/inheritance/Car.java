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

/** Represents a car with a specific number of doors. */
@Getter
public class Car extends Vehicle {
  private int numDoors;

  /**
   * Constructs a Car object.
   *
   * @param year the manufacturing year
   * @param make the make of the car
   * @param model the model of the car
   * @param numDoors the number of doors
   * @param id the unique identifier for the car
   */
  public Car(int year, String make, String model, int numDoors, int id) {
    super(year, make, model, id);
    if (numDoors <= 0) {
      throw new IllegalArgumentException("Number of doors must be positive.");
    }
    this.numDoors = numDoors;
  }

  /**
   * Sets the number of doors for the car.
   *
   * @param doors the number of doors
   */
  public void setNumDoors(int doors) {
    if (doors <= 0) {
      throw new IllegalArgumentException("Number of doors must be positive.");
    }
    this.numDoors = doors;
  }

  @Override
  public String toString() {
    return "Car{"
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
        + ", numberOfDoors="
        + getNumDoors()
        + '}';
  }
}

/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.collectionpipeline;

import java.util.List;

/**
 * A factory class to create a collection of {@link Car} instances.
 */
public class CarFactory {
  private CarFactory() {
  }

  /**
   * Factory method to create a {@link List} of {@link Car} instances.
   *
   * @return {@link List} of {@link Car}
   */
  public static List<Car> createCars() {
    return List.of(new Car("Jeep", "Wrangler", 2011, Category.JEEP),
        new Car("Jeep", "Comanche", 1990, Category.JEEP),
        new Car("Dodge", "Avenger", 2010, Category.SEDAN),
        new Car("Buick", "Cascada", 2016, Category.CONVERTIBLE),
        new Car("Ford", "Focus", 2012, Category.SEDAN),
        new Car("Chevrolet", "Geo Metro", 1992, Category.CONVERTIBLE));
  }
}
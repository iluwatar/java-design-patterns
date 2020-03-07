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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests that Collection Pipeline methods work as expected.
 */
public class AppTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

  private List<Car> cars = CarFactory.createCars();

  @Test
  public void testGetModelsAfter2000UsingFor() {
    var models = ImperativeProgramming.getModelsAfter2000(cars);
    assertEquals(List.of("Avenger", "Wrangler", "Focus", "Cascada"), models);
  }

  @Test
  public void testGetModelsAfter2000UsingPipeline() {
    var models = FunctionalProgramming.getModelsAfter2000(cars);
    assertEquals(List.of("Avenger", "Wrangler", "Focus", "Cascada"), models);
  }

  @Test
  public void testGetGroupingOfCarsByCategory() {
    var modelsExpected = Map.of(
        Category.CONVERTIBLE, List.of(
            new Car("Buick", "Cascada", 2016, Category.CONVERTIBLE),
            new Car("Chevrolet", "Geo Metro", 1992, Category.CONVERTIBLE)
        ),
        Category.SEDAN, List.of(
            new Car("Dodge", "Avenger", 2010, Category.SEDAN),
            new Car("Ford", "Focus", 2012, Category.SEDAN)
        ),
        Category.JEEP, List.of(
            new Car("Jeep", "Wrangler", 2011, Category.JEEP),
            new Car("Jeep", "Comanche", 1990, Category.JEEP))
    );
    var modelsFunctional = FunctionalProgramming.getGroupingOfCarsByCategory(cars);
    var modelsImperative = ImperativeProgramming.getGroupingOfCarsByCategory(cars);
    LOGGER.info("Category " + modelsFunctional);
    assertEquals(modelsExpected, modelsFunctional);
    assertEquals(modelsExpected, modelsImperative);
  }

  @Test
  public void testGetSedanCarsOwnedSortedByDate() {
    var john = new Person(cars);
    var modelsExpected = List.of(
        new Car("Dodge", "Avenger", 2010, Category.SEDAN),
        new Car("Ford", "Focus", 2012, Category.SEDAN)
    );
    var modelsFunctional = FunctionalProgramming.getSedanCarsOwnedSortedByDate(List.of(john));
    var modelsImperative = ImperativeProgramming.getSedanCarsOwnedSortedByDate(List.of(john));
    assertEquals(modelsExpected, modelsFunctional);
    assertEquals(modelsExpected, modelsImperative);
  }
}

/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that Collection Pipeline methods work as expected.
 */
public class AppTest {
  
  private List<Car> cars = CarFactory.createCars();
  
  @Test
  public void testGetModelsAfter2000UsingFor() {
    List<String> models = ImperativeProgramming.getModelsAfter2000(cars);
    assertEquals(models, Arrays.asList("Avenger", "Wrangler", "Focus", "Cascada"));
  }
  
  @Test
  public void testGetModelsAfter2000UsingPipeline() {
    List<String> models = FunctionalProgramming.getModelsAfter2000(cars);
    assertEquals(models, Arrays.asList("Avenger", "Wrangler", "Focus", "Cascada"));
  }
  
  @Test
  public void testGetGroupingOfCarsByCategory() {
    Map<String, List<Car>> modelsFunctional = FunctionalProgramming.getGroupingOfCarsByCategory(cars);
    Map<String, List<Car>> modelsImperative = ImperativeProgramming.getGroupingOfCarsByCategory(cars);
    assertEquals(modelsFunctional, modelsImperative);
  }
  
  @Test
  public void testGetSedanCarsOwnedSortedByDate() {
    Person john = new Person(cars);
    List<Car> modelsFunctional = FunctionalProgramming.getSedanCarsOwnedSortedByDate(Arrays.asList(john));
    List<Car> modelsImperative = ImperativeProgramming.getSedanCarsOwnedSortedByDate(Arrays.asList(john));
    assertEquals(modelsFunctional, modelsImperative);
  }
}

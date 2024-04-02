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

package com.iluwatar.collectionpipeline;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Iterating and sorting with a collection pipeline
 *
 * <p>In functional programming, it's common to sequence complex operations through
 * a series of smaller modular functions or operations. The series is called a composition of
 * functions, or a function composition. When a collection of data flows through a function
 * composition, it becomes a collection pipeline. Function Composition and Collection Pipeline are
 * two design patterns frequently used in functional-style programming.
 *
 * <p>Instead of passing a lambda expression to the map method, we passed the
 * method reference Car::getModel. Likewise, instead of passing the lambda expression car ->
 * car.getYear() to the comparing method, we passed the method reference Car::getYear. Method
 * references are short, concise, and expressive. It is best to use them wherever possible.
 */
public class FunctionalProgramming {
  private FunctionalProgramming() {
  }

  /**
   * Method to get models using for collection pipeline.
   *
   * @param cars {@link List} of {@link Car} to be used for filtering
   * @return {@link List} of {@link String} representing models built after year 2000
   */
  public static List<String> getModelsAfter2000(List<Car> cars) {
    return cars.stream().filter(car -> car.year() > 2000).sorted(Comparator.comparing(Car::year))
        .map(Car::model).toList();
  }

  /**
   * Method to group cars by category using groupingBy.
   *
   * @param cars {@link List} of {@link Car} to be used for grouping
   * @return {@link Map} with category as key and cars belonging to that category as value
   */
  public static Map<Category, List<Car>> getGroupingOfCarsByCategory(List<Car> cars) {
    return cars.stream().collect(Collectors.groupingBy(Car::category));
  }

  /**
   * Method to get all Sedan cars belonging to a group of persons sorted by year of manufacture.
   *
   * @param persons {@link List} of {@link Person} to be used
   * @return {@link List} of {@link Car} to belonging to the group
   */
  public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons) {
    return persons.stream().map(Person::cars).flatMap(List::stream)
        .filter(car -> Category.SEDAN.equals(car.category()))
        .sorted(Comparator.comparing(Car::year)).toList();
  }
}

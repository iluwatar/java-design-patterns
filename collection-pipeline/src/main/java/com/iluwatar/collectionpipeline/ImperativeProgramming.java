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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Imperative-style programming to iterate over the list and get the names of cars made later than
 * the year 2000. We then sort the models in ascending order by year.
 *
 * <p>As you can see, there's a lot of looping in this code. First, the
 * getModelsAfter2000UsingFor method takes a list of cars as its parameter. It extracts or filters
 * out cars made after the year 2000, putting them into a new list named carsSortedByYear. Next, it
 * sorts that list in ascending order by year-of-make. Finally, it loops through the list
 * carsSortedByYear to get the model names and returns them in a list.
 *
 * <p>This short example demonstrates what I call the effect of statements. While
 * functions and methods in general can be used as expressions, the {@link Collections} sort method
 * doesn't return a result. Because it is used as a statement, it mutates the list given as
 * argument. Both of the for loops also mutate lists as they iterate. Being statements, that's just
 * how these elements work. As a result, the code contains unnecessary garbage variables
 */
public class ImperativeProgramming {
  private ImperativeProgramming() {
  }

  /**
   * Method to return the car models built after year 2000 using for loops.
   *
   * @param cars {@link List} of {@link Car} to iterate over
   * @return {@link List} of {@link String} of car models built after year 2000
   */
  public static List<String> getModelsAfter2000(List<Car> cars) {
    return cars.stream()
        .filter(car -> car.getYear() > 2000)
        .sorted(Comparator.comparingInt(Car::getYear))
        .map(Car::getModel)
        .collect(Collectors.toList());
  }

  /**
   * Method to group cars by category using for loops.
   *
   * @param cars {@link List} of {@link Car} to be used for grouping
   * @return {@link Map} with category as key and cars belonging to that category as value
   */
  public static Map<Category, List<Car>> getGroupingOfCarsByCategory(List<Car> cars) {
    return cars.stream().collect(Collectors.groupingBy(Car::getCategory));
  }

  /**
   * Method to get all Sedan cars belonging to a group of persons sorted by year of manufacture
   * using for loops.
   *
   * @param persons {@link List} of {@link Person} to be used
   * @return {@link List} of {@link Car} to belonging to the group
   */
  public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons) {
    return persons.stream()
        .map(Person::getCars)
        .flatMap(Collection::stream)
        .filter(car -> car.getCategory() == Category.SEDAN)
        .sorted(Comparator.comparingInt(Car::getYear))
        .collect(Collectors.toList());
  }
}

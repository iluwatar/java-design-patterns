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
package com.iluwatar.collectionpipeline

// ABOUTME: Demonstrates imperative-style programming with explicit loops and mutable collections.
// ABOUTME: Contrasts with FunctionalProgramming to show the collection pipeline pattern benefits.

/**
 * Imperative-style programming to iterate over the list and get the names of cars made later than
 * the year 2000. We then sort the models in ascending order by year.
 *
 * As you can see, there's a lot of looping in this code. First, the getModelsAfter2000
 * method takes a list of cars as its parameter. It extracts or filters out cars made after the year
 * 2000, putting them into a new list named carsSortedByYear. Next, it sorts that list in ascending
 * order by year-of-make. Finally, it loops through the list carsSortedByYear to get the model names
 * and returns them in a list.
 *
 * This short example demonstrates what is called the effect of statements. While functions and
 * methods in general can be used as expressions, the sort method doesn't return a result. Because
 * it is used as a statement, it mutates the list given as argument. Both of the for loops also
 * mutate lists as they iterate. Being statements, that's just how these elements work. As a result,
 * the code contains unnecessary garbage variables.
 */
object ImperativeProgramming {
    /**
     * Returns the car models built after year 2000 using for loops.
     *
     * @param cars [List] of [Car] to iterate over
     * @return [List] of [String] of car models built after year 2000
     */
    fun getModelsAfter2000(cars: List<Car>): List<String> {
        val carsSortedByYear = mutableListOf<Car>()

        for (car in cars) {
            if (car.year > 2000) {
                carsSortedByYear.add(car)
            }
        }

        carsSortedByYear.sortWith(Comparator { car1, car2 -> car1.year - car2.year })

        val models = mutableListOf<String>()
        for (car in carsSortedByYear) {
            models.add(car.model)
        }

        return models
    }

    /**
     * Groups cars by category using for loops.
     *
     * @param cars [List] of [Car] to be used for grouping
     * @return [Map] with category as key and cars belonging to that category as value
     */
    fun getGroupingOfCarsByCategory(cars: List<Car>): Map<Category, List<Car>> {
        val groupingByCategory = mutableMapOf<Category, MutableList<Car>>()
        for (car in cars) {
            groupingByCategory.getOrPut(car.category) { mutableListOf() }.add(car)
        }
        return groupingByCategory
    }

    /**
     * Returns all Sedan cars belonging to a group of persons sorted by year of manufacture
     * using for loops.
     *
     * @param persons [List] of [Person] to be used
     * @return [List] of [Car] belonging to the group
     */
    fun getSedanCarsOwnedSortedByDate(persons: List<Person>): List<Car> {
        val cars = mutableListOf<Car>()
        for (person in persons) {
            cars.addAll(person.cars)
        }

        val sedanCars = mutableListOf<Car>()
        for (car in cars) {
            if (car.category == Category.SEDAN) {
                sedanCars.add(car)
            }
        }

        sedanCars.sortWith(Comparator { o1, o2 -> o1.year - o2.year })

        return sedanCars
    }
}
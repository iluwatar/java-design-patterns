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

// ABOUTME: Main application entry point demonstrating the Repository pattern with Spring Data JPA.
// ABOUTME: Shows CRUD operations and specification-based queries using XML-based Spring configuration.
package com.iluwatar.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.support.ClassPathXmlApplicationContext

private val logger = KotlinLogging.logger {}

/**
 * Repository pattern mediates between the domain and data mapping layers using a collection-like
 * interface for accessing domain objects. A system with complex domain model often benefits from a
 * layer that isolates domain objects from the details of the database access code and in such
 * systems it can be worthwhile to build another layer of abstraction over the mapping layer where
 * query construction code is concentrated. This becomes more important when there are a large
 * number of domain classes or heavy querying. In these cases particularly, adding this layer helps
 * minimize duplicate query logic.
 *
 * In this example we utilize Spring Data to automatically generate a repository for us from the
 * [Person] domain object. Using the [PersonRepository] we perform CRUD operations on
 * the entity, moreover, the query by [org.springframework.data.jpa.domain.Specification] are
 * also performed. Underneath we have configured in-memory H2 database for which schema is created
 * and dropped on each run.
 */
fun main() {
    val context = ClassPathXmlApplicationContext("applicationContext.xml")
    val repository = context.getBean(PersonRepository::class.java)

    val peter = Person("Peter", "Sagan", 17)
    val nasta = Person("Nasta", "Kuzminova", 25)
    val john = Person("John", "lawrence", 35)
    val terry = Person("Terry", "Law", 36)

    // Add new Person records
    repository.save(peter)
    repository.save(nasta)
    repository.save(john)
    repository.save(terry)

    // Count Person records
    logger.info { "Count Person records: ${repository.count()}" }

    // Print all records
    val persons = repository.findAll()
    persons.forEach { logger.info { it.toString() } }

    // Update Person
    nasta.name = "Barbora"
    nasta.surname = "Spotakova"
    repository.save(nasta)

    repository.findById(2L).ifPresent { p -> logger.info { "Find by id 2: $p" } }

    // Remove record from Person
    repository.deleteById(2L)

    // count records
    logger.info { "Count Person records: ${repository.count()}" }

    // find by name
    repository
        .findOne(PersonSpecifications.NameEqualSpec("John"))
        .ifPresent { p -> logger.info { "Find by John is $p" } }

    // find by age
    val personsByAge = repository.findAll(PersonSpecifications.AgeBetweenSpec(20, 40))

    logger.info { "Find Person with age between 20,40: " }
    personsByAge.forEach { logger.info { it.toString() } }

    repository.deleteAll()

    context.close()
}

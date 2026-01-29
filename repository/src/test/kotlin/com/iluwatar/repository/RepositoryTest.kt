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

// ABOUTME: Integration tests for PersonRepository using XML-based Spring configuration.
// ABOUTME: Tests CRUD operations and JPA Specification queries with applicationContext.xml.
package com.iluwatar.repository

import jakarta.annotation.Resource
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * Test case to test the functions of [PersonRepository], beside the CRUD functions, the query
 * by [org.springframework.data.jpa.domain.Specification] are also test.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = ["locations=classpath:applicationContext.xml"])
class RepositoryTest {

    @Resource
    private lateinit var repository: PersonRepository

    private val peter = Person("Peter", "Sagan", 17)
    private val nasta = Person("Nasta", "Kuzminova", 25)
    private val john = Person("John", "lawrence", 35)
    private val terry = Person("Terry", "Law", 36)

    private val persons = listOf(peter, nasta, john, terry)

    /**
     * Prepare data for test
     */
    @BeforeEach
    fun setup() {
        repository.saveAll(persons)
    }

    @Test
    fun testFindAll() {
        val actuals = repository.findAll()
        assertTrue(actuals.containsAll(persons) && persons.containsAll(actuals))
    }

    @Test
    fun testSave() {
        var terry = repository.findByName("Terry")
        terry!!.surname = "Lee"
        terry.age = 47
        repository.save(terry)

        terry = repository.findByName("Terry")
        assertEquals("Lee", terry!!.surname)
        assertEquals(47, terry.age)
    }

    @Test
    fun testDelete() {
        val terry = repository.findByName("Terry")
        repository.delete(terry!!)

        assertEquals(3, repository.count())
        assertNull(repository.findByName("Terry"))
    }

    @Test
    fun testCount() {
        assertEquals(4, repository.count())
    }

    @Test
    fun testFindAllByAgeBetweenSpec() {
        val persons = repository.findAll(PersonSpecifications.AgeBetweenSpec(20, 40))

        assertEquals(3, persons.size)
        assertTrue(persons.all { it.age > 20 && it.age < 40 })
    }

    @Test
    fun testFindOneByNameEqualSpec() {
        val actual = repository.findOne(PersonSpecifications.NameEqualSpec("Terry"))
        assertTrue(actual.isPresent)
        assertEquals(terry, actual.get())
    }

    @AfterEach
    fun cleanup() {
        repository.deleteAll()
    }
}

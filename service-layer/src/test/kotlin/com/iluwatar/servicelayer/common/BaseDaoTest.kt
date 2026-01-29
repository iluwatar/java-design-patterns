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

// ABOUTME: Abstract base test class providing common DAO test operations.
// ABOUTME: Tests CRUD operations inherited by all entity-specific DAO tests.
package com.iluwatar.servicelayer.common

import com.iluwatar.servicelayer.hibernate.HibernateUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

/**
 * Test for Base Data Access Objects
 *
 * @param E Type of Base Entity
 * @param D Type of Dao Base Implementation
 */
abstract class BaseDaoTest<E : BaseEntity, D : DaoBaseImpl<E>>(
    /** Factory, used to create new entity instances with the given name */
    private val factory: (String) -> E,
    /** The tested data access object */
    private val dao: D
) {

    companion object {
        /** The number of entities stored before each test */
        private const val INITIAL_COUNT = 5

        /** The unique id generator, shared between all entities */
        private val ID_GENERATOR = AtomicInteger()
    }

    @BeforeEach
    fun setUp() {
        repeat(INITIAL_COUNT) {
            val className = dao.persistentClass.simpleName
            val entityName = "$className${ID_GENERATOR.incrementAndGet()}"
            dao.persist(factory(entityName))
        }
    }

    @AfterEach
    fun tearDown() {
        HibernateUtil.dropSession()
    }

    internal fun getDao(): D = dao

    @Test
    fun testFind() {
        val all = dao.findAll()
        for (entity in all) {
            val byId = dao.find(entity.id!!)
            assertNotNull(byId)
            assertEquals(byId?.id, byId?.id)
        }
    }

    @Test
    fun testDelete() {
        val originalEntities = dao.findAll()
        dao.delete(originalEntities[1])
        dao.delete(originalEntities[2])

        val entitiesLeft = dao.findAll()
        assertNotNull(entitiesLeft)
        assertEquals(INITIAL_COUNT - 2, entitiesLeft.size)
    }

    @Test
    fun testFindAll() {
        val all = dao.findAll()
        assertNotNull(all)
        assertEquals(INITIAL_COUNT, all.size)
    }

    @Test
    fun testSetId() {
        val entity = factory("name")
        assertNull(entity.id)

        val expectedId = 1L
        entity.id = expectedId
        assertEquals(expectedId, entity.id)
    }

    @Test
    fun testSetName() {
        val entity = factory("name")
        assertEquals("name", entity.name)
        assertEquals("name", entity.toString())

        val expectedName = "new name"
        entity.name = expectedName
        assertEquals(expectedName, entity.name)
        assertEquals(expectedName, entity.toString())
    }
}

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

// ABOUTME: Abstract base implementation of DAO providing common Hibernate operations.
// ABOUTME: Handles transaction management and session lifecycle for all entity types.
package com.iluwatar.servicelayer.common

import com.iluwatar.servicelayer.hibernate.HibernateUtil
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import java.lang.reflect.ParameterizedType

/**
 * Base class for Dao implementations.
 *
 * @param E Type of Entity
 */
abstract class DaoBaseImpl<E : BaseEntity> : Dao<E> {

    @Suppress("UNCHECKED_CAST")
    internal val persistentClass: Class<E> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<E>

    /**
     * Making this getSessionFactory() instead of getSession() so that it is the responsibility
     * of the caller to open as well as close the session (prevents potential resource leak).
     */
    internal fun getSessionFactory(): SessionFactory = HibernateUtil.getSessionFactory()

    override fun find(id: Long): E? {
        var tx: Transaction? = null
        val result: E?
        getSessionFactory().openSession().use { session ->
            try {
                tx = session.beginTransaction()
                val criteriaBuilder = session.criteriaBuilder
                val builderQuery = criteriaBuilder.createQuery(persistentClass)
                val root = builderQuery.from(persistentClass)
                builderQuery.select(root).where(criteriaBuilder.equal(root.get<Long>("id"), id))
                val query = session.createQuery(builderQuery)
                result = query.uniqueResult()
                tx?.commit()
            } catch (e: Exception) {
                tx?.rollback()
                throw e
            }
        }
        return result
    }

    override fun persist(entity: E) {
        var tx: Transaction? = null
        getSessionFactory().openSession().use { session ->
            try {
                tx = session.beginTransaction()
                session.persist(entity)
                tx?.commit()
            } catch (e: Exception) {
                tx?.rollback()
                throw e
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun merge(entity: E): E {
        var tx: Transaction? = null
        val result: E
        getSessionFactory().openSession().use { session ->
            try {
                tx = session.beginTransaction()
                result = session.merge(entity) as E
                tx?.commit()
            } catch (e: Exception) {
                tx?.rollback()
                throw e
            }
        }
        return result
    }

    override fun delete(entity: E) {
        var tx: Transaction? = null
        getSessionFactory().openSession().use { session ->
            try {
                tx = session.beginTransaction()
                @Suppress("DEPRECATION")
                session.delete(entity)
                tx?.commit()
            } catch (e: Exception) {
                tx?.rollback()
                throw e
            }
        }
    }

    override fun findAll(): List<E> {
        var tx: Transaction? = null
        val result: List<E>
        getSessionFactory().openSession().use { session ->
            try {
                tx = session.beginTransaction()
                val criteriaBuilder = session.criteriaBuilder
                val builderQuery = criteriaBuilder.createQuery(persistentClass)
                val root = builderQuery.from(persistentClass)
                builderQuery.select(root)
                val query = session.createQuery(builderQuery)
                result = query.resultList
                tx?.commit()
            } catch (e: Exception) {
                tx?.rollback()
                throw e
            }
        }
        return result
    }
}

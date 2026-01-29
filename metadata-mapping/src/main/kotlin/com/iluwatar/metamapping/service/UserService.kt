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

// ABOUTME: Service layer for User entity providing CRUD operations via Hibernate.
// ABOUTME: Encapsulates database interactions for listing, creating, updating, deleting, and getting users.
package com.iluwatar.metamapping.service

import com.iluwatar.metamapping.model.User
import com.iluwatar.metamapping.utils.HibernateUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import org.hibernate.HibernateException

private val logger = KotlinLogging.logger {}

/**
 * Service layer for user.
 */
class UserService {
    private val factory = HibernateUtil.sessionFactory

    /**
     * List all users.
     *
     * @return list of users
     */
    fun listUser(): List<User> {
        logger.info { "list all users." }
        val users = mutableListOf<User>()
        try {
            factory.openSession().use { session ->
                val tx = session.beginTransaction()
                @Suppress("UNCHECKED_CAST")
                val userIter = session.createQuery("FROM User").list() as List<User>
                users.addAll(userIter)
                tx.commit()
            }
        } catch (e: HibernateException) {
            logger.debug(e) { "fail to get users" }
        }
        return users
    }

    /**
     * Add a user.
     *
     * @param user user entity
     * @return user id
     */
    fun createUser(user: User): Int {
        logger.info { "create user: ${user.username}" }
        var id = -1
        try {
            factory.openSession().use { session ->
                val tx = session.beginTransaction()
                id = session.save(user) as Int
                tx.commit()
            }
        } catch (e: HibernateException) {
            logger.debug(e) { "fail to create user" }
        }
        logger.info { "create user ${user.username} at $id" }
        return id
    }

    /**
     * Update user.
     *
     * @param id user id
     * @param user new user entity
     */
    fun updateUser(id: Int, user: User) {
        logger.info { "update user at $id" }
        try {
            factory.openSession().use { session ->
                val tx = session.beginTransaction()
                user.id = id
                session.update(user)
                tx.commit()
            }
        } catch (e: HibernateException) {
            logger.debug(e) { "fail to update user" }
        }
    }

    /**
     * Delete user.
     *
     * @param id user id
     */
    fun deleteUser(id: Int) {
        logger.info { "delete user at: $id" }
        try {
            factory.openSession().use { session ->
                val tx = session.beginTransaction()
                val user = session.get(User::class.java, id)
                session.delete(user)
                tx.commit()
            }
        } catch (e: HibernateException) {
            logger.debug(e) { "fail to delete user" }
        }
    }

    /**
     * Get user.
     *
     * @param id user id
     * @return user entity
     */
    fun getUser(id: Int): User? {
        logger.info { "get user at: $id" }
        var user: User? = null
        try {
            factory.openSession().use { session ->
                val tx = session.beginTransaction()
                user = session.get(User::class.java, id)
                tx.commit()
            }
        } catch (e: HibernateException) {
            logger.debug(e) { "fail to get user" }
        }
        return user
    }

    /**
     * Close hibernate.
     */
    fun close() {
        HibernateUtil.shutdown()
    }
}

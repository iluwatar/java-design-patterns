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
// ABOUTME: Utility object providing singleton access to Hibernate SessionFactory.
// ABOUTME: Initializes the SessionFactory from hibernate.cfg.xml configuration on startup.
package com.iluwatar.cqrs.util

import io.github.oshai.kotlinlogging.KotlinLogging
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

private val logger = KotlinLogging.logger {}

/**
 * This object simply returns one instance of [SessionFactory] initialized when the application
 * is started.
 */
object HibernateUtil {

    val sessionFactory: SessionFactory = buildSessionFactory()

    private fun buildSessionFactory(): SessionFactory {
        // configures settings from hibernate.cfg.xml
        val registry = StandardServiceRegistryBuilder().configure().build()
        return try {
            MetadataSources(registry).buildMetadata().buildSessionFactory()
        } catch (ex: Exception) {
            StandardServiceRegistryBuilder.destroy(registry)
            logger.error(ex) { "Initial SessionFactory creation failed." }
            throw ExceptionInInitializerError(ex)
        }
    }
}

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

// ABOUTME: Hibernate session factory singleton providing database connection management.
// ABOUTME: Configures H2 in-memory database and entity mappings for the application.
package com.iluwatar.servicelayer.hibernate

import com.iluwatar.servicelayer.spell.Spell
import com.iluwatar.servicelayer.spellbook.Spellbook
import com.iluwatar.servicelayer.wizard.Wizard
import io.github.oshai.kotlinlogging.KotlinLogging
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

private val logger = KotlinLogging.logger {}

/**
 * Produces the Hibernate [SessionFactory].
 */
object HibernateUtil {

    /** The cached session factory. */
    @Volatile
    private var sessionFactory: SessionFactory? = null

    /**
     * Create the current session factory instance, create a new one when there is none yet.
     *
     * @return The session factory
     */
    @Synchronized
    fun getSessionFactory(): SessionFactory {
        if (sessionFactory == null) {
            try {
                sessionFactory = Configuration()
                    .addAnnotatedClass(Wizard::class.java)
                    .addAnnotatedClass(Spellbook::class.java)
                    .addAnnotatedClass(Spell::class.java)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                    .setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.show_sql", "false")
                    .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                    .buildSessionFactory()
            } catch (ex: Throwable) {
                logger.error(ex) { "Initial SessionFactory creation failed." }
                throw ExceptionInInitializerError(ex)
            }
        }
        return sessionFactory!!
    }

    /**
     * Drop the current connection, resulting in a create-drop clean database next time. This is
     * mainly used for JUnit testing since one test should not influence the other.
     */
    fun dropSession() {
        getSessionFactory().close()
        sessionFactory = null
    }
}

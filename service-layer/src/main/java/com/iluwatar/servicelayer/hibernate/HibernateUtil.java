/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.servicelayer.hibernate;

import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.wizard.Wizard;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produces the Hibernate {@link SessionFactory}.
 */
public final class HibernateUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

  /**
   * The cached session factory
   */
  private static volatile SessionFactory sessionFactory;

  private HibernateUtil() {
  }

  /**
   * Create the current session factory instance, create a new one when there is none yet.
   *
   * @return The session factory
   */
  public static synchronized SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        sessionFactory =
            new Configuration().addAnnotatedClass(Wizard.class).addAnnotatedClass(Spellbook.class)
                .addAnnotatedClass(Spell.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop").buildSessionFactory();
      } catch (Throwable ex) {
        LOGGER.error("Initial SessionFactory creation failed.", ex);
        throw new ExceptionInInitializerError(ex);
      }
    }
    return sessionFactory;
  }

  /**
   * Drop the current connection, resulting in a create-drop clean database next time. This is
   * mainly used for JUnit testing since one test should not influence the other
   */
  public static void dropSession() {
    getSessionFactory().close();
    sessionFactory = null;
  }

}

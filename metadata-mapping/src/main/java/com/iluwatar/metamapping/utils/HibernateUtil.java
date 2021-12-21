package com.iluwatar.metamapping.utils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * manage hibernate
 */
@Slf4j
public class HibernateUtil {

  private static final SessionFactory sessionFactory = buildSessionFactory();

  /**
   * build session factory
   * @return session factory
   */
  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      return new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      LOGGER.error("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * get session factory
   * @return session factory
   */
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * close session factory
   */
  public static void shutdown() {
    // Close caches and connection pools
    getSessionFactory().close();
  }

}
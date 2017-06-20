package com.iluwatar.cqrs.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * 
 * @author Sabiq Ihab
 *
 */
public class HibernateUtil {

  private static final SessionFactory SESSIONFACTORY = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings //
                                                                                              // from hibernate.cfg.xml
        .build();
    try {
      return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Throwable ex) {
      StandardServiceRegistryBuilder.destroy(registry);
      // TODO HibernateUtil : change print with logger
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return SESSIONFACTORY;
  }

}

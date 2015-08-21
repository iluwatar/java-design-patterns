package com.iluwatar.servicelayer.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.iluwatar.servicelayer.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.spellbook.Spellbook;
import com.iluwatar.servicelayer.wizard.Wizard;

/**
 * 
 * Produces the Hibernate {@link SessionFactory}.
 *
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
	    	sessionFactory = new Configuration()
	  	  		.addAnnotatedClass(Wizard.class)
	  	  		.addAnnotatedClass(Spellbook.class)
	  	  		.addAnnotatedClass(Spell.class)
	  	  		.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
	  	  		.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
	  	  		.setProperty("hibernate.current_session_context_class", "thread")
	  	  		.setProperty("hibernate.show_sql", "true")
	  	  		.setProperty("hibernate.hbm2ddl.auto", "create-drop")
	  	  		.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

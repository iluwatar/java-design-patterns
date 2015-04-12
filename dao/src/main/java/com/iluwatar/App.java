package com.iluwatar;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App 
{
    public static void main( String[] args ) {
    	
    	SessionFactory sessionFactory = new Configuration()
    	  .addAnnotatedClass(Wizard.class)
    	  .addAnnotatedClass(Spellbook.class)
    	  .addAnnotatedClass(Spell.class)
    	  .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
    	  .setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
    	  .setProperty("hibernate.current_session_context_class", "thread")
    	  .setProperty("hibernate.show_sql", "true")
    	  .setProperty("hibernate.hbm2ddl.auto", "create")
    	  .buildSessionFactory();
    	  
    	  Session session = sessionFactory.getCurrentSession();
    	  session.beginTransaction();
    	  Wizard wizard = new Wizard();
    	  wizard.setFirstName("Jugga");
    	  Spellbook spellbook = new Spellbook();
    	  Spell spell = new Spell();
    	  spellbook.getSpells().add(spell);
    	  wizard.getSpellbooks().add(spellbook);
    	  session.persist(wizard);
    	  Query query = session.createQuery("from Wizard");
    	  List<?> list = query.list();
    	  Wizard p1 = (Wizard) list.get(0);
    	  System.out.println(String.format("id=%d firstName=%s", p1.getId(), p1.getFirstName()));
    	  session.getTransaction().commit();    	
    }
}

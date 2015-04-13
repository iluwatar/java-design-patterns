package com.iluwatar;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public abstract class DaoBase<E extends BaseEntity> {
	
	@SuppressWarnings("unchecked")
	protected Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

	protected final SessionFactory sessionFactory = createSessionFactory();
	
	private SessionFactory createSessionFactory() {
    	SessionFactory sessionFactory = new Configuration()
  	  		.addAnnotatedClass(Wizard.class)
  	  		.addAnnotatedClass(Spellbook.class)
  	  		.addAnnotatedClass(Spell.class)
  	  		.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
  	  		.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
  	  		.setProperty("hibernate.current_session_context_class", "thread")
  	  		.setProperty("hibernate.show_sql", "true")
  	  		.setProperty("hibernate.hbm2ddl.auto", "create-drop")
  	  		.buildSessionFactory();
    	return sessionFactory;
	}
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	E find(Long id) {
		Session session = getSession();
		Transaction tx = null;
		E result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(persistentClass);
			criteria.add(Restrictions.idEq(id));
			result = (E) criteria.uniqueResult();
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
		return result;
	}
	
	void persist(E entity) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(entity);
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
	}
	
	E merge(E entity) {
		Session session = getSession();
		Transaction tx = null;
		E result = null;
		try {
			tx = session.beginTransaction();
			result = (E) session.merge(entity);
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
		return result;
	}
	
	void delete(E entity) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
	}
	
	List<E> findAll() {
		Session session = getSession();
		Transaction tx = null;
		List<E> result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			result = criteria.list();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
		return result;
	}
}

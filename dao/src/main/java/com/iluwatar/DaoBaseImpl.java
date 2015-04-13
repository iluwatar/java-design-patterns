package com.iluwatar;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public abstract class DaoBaseImpl<E extends BaseEntity> implements Dao<E> {
	
	@SuppressWarnings("unchecked")
	protected Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

	private Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	@Override
	public E find(Long id) {
		Session session = getSession();
		Transaction tx = null;
		E result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
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
	
	@Override
	public void persist(E entity) {
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
	
	@Override
	public E merge(E entity) {
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
	
	@Override
	public void delete(E entity) {
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
	
	@Override
	public List<E> findAll() {
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

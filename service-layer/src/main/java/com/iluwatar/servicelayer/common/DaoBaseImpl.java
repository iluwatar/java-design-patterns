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
package com.iluwatar.servicelayer.common;

import com.iluwatar.servicelayer.hibernate.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Base class for Dao implementations.
 *
 * @param <E> Type of Entity
 */
public abstract class DaoBaseImpl<E extends BaseEntity> implements Dao<E> {

  @SuppressWarnings("unchecked")
  protected Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass()
      .getGenericSuperclass()).getActualTypeArguments()[0];

  /*
   * Making this getSessionFactory() instead of getSession() so that it is the responsibility
   * of the caller to open as well as close the session (prevents potential resource leak).
   */
  protected SessionFactory getSessionFactory() {
    return HibernateUtil.getSessionFactory();
  }

  @Override
  public E find(Long id) {
    Transaction tx = null;
    E result;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<E> builderQuery = criteriaBuilder.createQuery(persistentClass);
      Root<E> root = builderQuery.from(persistentClass);
      builderQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
      Query<E> query = session.createQuery(builderQuery);
      result = query.uniqueResult();
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
    return result;
  }

  @Override
  public void persist(E entity) {
    Transaction tx = null;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      session.persist(entity);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
  }

  @Override
  public E merge(E entity) {
    Transaction tx = null;
    E result;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      result = (E) session.merge(entity);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
    return result;
  }

  @Override
  public void delete(E entity) {
    Transaction tx = null;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      session.delete(entity);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
  }

  @Override
  public List<E> findAll() {
    Transaction tx = null;
    List<E> result;
    try (var session = getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<E> builderQuery = criteriaBuilder.createQuery(persistentClass);
      Root<E> root = builderQuery.from(persistentClass);
      builderQuery.select(root);
      Query<E> query = session.createQuery(builderQuery);
      result = query.getResultList();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    }
    return result;
  }
}

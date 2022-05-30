package com.iluwatar.pessimistic.concurrency;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CustomerDao implements Dao<Customer> {
  private EntityManagerFactory emf;

  public CustomerDao(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public Optional<Customer> get(long id) {
    EntityManager entityManager = emf.createEntityManager();

    Optional<Customer> result = Optional.ofNullable(entityManager.find(Customer.class, id));

    entityManager.close();
    return result;
  }

  @Override
  public List<Customer> getAll() {
    EntityManager entityManager = emf.createEntityManager();
    Query query = entityManager.createQuery("SELECT e FROM Customer e");
    List<Customer> result = query.getResultList();
    entityManager.close();
    return result;
  }

  @Override
  public void save(Customer customer) {
    executeInsideTransaction(entityManager -> entityManager.persist(customer));
  }

  private void executeInsideTransaction(Consumer<EntityManager> action) {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      action.accept(entityManager);
      transaction.commit();
    } catch (RuntimeException e) {
      transaction.rollback();
      throw e;
    }
    entityManager.close();
  }

  @Override
  public void update(Customer customer, String[] params) throws LockingException {
    executeInsideTransaction(
        em -> {
          Query query =
              em.createQuery("update Customer set " + "name = :newName " + "where id = :oldId ");

          query.setParameter("newName", customer.getName());
          query.setParameter("oldId", Long.parseLong(params[0]));
          query.executeUpdate();
        });
  }

  @Override
  public void delete(Customer customer) {
    executeInsideTransaction(em -> em.remove(customer));
  }

  /**
   * clears the customer table.
   */
  public void deleteAll() {
    executeInsideTransaction(
        em -> {
          Query query = em.createQuery("DELETE FROM Customer");
          query.executeUpdate();
        });
  }

  public boolean verifyLock(Customer customer, String editingUser) {
    return Objects.equals(customer.getLockingUser(), editingUser);
  }
}

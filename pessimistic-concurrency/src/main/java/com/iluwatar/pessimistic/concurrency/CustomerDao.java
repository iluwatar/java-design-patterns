package com.iluwatar.pessimistic.concurrency;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CustomerDao implements Dao<Customer>{
    private EntityManagerFactory emf;

    public CustomerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Optional<Customer> get(long id) {
        EntityManager entityManager = emf.createEntityManager();
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> getAll() {
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM Customer e");
        query.executeUpdate();
        return null;
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
        executeInsideTransaction(em -> {
            Query query = em.createQuery("update Customer set "
                + "id = :newId, "
                + "name = :newName "
                + "where id = :oldId "

        );
        query.setParameter("newId", customer.getId());
        query.setParameter("newName", customer.getName());
        query.setParameter("oldId", Integer.parseInt(params[0]));
        query.executeUpdate();
        });
    }

    @Override
    public void delete(Customer customer) {
        executeInsideTransaction(em -> em.remove(customer));
    }

    public void  deleteAll() {
        executeInsideTransaction(em -> {
            Query query = em.createQuery("DELETE FROM Customer");
            query.executeUpdate();
        });
    }
}

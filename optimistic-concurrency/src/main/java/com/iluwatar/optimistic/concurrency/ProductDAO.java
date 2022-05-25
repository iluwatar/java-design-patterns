package com.iluwatar.optimistic.concurrency;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

import org.hibernate.StaleObjectStateException;

public class ProductDAO implements Dao<Product> {
    private EntityManager em;
    public ProductDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Optional<Product> get(long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public List<Product> getAll() {
        Query query = em.createQuery("SELECT e from Product e");
        return query.getResultList();
    }

    @Override
    public void save(Product product) {
        executeInsideTransaction(em -> em.persist(product));
    }

    @Override
    public void update(Product product, String[] params) throws OptimisticLockException, StaleObjectStateException {
        product.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        product.setDescription(Objects.requireNonNull(params[1], "Description cannot be null"));
        product.setPrice(Objects.requireNonNull(Double.valueOf(params[2]), "Price cannot be null"));
        product.setAmountInStock(Objects.requireNonNull(Integer.valueOf(params[3]), "Amount in stock cannot be null"));

        executeInsideTransaction(entityManager -> entityManager.merge(product));
    }

    @Override
    public void delete(Product product) {
        executeInsideTransaction(entityManager -> entityManager.remove(product));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}

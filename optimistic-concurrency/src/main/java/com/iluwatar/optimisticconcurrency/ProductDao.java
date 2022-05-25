package com.iluwatar.optimisticconcurrency;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * An implementation of Dao interface for Product class.
 */
public class ProductDao implements Dao<Product> {
    /**
     * entry manager factory to use for creating entity manager.
     */
    private EntityManagerFactory emf;

    /**
     * Construct an instance of ProductDao.
     * @param entityManagerFactory the entity manager factory to use.
     */
    public ProductDao(final EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    /**
     * Get a product with the specified id from the Product table.
     * @param id id of the product.
     * @return a product with the id.
     */
    @Override
    public Optional<Product> get(final long id) {
        EntityManager em = emf.createEntityManager();
        Optional result = Optional.ofNullable(em.find(Product.class, id));
        em.close();
        return result;
    }

    /**
     * Get all products from the Product table.
     * @return a list of all products.
     */
    @Override
    public List<Product> getAll() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM Product e");
        List result = query.getResultList();
        em.close();
        return result;
    }

    /**
     * Insert a product in the Product table.
     * @param product the product to be inserted.
     */
    @Override
    public void save(final Product product) {
        executeInsideTransaction(em -> em.persist(product));
    }

    /**z
     * Update a product in the Product table.
     * @param product the product to be updated.
     */
    @Override
    public void update(final Product product) {
        executeInsideTransaction(em -> em.merge(product));
    }

    /**
     * Delete a product from the Product table.
     * @param product the product to be deleted.
     */
    @Override
    public void delete(final Product product) {
        executeInsideTransaction(em -> em.remove(product));
    }

    /**
     * Delete all products in the Product table.
     */
    public void  deleteAll() {
        executeInsideTransaction((em) -> {
            Query query = em.createQuery("DELETE FROM Product");
            query.executeUpdate();
        });
    }

    /**
     * Execute operation in transaction.
     * @param op operation to perform.
     */
    private void executeInsideTransaction(final Consumer<EntityManager> op) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            op.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
        em.close();
    }
}

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
     * @param newProduct the product to be updated.
     * @param oldId id of the product to be updated.
     * @param oldVersion version of the product to be updated.
     * @param useLock whether to enable locking.<br>
     *                This is used to show difference.
     * @return the number of rows affected by the update.
     */
    @Override
    public int update(final Product newProduct, final long oldId,
                       final int oldVersion, final boolean useLock) {

        var rowsAffected = new Object() {
            private int num = 0;

            public int getNum() {
                return num;
            }
        };

        executeInsideTransaction((em) -> {
            // construct query
            Query query = em.createQuery("update Product set "
                    + "id = :newId, "
                    + "version = :newVersion, "
                    + "name = :newName, "
                    + "description = :newDesc, "
                    + "price = :newPrice, "
                    + "amountInStock = :newAmount "
                    + "where id = :oldId "
                    + "and version = :oldVersion"
            );
            // set params
            query.setParameter("newId", newProduct.getId());
            query.setParameter("newVersion",
                    useLock ? oldVersion + 1 : oldVersion);
            query.setParameter("newName", newProduct.getName());
            query.setParameter("newDesc", newProduct.getDescription());
            query.setParameter("newPrice", newProduct.getPrice());
            query.setParameter("newAmount", newProduct.getAmountInStock());
            query.setParameter("oldId", oldId);
            query.setParameter("oldVersion", oldVersion);
            // update
            rowsAffected.num = query.executeUpdate();
        });

        return rowsAffected.num;
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

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
  private final EntityManagerFactory emf;

  /**
   * Construct an instance of ProductDao.
   * @param emFactory the entity manager factory to use.
   */
  public ProductDao(final EntityManagerFactory emFactory) {
    this.emf = emFactory;
  }

  /**
   * Get a product with the specified id from the Product table.
   * @param id id of the product.
   * @return a product with the id.
   */
  @Override
  public Optional<Product> get(final long id) {
    final EntityManager entityManager = emf.createEntityManager();
    final Optional<Product> result = Optional.ofNullable(
        entityManager.find(Product.class, id));
    entityManager.close();
    return result;
  }

  /**
   * Get all products from the Product table.
   * @return a list of all products.
   */
  @Override
  public List<Product> getAll() {
    final EntityManager entityManager = emf.createEntityManager();
    final Query query = entityManager.createQuery("SELECT e FROM Product e");
    final List<Product> result = query.getResultList();
    entityManager.close();
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

  /**
   * Update a product in the Product table.
   * @param newProduct the product to be updated.
   * @param oldId id of the product to be updated.
   * @param oldVersion version of the product to be updated.
   * @param useLock whether to enable locking.
   * @throws OptimisticLockException when update with outdated version
   */
  @Override
  public void update(final Product newProduct,
                     final long oldId,
                     final int oldVersion,
                     final boolean useLock) throws OptimisticLockException {
    final var rowsAffected = new Object() {
      /**
       * num.
       */
      private int num;

      public int getNum() {
        return num;
      }

      public void setNum(final int rows) {
        num = rows;
      }
    };

    executeInsideTransaction(em -> {
      // construct query
      final Query query = em.createQuery("update Product set "
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
      rowsAffected.setNum(query.executeUpdate());
    });

    if (rowsAffected.getNum() == 0) {
      throw new OptimisticLockException(
          "Rows were modified by other transactions!");
    }
  }

  /**
   * Delete a product from the Product table.
   * @param product the product to be deleted.
   */
  @Override
  public void delete(final Product product) {
    executeInsideTransaction(em -> em.remove(
        em.contains(product) ? product : em.merge(product)));
  }

  /**
   * Delete all products in the Product table.
   */
  public void  deleteAll() {
    executeInsideTransaction(em -> {
      final Query query = em.createQuery("DELETE FROM Product");
      query.executeUpdate();
    });
  }

  /**
   * Execute operation in transaction.
   * @param action operation to perform.
   */
  private void executeInsideTransaction(final Consumer<EntityManager> action) {
    final EntityManager entityManager = emf.createEntityManager();
    final EntityTransaction transaction = entityManager.getTransaction();
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
}

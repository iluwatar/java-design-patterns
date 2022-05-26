package com.iluwatar.optimisticconcurrency;

import java.util.List;
import java.util.Optional;

/**
 * A Dao interface to be implemented by concrete Dao.
 * @param <T> Entity class.
 */
public interface Dao<T> {
  /**
   * Get an entity from database based on id.
   * @param id id of the entity.
   * @return an entity with the id.
   */
  Optional<T> get(long id);

  /**
   * Get all entities from database that belong to the Entity class.
   * @return list of entities in the Entity class.
   */
  List<T> getAll();

  /**
   * Save entry in the database.
   * @param t the entry to be saved.
   */
  void save(T t);

  /**
   * Update an entry in the database.
   * @param newT the entry to be updated.
   * @param oldId old id of the entry. (In case of id being updated)
   * @param oldVersion old version of the entry.
   * @param useLock whether to lock when performing update.
   * @throws OptimisticLockException when update with outdated version
   */
  void update(T newT, long oldId,
              int oldVersion, boolean useLock) throws OptimisticLockException;

  /**
   * Delete an entry from the database.
   * @param t the entry to be deleted.
   */
  void delete(T t);
}

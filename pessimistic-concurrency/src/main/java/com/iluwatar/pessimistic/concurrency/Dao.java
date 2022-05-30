package com.iluwatar.pessimistic.concurrency;

import java.util.List;
import java.util.Optional;

/**
 * A Dao interface to be implemented by concrete Dao.
 *
 * @param <T> Entity class.
 */
public interface Dao<T> {
  /**
   * Get an entity from database based on id.
   *
   * @param id id of the entity.
   * @return an entity with the id.
   */
  Optional<T> get(long id);

  /**
   * Save entry in the database.
   *
   * @param t the entry to be saved.
   */
  void save(T t);

  /**
   * Update an entry in the database.
   *
   * @param t the entry to be updated.
   * @throws LockingException when update with outdated version
   */
  void update(T t, String[] params) throws LockingException;

  /**
   * Delete an entry from the database.
   *
   * @param t the entry to be deleted.
   */
  void delete(T t);
}

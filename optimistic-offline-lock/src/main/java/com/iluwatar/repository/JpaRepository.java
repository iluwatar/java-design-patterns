package com.iluwatar.repository;

/**
 * Imitation of Spring's JpaRepository.
 *
 * @param <T> target database entity
 */
public interface JpaRepository<T> {

  /**
   * Get object by it's PK.
   *
   * @param id primary key
   * @return {@link T}
   */
  T findById(long id);

  /**
   * Get current object version.
   *
   * @param id primary key
   * @return object's version
   */
  int getEntityVersionById(long id);

  /**
   * Update object.
   *
   * @param obj entity to update
   * @return number of modified records
   */
  int update(T obj);
}
